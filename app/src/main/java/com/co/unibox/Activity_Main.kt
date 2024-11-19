package com.co.unibox

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Activity_Main : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Solicitar permisos para notificaciones en Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }

        // Inicializar Firebase Auth
        auth = Firebase.auth

        // Manejar la navegación al presionar "Registrarse"
        binding.btnRegistrarse.setOnClickListener {
            val intent = Intent(this, Activity_Register::class.java)
            startActivity(intent)
        }

        // Manejar la autenticación al presionar "Iniciar Sesión"
        binding.btnIniciarSesion.setOnClickListener {
            val email = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signIn(email, password)
            } else {
                Toast.makeText(this, "Por favor, ingrese email y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        // Lógica del botón para probar notificaciones
        binding.btnSellerHome.setOnClickListener {
            try {
                NotificationService.scheduleNotificationService(this)
                Toast.makeText(this, "Prueba de notificación iniciada", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al programar notificación: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso
                    val user = auth.currentUser
                    Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Shopper_Activity_Home::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Si el inicio de sesión falla, muestra un mensaje al usuario.
                    showErrorDialog()
                }
            }
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error de autenticación")
        builder.setMessage("Usuario o contraseña incorrectos")
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Permiso concedido para notificaciones", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permiso denegado para notificaciones", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

