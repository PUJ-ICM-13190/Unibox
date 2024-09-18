package com.co.unibox

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Usando ViewBinding para referenciar los elementos de la UI
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val vendedorUser = "vendedor"
        val vendedorPass = "vendedor"
        val clienteUser = "cliente"
        val clientePass = "cliente"

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Manejar la navegación al presionar "Registrarse"
        binding.btnRegistrarse.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Manejar la navegación al presionar "Iniciar Sesión"
        binding.btnIniciarSesion.setOnClickListener {
            val enteredUsername = binding.usernameEditText.text.toString()
            val enteredPassword = binding.passwordEditText.text.toString()

            if ((enteredUsername == vendedorUser && enteredPassword == vendedorPass) ||
                (enteredUsername == clienteUser && enteredPassword == clientePass)) {
                val intent = Intent(this, HomeClientActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
            } else {
                // Mostrar el diálogo de error
                showErrorDialog()
            }
        }
    }


    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error de autenticación")
        builder.setMessage("Usuario o contraseña incorrectos")
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss() // Cerrar el diálogo
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}