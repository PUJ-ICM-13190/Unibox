package com.co.unibox

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.co.unibox.data.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.File
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

        // Configurar botones
        configureBackButton()
        configureAddProductButton()
        configureAddImageButton()

        // Verificar permisos de almacenamiento
        checkPermissions()
    }

    // Configurar el botón de regresar
    private fun configureBackButton() {
        val btnVolver = findViewById<ImageButton>(R.id.btn_back)
        btnVolver.setOnClickListener { finish() }
    }

    // Configurar el botón de agregar producto
    private fun configureAddProductButton() {
        val btnAgregar = findViewById<Button>(R.id.btn_add_product)
        btnAgregar.setOnClickListener {
            Log.d("AddProduct", "Botón Agregar presionado")
            validateAndSaveProduct()
        }
    }

    // Configurar el botón para añadir imagen
    private fun configureAddImageButton() {
        val btnAddImage = findViewById<ImageButton>(R.id.btn_add_image)
        btnAddImage.setOnClickListener {
            if (checkPermissions()) {
                openGallery()
            } else {
                requestPermissions()
            }
        }
    }

    // Abrir la galería para seleccionar una imagen
    private fun openGallery() {
        getContent.launch("image/*")
    }

    // Validar campos y guardar producto
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

    // Subir imagen localmente y guardar el producto en Firebase
    private fun uploadImageAndSaveProduct(
        name: String,
        category: String,
        quantity: Int?,
        price: Double,
        description: String
    ) {
        val userId = auth.currentUser?.uid ?: run {
            Toast.makeText(this, "Error al obtener el usuario autenticado.", Toast.LENGTH_SHORT).show()
            return
        }

        val productId = UUID.randomUUID().toString()

        // Guardar la imagen localmente
        val imagePath = imageUri?.let { saveImageLocally(it) }

        if (imagePath == null) {
            Toast.makeText(this, "Error al guardar la imagen localmente.", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear objeto Product
        val product = Product(
            id = productId,
            name = name,
            category = category,
            quantity = quantity,
            price = price,
            description = description,
            imageUrl = imagePath // Ruta local de la imagen
        )

        // Guardar el producto en Firebase Database
        database.child("products").child(userId).child(productId).setValue(product)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Producto guardado correctamente.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Seller_Activity_List_Products::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("AddProduct", "Error al guardar el producto: ${task.exception?.message}")
                    Toast.makeText(this, "Error al guardar el producto.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Guardar imagen localmente
    private fun saveImageLocally(uri: Uri): String? {
        return try {
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val fileName = "${UUID.randomUUID()}.jpg"
            val file = File(filesDir, fileName)
            val outputStream = file.outputStream()
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Verificar permisos de almacenamiento
    private fun checkPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }
    }

    // Solicitar permisos si no están otorgados
    private fun requestPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        requestPermissions(permissions, 1001)
    }
}
