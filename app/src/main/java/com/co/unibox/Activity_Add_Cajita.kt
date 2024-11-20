package com.co.unibox

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Activity_Add_Cajita : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var spinnerCajas: Spinner
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cajita)

        // Inicializar Firebase
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        // Referencias a los elementos de la UI
        spinnerCajas = findViewById(R.id.spinner_caja)
        btnGuardar = findViewById(R.id.btn_guardar)

        // Cargar datos del spinner desde Firebase
        cargarCajasDisponibles()

        // Manejar clic en el botón Guardar
        btnGuardar.setOnClickListener {
            guardarSeleccion()
        }
    }

    private fun cargarCajasDisponibles() {
        val listaCajas = mutableListOf<String>()

        // Obtener cajitas disponibles
        database.child("cajitas").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaCajas.clear()
                for (cajaSnapshot in snapshot.children) {
                    val disponible = cajaSnapshot.child("disponible").getValue(Boolean::class.java) ?: false
                    if (disponible) {
                        listaCajas.add(cajaSnapshot.key ?: "")
                    }
                }

                // Adaptador para el Spinner
                val adapter = ArrayAdapter(this@Activity_Add_Cajita, android.R.layout.simple_spinner_item, listaCajas)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCajas.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Activity_Add_Cajita, "Error al cargar cajas", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun guardarSeleccion() {
        val cajaSeleccionada = spinnerCajas.selectedItem?.toString() ?: return
        val usuarioActual = auth.currentUser

        if (usuarioActual == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = usuarioActual.uid

        // Verificar si la caja sigue disponible y asociarla al usuario
        database.child("cajitas").child(cajaSeleccionada).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val disponible = snapshot.child("disponible").getValue(Boolean::class.java) ?: false

                if (disponible) {
                    // Marcar la caja como no disponible
                    database.child("cajitas").child(cajaSeleccionada).updateChildren(
                        mapOf(
                            "disponible" to false,
                            "usuario" to userId
                        )
                    )

                    // Agregar la caja a la lista de cajas seleccionadas del usuario
                    database.child("users").child(userId).child("cajasSeleccionadas").addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(userSnapshot: DataSnapshot) {
                            val cajasSeleccionadas = userSnapshot.children.mapNotNull { it.getValue(String::class.java) }.toMutableList()
                            if (!cajasSeleccionadas.contains(cajaSeleccionada)) {
                                cajasSeleccionadas.add(cajaSeleccionada)
                            }
                            database.child("users").child(userId).child("cajasSeleccionadas").setValue(cajasSeleccionadas)

                            Toast.makeText(this@Activity_Add_Cajita, "Caja asociada exitosamente", Toast.LENGTH_SHORT).show()
                            finish() // Cierra la actividad
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@Activity_Add_Cajita, "Error al actualizar las cajas seleccionadas", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(this@Activity_Add_Cajita, "La caja ya no está disponible", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Activity_Add_Cajita, "Error al guardar selección", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
