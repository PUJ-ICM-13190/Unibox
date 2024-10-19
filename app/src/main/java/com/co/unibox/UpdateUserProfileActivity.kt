package com.co.unibox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class UpdateUserProfileActivity : AppCompatActivity() {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopper_update_data)

        // Botón de retroceso
        val btn_regresar = findViewById<ImageView>(R.id.btn_regresar)
        btn_regresar.setOnClickListener {
            finish() // Vuelve atrás cuando se hace clic en el ImageView
        }

        val button_seller_mode = findViewById<Button>(R.id.button_seller_mode)
        button_seller_mode.setOnClickListener {
            val intent = Intent(this, HomeSellerActivity::class.java)
            startActivity(intent) // Inicia la actividad para actualizar el perfil
        }

        // Verificar si el dispositivo admite autenticación biométrica
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // El dispositivo admite la autenticación biométrica
                setupBiometricAuthentication()
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(this, "El dispositivo no tiene hardware de huella digital", Toast.LENGTH_SHORT).show()
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Toast.makeText(this, "El sensor biométrico no está disponible actualmente", Toast.LENGTH_SHORT).show()
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Toast.makeText(this, "No hay huellas registradas en el dispositivo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupBiometricAuthentication() {
        // Ejecutador para el manejo de los callbacks de la huella digital
        val executor: Executor = ContextCompat.getMainExecutor(this)

        // Configurar el BiometricPrompt
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // Aquí se maneja la autenticación exitosa
                Toast.makeText(applicationContext, "Autenticación exitosa", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                // Aquí se maneja el error de autenticación
                Toast.makeText(applicationContext, "Error: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                // Aquí se maneja cuando la autenticación falla
                Toast.makeText(applicationContext, "Autenticación fallida", Toast.LENGTH_SHORT).show()
            }
        })

        // Configurar la ventana de autenticación biométrica
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación Biométrica")
            .setSubtitle("Inicie sesión utilizando su huella digital")
            .setNegativeButtonText("Cancelar")
            .build()

        // Mostrar el diálogo de autenticación al cargar la actividad
        biometricPrompt.authenticate(promptInfo)
    }
}


