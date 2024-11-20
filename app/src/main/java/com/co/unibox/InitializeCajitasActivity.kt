package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class InitializeCajitasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar Firebase Realtime Database
        val database = FirebaseDatabase.getInstance().reference

        // Crear datos iniciales para las cajitas
        val cajitas = mapOf(
            "Cajita 1" to mapOf(
                "descripcion" to "Contiene dulces",
                "ubicacion" to mapOf("latitud" to 4.628885957332721, "longitud" to -74.0646483975561),
                "disponible" to true,
                "usuario" to null
            ),
            "Cajita 2" to mapOf(
                "descripcion" to "Contiene comida",
                "ubicacion" to mapOf("latitud" to 4.630850948069212, "longitud" to -74.06368548451991),
                "disponible" to true,
                "usuario" to null
            ),
            "Cajita 3" to mapOf(
                "descripcion" to "Contiene postres",
                "ubicacion" to mapOf("latitud" to 4.627763359007732, "longitud" to -74.0642532130572),
                "disponible" to true,
                "usuario" to null
            ),
            "Cajita 4" to mapOf(
                "descripcion" to "Contiene dulces",
                "ubicacion" to mapOf("latitud" to 4.628832744077274, "longitud" to -74.0629979392442),
                "disponible" to true,
                "usuario" to null
            ),
            "Cajita 5" to mapOf(
                "descripcion" to "Contiene comida",
                "ubicacion" to mapOf("latitud" to 4.6285226225938585, "longitud" to -74.06478965486465),
                "disponible" to true,
                "usuario" to null
            ),
            "Cajita 6" to mapOf(
                "descripcion" to "Contiene postres",
                "ubicacion" to mapOf("latitud" to 4.628885957332721, "longitud" to -74.0646483975561),
                "disponible" to true,
                "usuario" to null
            ),
            "Cajita 7" to mapOf(
                "descripcion" to "Contiene dulces",
                "ubicacion" to mapOf("latitud" to 4.630850948069212, "longitud" to -74.06368548451991),
                "disponible" to true,
                "usuario" to null
            ),
            "Cajita 8" to mapOf(
                "descripcion" to "Contiene comida",
                "ubicacion" to mapOf("latitud" to 4.627763359007732, "longitud" to -74.0642532130572),
                "disponible" to true,
                "usuario" to null
            ),
            "Cajita 9" to mapOf(
                "descripcion" to "Contiene postres",
                "ubicacion" to mapOf("latitud" to 4.628832744077274, "longitud" to -74.0629979392442),
                "disponible" to true,
                "usuario" to null
            ),
            "Cajita 10" to mapOf(
                "descripcion" to "Contiene dulces",
                "ubicacion" to mapOf("latitud" to 4.6285226225938585, "longitud" to -74.06478965486465),
                "disponible" to true,
                "usuario" to null
            )
        )

        // Guardar las cajitas en Firebase
        database.child("cajitas").setValue(cajitas)
            .addOnSuccessListener {
                println("Cajitas inicializadas correctamente.")
            }
            .addOnFailureListener { error ->
                println("Error al inicializar cajitas: ${error.message}")
            }
    }
}
