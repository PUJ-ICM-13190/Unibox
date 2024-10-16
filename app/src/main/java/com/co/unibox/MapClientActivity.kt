package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.ActivityShopperMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapClientActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityShopperMapBinding
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopperMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el mapa
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Botón para regresar
        binding.btnRegresar?.setOnClickListener {
            finish()
        }
    }

    // Método que se llama cuando el mapa está listo
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        // Configurar la ubicación inicial (por ejemplo, Bogotá)
        val bogota = LatLng(4.60971, -74.08175)
        map.addMarker(MarkerOptions().position(bogota).title("Bogotá"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 12.0f))
    }
}