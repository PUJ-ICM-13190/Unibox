package com.co.unibox

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.co.unibox.data.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class Seller_Activity_Add_Product : AppCompatActivity() {

    private var imageUri: Uri? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            findViewById<ImageButton>(R.id.btn_add_image).setImageURI(imageUri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_activity_add_product)

        // Inicializar Firebase Auth y Database
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        configureBackButton()
        configureAddProductButton()
        configureAddImageButton()
    }

    private fun configureBackButton() {
        val btnVolver = findViewById<ImageButton>(R.id.btn_back)
        btnVolver.setOnClickListener { finish() }
    }

    private fun configureAddProductButton() {
        val btnAgregar = findViewById<Button>(R.id.btn_add_product)
        btnAgregar.setOnClickListener { validateAndSaveProduct() }

    }

    private fun configureAddImageButton() {
        val btnAddImage = findViewById<ImageButton>(R.id.btn_add_image)
        btnAddImage.setOnClickListener {
            if (checkPermission()) {
                openGallery()
            } else {
                requestPermission()
            }
        }
    }

    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        requestPermissions(arrayOf(permission), 1001)
    }

    private fun openGallery() {
        getContent.launch("image/*")
    }

    private fun validateAndSaveProduct() {
        val name = findViewById<EditText>(R.id.edit_product_name).text.toString().trim()
        val category = findViewById<Spinner>(R.id.spinner_category).selectedItem.toString()
        val quantity = findViewById<EditText>(R.id.edit_quantity).text.toString().toIntOrNull()
        val price = findViewById<EditText>(R.id.edit_price).text.toString().toDoubleOrNull()
        val description = findViewById<EditText>(R.id.edit_description).text.toString().trim()

        if (name.isEmpty() || category == "Seleccionar" || quantity == null || price == null || description.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        uploadImageAndSaveProduct(name, category, quantity, price, description)
    }

    private fun uploadImageAndSaveProduct(
        name: String,
        category: String,
        quantity: Int?, // Cambiado a Double para que coincida con la clase Product
        price: Double,
        description: String
    ) {
        val userId = auth.currentUser?.uid ?: run {
            Toast.makeText(this, "Error al obtener el usuario autenticado.", Toast.LENGTH_SHORT).show()
            return
        }

        // Subir imagen a Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("products/${UUID.randomUUID()}")
        imageUri?.let { uri ->
            storageRef.putFile(uri).addOnSuccessListener { taskSnapshot ->
                storageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                    // Guardar los datos del producto en Realtime Database
                    val productId = database.child("products").push().key ?: return@addOnSuccessListener
                    val product = Product(
                        id = productId, // Ahora aseguramos que el ID único sea usado
                        name = name,
                        category = category,
                        quantity = quantity, // Mantenerlo como Double
                        price = price,       // Mantenerlo como Double
                        description = description,
                        imageUrl = imageUrl.toString()
                    )

                    // Subir el producto a la base de datos
                    database.child("products").child(userId).child(productId).setValue(product)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Producto guardado correctamente.", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, Seller_Activity_List_Products::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Para limpiar la pila de actividades
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Error al guardar el producto.", Toast.LENGTH_SHORT).show()
                            }
                        }
                }.addOnFailureListener {
                    Toast.makeText(this, "Error al obtener la URL de la imagen.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Error al subir la imagen.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
