package com.co.unibox

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PayClientActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private val TAKE_PHOTO_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopper_pay)

        // Botón de ir a la página principal
        val btn_Home = findViewById<ImageView>(R.id.btn_Home)
        btn_Home.setOnClickListener {
            val intent = Intent(this, HomeClientActivity::class.java)
            startActivity(intent)
        }

        // Botón de WhatsApp
        val btnwhat = findViewById<ImageView>(R.id.btnwhat)
        btnwhat.setOnClickListener {
            val intent = Intent(this, WhatsAppClientActivity::class.java)
            startActivity(intent)
        }

        // Botón de pagar (ahora abre la cámara)
        val btnPagar = findViewById<Button>(R.id.btnPagar)
        btnPagar.setOnClickListener {
            Log.d("PayClientActivity", "Botón PAGAR presionado")
            if (checkCameraPermission()) {
                openCameraForPhoto()
            } else {
                requestCameraPermission()
            }
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    private fun openCameraForPhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUEST_CODE)
        } catch (e: Exception) {
            Log.e("PayClientActivity", "Error al abrir la cámara para foto: ${e.message}")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    openCameraForPhoto()
                } else {
                    Log.d("PayClientActivity", "Permiso de cámara denegado")
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TAKE_PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            // Aquí puedes manejar la foto tomada
            Log.d("PayClientActivity", "Foto tomada con éxito")
            // Por ejemplo, puedes obtener la imagen así:
            // val imageBitmap = data?.extras?.get("data") as Bitmap
            // Y luego hacer algo con ella, como mostrarla en un ImageView
        }
    }
}