package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Seller_Activity_My_Cajitas : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Seller_Activity_Cajita_Adapter
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var fabAddCajita: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_activity_list_cajitas)

        topAppBar = findViewById(R.id.topAppBar)
        recyclerView = findViewById(R.id.recyclerViewCajitas)
        fabAddCajita = findViewById(R.id.fab_add_cajita)

        // Lista de cajitas con su nombre y descripción
        val cajitas = listOf(
            "Cajita 1" to "Contiene postres variados",
            "Cajita 2" to "Contiene productos electrónicos",
            "Cajita 3" to "Contiene ropa y accesorios"
        )

        adapter = Seller_Activity_Cajita_Adapter(cajitas) { position ->
            // Abre la actividad de detalles de la cajita seleccionada
            val intent = Intent(this, Seller_Activity_Details_Cajita::class.java)
            intent.putExtra("cajaId", position + 1)  // Pasar el ID de la cajita
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Acción del botón flotante para agregar una nueva cajita
        fabAddCajita.setOnClickListener {
            // Implementa la acción para agregar una nueva cajita
        }
    }
}
