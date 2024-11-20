package com.co.unibox

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private val cajitas = mutableListOf<Pair<String, String>>() // Lista de cajitas del usuario

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
            // Obtener el nombre de la caja en la posición seleccionada
            val nombreCaja = cajitas[position].first // 'first' contiene el nombre de la caja según tu lista de pares

            // Abre la actividad de detalles de la cajita seleccionada
            val intent = Intent(this, Seller_Activity_Details_Cajita::class.java)
            intent.putExtra("cajaNombre", nombreCaja) // Pasar el nombre de la cajita como extra
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

        // Referencia a las cajitas seleccionadas por el usuario
        database.child("users").child(userId).child("cajaSeleccionada").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nombreCajita = snapshot.getValue(String::class.java)
                    if (nombreCajita != null) {
                        // Obtener detalles de la cajita
                        database.child("cajitas").child(nombreCajita).addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(cajitaSnapshot: DataSnapshot) {
                                val descripcion = cajitaSnapshot.child("descripcion").getValue(String::class.java) ?: "Sin descripción"
                                cajitas.add(Pair(nombreCajita, descripcion))
                                adapter.notifyDataSetChanged() // Refrescar el adaptador
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(this@Seller_Activity_My_Cajitas, "Error al cargar cajita: ${error.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                } else {
                    Toast.makeText(this@Seller_Activity_My_Cajitas, "No tienes cajitas asociadas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Seller_Activity_My_Cajitas, "Error al cargar cajitas: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
