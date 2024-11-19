package com.co.unibox

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.co.unibox.data.Product
import com.co.unibox.databinding.SellerActivityEditProductBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class Seller_Activity_Edit_Product : AppCompatActivity() {

    private lateinit var binding: SellerActivityEditProductBinding
    private lateinit var productId: String
    private lateinit var userId: String
    private var newImageUri: Uri? = null // Almacena la URI de la nueva imagen seleccionada

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SellerActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el productId desde el intent
        productId = intent.getStringExtra("productId") ?: ""
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        if (productId.isEmpty() || userId.isEmpty()) {
            Toast.makeText(this, "Error: Producto no encontrado.", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Configurar el spinner y cargar los datos
        setupCategorySpinner()
        loadProductData()

        // Configurar los botones
        configureButtons()
    }

    private fun setupCategorySpinner() {
        val categories = resources.getStringArray(R.array.categorias)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter
    }

    private fun configureButtons() {
        binding.btnBack.setOnClickListener { finish() }
        binding.btnSave.setOnClickListener { saveProductData() }
        binding.btnEliminar.setOnClickListener { deleteProduct() }

        binding.btnEditImage.setOnClickListener {
            // Abrir la galería para seleccionar una nueva imagen
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }

    private fun loadProductData() {
        val database = FirebaseDatabase.getInstance().reference
        val productRef = database.child("products").child(userId).child(productId)

        productRef.get().addOnSuccessListener { snapshot ->
            val product = snapshot.getValue(Product::class.java)
            if (product != null) {
                binding.editProductName.setText(product.name)
                binding.spinnerCategory.setSelection(getCategoryIndex(product.category))
                binding.editPrice.setText(product.price.toString())
                binding.editQuantity.setText(product.quantity?.toString() ?: "")
                binding.editDescription.setText(product.description)

                // Cargar imagen con Glide
                Glide.with(this)
                    .load(product.imageUrl)
                    .placeholder(R.drawable.ic_products)
                    .into(binding.imgProduct)
            } else {
                Toast.makeText(this, "Producto no encontrado.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error al cargar los datos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCategoryIndex(category: String?): Int {
        val categories = resources.getStringArray(R.array.categorias)
        return categories.indexOfFirst { it.equals(category, ignoreCase = true) }.coerceAtLeast(0)
    }

    private fun saveProductData() {
        val name = binding.editProductName.text.toString().trim()
        val category = binding.spinnerCategory.selectedItem?.toString() ?: ""
        val price = binding.editPrice.text.toString().toDoubleOrNull()
        val quantity = binding.editQuantity.text.toString().toIntOrNull()
        val description = binding.editDescription.text.toString().trim()

        if (name.isEmpty() || category.isEmpty() || price == null || quantity == null) {
            Toast.makeText(this, "Por favor completa todos los campos correctamente.", Toast.LENGTH_SHORT).show()
            return
        }

        val database = FirebaseDatabase.getInstance().reference
        val productRef = database.child("products").child(userId).child(productId)

        productRef.get().addOnSuccessListener { snapshot ->
            val currentProduct = snapshot.getValue(Product::class.java)

            if (currentProduct == null) {
                Toast.makeText(this, "Producto no encontrado para actualizar.", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }

            // Obtener el `imageUrl` actual si no se selecciona una nueva imagen
            val updatedImageUrl = newImageUri?.let { saveImageLocally(it) } ?: currentProduct.imageUrl

            // Crear el objeto actualizado
            val updatedProduct = Product(
                id = productId,
                name = name,
                category = category,
                quantity = quantity,
                price = price,
                description = description,
                imageUrl = updatedImageUrl // Conservar la URL existente o usar la nueva
            )

            // Guardar en Firebase
            productRef.setValue(updatedProduct).addOnSuccessListener {
                Toast.makeText(this, "Producto actualizado correctamente.", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Error al actualizar el producto.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error al cargar los datos actuales.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun deleteProduct() {
        val database = FirebaseDatabase.getInstance().reference
        val productRef = database.child("products").child(userId).child(productId)

        productRef.removeValue().addOnSuccessListener {
            Toast.makeText(this, "Producto eliminado correctamente.", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Error al eliminar el producto.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveImageLocally(uri: Uri): String? {
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val file = File(getExternalFilesDir(null), "$productId.jpg")

            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }

            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error al guardar la imagen.", Toast.LENGTH_SHORT).show()
            return null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            newImageUri = data.data
            newImageUri?.let {
                Glide.with(this)
                    .load(it)
                    .placeholder(R.drawable.ic_products)
                    .into(binding.imgProduct)
            }
        }
    }
}
