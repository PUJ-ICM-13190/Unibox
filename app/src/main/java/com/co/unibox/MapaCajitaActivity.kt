package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapaCajitaActivity : AppCompatActivity(), OnMapReadyCallback {

    private var latitud: Double = 0.0
    private var longitud: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa_cajita)

        // Recibir datos de latitud y longitud de la actividad anterior
        latitud = intent.getDoubleExtra("latitud", 0.0)
        longitud = intent.getDoubleExtra("longitud", 0.0)

        // Inicializar el mapa
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Crear LatLng a partir de la latitud y longitud recibidas
        val ubicacionCajita = LatLng(latitud, longitud)

        // Agregar un marcador en la ubicación
        googleMap.addMarker(
            MarkerOptions()
                .position(ubicacionCajita)
                .title("Ubicación de la cajita")
        )

        // Mover la cámara a la ubicación y ajustar el zoom
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionCajita, 15f))
    }
}
