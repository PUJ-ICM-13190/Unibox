package com.co.unibox

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.co.unibox.data.Cajita
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Seller_Activity_My_Cajitas : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Seller_Activity_Cajita_Adapter
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var fabAddCajita: FloatingActionButton
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val cajitas = mutableListOf<Cajita>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_activity_list_cajitas)

        // Inicializar Firebase
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        // Referencias de la UI
        topAppBar = findViewById(R.id.topAppBar)
        recyclerView = findViewById(R.id.recyclerViewCajitas)
        fabAddCajita = findViewById(R.id.fab_add_cajita)


        // Configurar RecyclerView
        // Configurar RecyclerView
        adapter = Seller_Activity_Cajita_Adapter(cajitas) { position ->
            val cajitaSeleccionada = cajitas[position]
            val intent = Intent(this, Seller_Activity_Details_Cajita::class.java).apply {
                putExtra("nombre", cajitaSeleccionada.nombre)
                putExtra("descripcion", cajitaSeleccionada.descripcion)
                putExtra("latitud", cajitaSeleccionada.latitud)
                putExtra("longitud", cajitaSeleccionada.longitud)
            }
            startActivity(intent)
        }


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Cargar las cajitas del usuario
        cargarCajitas()

        // Acción del botón flotante para agregar una nueva cajita
        fabAddCajita.setOnClickListener {
            val intent = Intent(this, Activity_Add_Cajita::class.java)
            startActivity(intent)
            Toast.makeText(this, "Función para agregar una nueva cajita", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarCajitas() {
        val userId = auth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            return
        }

        // Limpiar lista antes de cargar nuevos datos
        cajitas.clear()

        // Consultar todas las cajitas y filtrar por usuario
        database.child("cajitas").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (cajitaSnapshot in snapshot.children) {
                        val usuarioCajita = cajitaSnapshot.child("usuario").getValue(String::class.java)
                        if (usuarioCajita == userId) {
                            val nombreCajita = cajitaSnapshot.key ?: "Sin nombre"
                            val descripcion = cajitaSnapshot.child("descripcion").getValue(String::class.java) ?: "Sin descripción"
                            val latitud = cajitaSnapshot.child("ubicacion/latitud").getValue(Double::class.java) ?: 0.0
                            val longitud = cajitaSnapshot.child("ubicacion/longitud").getValue(Double::class.java) ?: 0.0

                            // Crear objeto Cajita y agregarlo a la lista
                            val cajita = Cajita(nombreCajita, descripcion, latitud, longitud)
                            cajitas.add(cajita)
                        }
                    }

                    if (cajitas.isEmpty()) {
                        Toast.makeText(this@Seller_Activity_My_Cajitas, "No tienes cajitas asociadas", Toast.LENGTH_SHORT).show()
                    } else {
                        adapter.notifyDataSetChanged() // Refrescar el adaptador
                    }
                } else {
                    Toast.makeText(this@Seller_Activity_My_Cajitas, "No se encontraron cajitas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Seller_Activity_My_Cajitas, "Error al cargar cajitas: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
