package com.co.unibox

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.co.unibox.databinding.ShopperActivityMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class Shopper_Activity_Map : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ShopperActivityMapBinding

    private lateinit var map: GoogleMap
    private lateinit var sellerData: JSONObject
    private lateinit var currentLocation: LatLng // Variable para almacenar la ubicación actual del cliente

    // Lista para mantener referencias de los marcadores
    private val markerList = mutableListOf<Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShopperActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el mapa
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Botón para regresar
        binding.btnRegresar?.setOnClickListener {
            finish()
        }

        // Configurar el RecyclerView
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val success = map.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                this, R.raw.style // Archivo style.json en la carpeta raw
            )
        )
        if (!success) {
            println("Error al aplicar el estilo del mapa.")
        }


        // Pedir permisos de localización
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }

        map.isMyLocationEnabled = true

        // Obtener la ubicación actual del cliente
        map.setOnMyLocationChangeListener { location ->
            currentLocation = LatLng(location.latitude, location.longitude)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16.0f))

            updateMarkers(currentLocation)
        }
        map.setOnMarkerClickListener(this)
    }

    private fun updateMarkers(currentLocation: LatLng) {
        // Remover los marcadores que están fuera del rango
        val maxDistance = 120
        val iterator = markerList.iterator()

        while (iterator.hasNext()) {
            val marker = iterator.next()
            val markerLocation = marker.position

            val distance = FloatArray(1)
            Location.distanceBetween(
                currentLocation.latitude, currentLocation.longitude,
                markerLocation.latitude, markerLocation.longitude,
                distance
            )

            // Remover los marcadores que están fuera del rango
            if (distance[0] > maxDistance) {
                marker.remove()
                iterator.remove()
            }
        }

        addMarkersFromJson(currentLocation)
    }

    // Método para leer el JSON y agregar los marcadores que están dentro de 100 metros del cliente
    private fun addMarkersFromJson(currentLocation: LatLng) {
        val jsonString = loadJSONFromAsset("data.json")
        sellerData = JSONObject(jsonString!!)

        val sellersArray = sellerData.getJSONArray("sellers")
        val maxDistance = 120
        val b = BitmapFactory.decodeResource(resources, R.drawable.logo_boxi)
        val smallMarker = Bitmap.createScaledBitmap(b, 150, 150, false)

        for (i in 0 until sellersArray.length()) {
            val seller = sellersArray.getJSONObject(i)
            val cajitasArray = seller.getJSONArray("cajitas")

            for (j in 0 until cajitasArray.length()) {
                val cajita = cajitasArray.getJSONObject(j)
                val cajitaName = cajita.getString("name")
                val ubication = cajita.getJSONObject("ubication")
                val latitude = ubication.getDouble("latitude")
                val longitude = ubication.getDouble("longitude")
                val location = LatLng(latitude, longitude)

                // Calcular la distancia entre la ubicación del cliente y la cajita
                val distance = FloatArray(1)
                Location.distanceBetween(
                    currentLocation.latitude, currentLocation.longitude,
                    latitude, longitude,
                    distance
                )

                // Agregar el marcador solo si la distancia está dentro del rango de 120 metros
                if (distance[0] <= maxDistance) {
                    val marker = map.addMarker(
                        MarkerOptions()
                            .position(location)
                            .title(cajitaName)
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                    )

                    marker?.tag = cajita
                    if (marker != null) {
                        markerList.add(marker) // Agregar el marcador a la lista
                    }
                }
            }
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val cajita = marker.tag as? JSONObject

        if (cajita != null) {
            val productsArray = cajita.getJSONArray("products")
            val activityProducts = mutableListOf<Activity_Product>()

            for (i in 0 until productsArray.length()) {
                val productJson = productsArray.getJSONObject(i)
                val activityProduct = Activity_Product(
                    id = productJson.getInt("id"),
                    name = productJson.getString("name"),
                    description = productJson.getString("description"),
                    price = productJson.getDouble("price"),
                    stock = productJson.getInt("stock")
                )
                activityProducts.add(activityProduct)
            }

            // Mostrar el RecyclerView con la lista de productos
            val adapter = Activity_Product_Map_View_Adapter(activityProducts)
            binding.recyclerViewProducts.adapter = adapter

            // Mostrar el CardView y el RecyclerView
            binding.cardViewEmprendimientos.visibility = View.VISIBLE
            binding.recyclerViewProducts.visibility = View.VISIBLE
        } else {
            // Ocultar el CardView si no hay productos seleccionados
            binding.cardViewEmprendimientos.visibility = View.GONE
        }

        return true
    }

    // Método para cargar el JSON desde la carpeta assets
    private fun loadJSONFromAsset(fileName: String): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }
}
