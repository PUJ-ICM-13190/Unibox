package com.co.unibox

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.RegisterBinding

class RegisterActivity : AppCompatActivity() {

    // Declarar la variable de binding
    private lateinit var binding: RegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la Toolbar como ActionBar
        setSupportActionBar(binding.toolbar)

        // Eliminar el título
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Habilitar el botón de navegación (volver)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Manejar el clic en el botón de volver
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.registerButton.setOnClickListener {
            showSuccessDialog()
        }
    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Registro Exitoso")
        builder.setMessage("Te has registrado correctamente")
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
            navigateToLogin()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    // Método para navegar a la página de inicio de sesión
    private fun navigateToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}