package com.co.unibox

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*

class Seller_Activity_Schedule_Location : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var selectedLocation: LatLng? = null
    private var activeMarker: Marker? = null // Referencia al marcador del punto de encuentro
    private var currentUserMarker: Marker? = null // Referencia al marcador del usuario actual
    private var otherUserMarker: Marker? = null // Referencia al marcador del otro usuario
    private lateinit var receiverUserId: String
    private lateinit var currentUserId: String
    private lateinit var chatRoomId: String
    private lateinit var confirmButton: View
    private lateinit var finishEventButton: View
    private lateinit var backButton: View
    private lateinit var database: FirebaseDatabase
    private lateinit var eventsRef: DatabaseReference
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var activeEventId: String? = null
    private var isCurrentUser: Boolean = false // Determina si el usuario es `currentUser`

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_activity_schedule_location)

        // Recibe los datos desde el Intent
        receiverUserId = intent.getStringExtra("RECEIVER_ID") ?: ""
        currentUserId = intent.getStringExtra("CURRENT_USER_ID") ?: ""
        chatRoomId = intent.getStringExtra("CHAT_ROOM_ID") ?: ""

        confirmButton = findViewById(R.id.confirm_button)
        finishEventButton = findViewById(R.id.finish_event_button)
        backButton = findViewById(R.id.btnRegresar)

        confirmButton.visibility = View.GONE // Ocultar botón inicialmente
        finishEventButton.visibility = View.GONE // Ocultar botón inicialmente

        database = FirebaseDatabase.getInstance()
        eventsRef = database.getReference("private_chats").child(chatRoomId).child("events")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Listener del botón de confirmar
        confirmButton.setOnClickListener {
            saveEventToFirebase()
        }

        // Listener del botón de finalizar evento
        finishEventButton.setOnClickListener {
            finishActiveEvent()
        }

        // Listener del botón de regresar
        backButton.setOnClickListener {
            finish() // Finaliza la actividad y regresa
        }

        // Verificar si ya existe un evento activo
        checkActiveEvent()

        // Solicitar permisos de ubicación
        checkLocationPermission()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Centrar el mapa en Bogotá
        val bogota = LatLng(4.60971, -74.08175) // Coordenadas de Bogotá
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 12f))

        // Agregar marcador al hacer long click, solo si no hay un evento activo
        mMap.setOnMapLongClickListener { location ->
            if (activeEventId == null) { // Permitir solo si no hay evento activo
                selectedLocation = location
                activeMarker?.remove() // Elimina cualquier marcador anterior
                activeMarker = mMap.addMarker(
                    MarkerOptions().position(location).title("Punto de encuentro seleccionado")
                )
                confirmButton.visibility = View.VISIBLE // Mostrar botón al seleccionar ubicación
            } else {
                Toast.makeText(this, "Ya hay un evento activo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkActiveEvent() {
        eventsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var newActiveEventId: String? = null
                var meetingPointLocation: LatLng? = null

                for (eventSnapshot in snapshot.children) {
                    val event = eventSnapshot.value as? Map<String, Any>
                    val status = event?.get("status") as? String
                    if (status == "active") {
                        newActiveEventId = eventSnapshot.key

                        // Obtener la ubicación del punto de encuentro
                        val locationMeetingPoint = event["locationMeetingPoint"] as? Map<String, Double>
                        val latitude = locationMeetingPoint?.get("latitude")
                        val longitude = locationMeetingPoint?.get("longitude")

                        if (latitude != null && longitude != null) {
                            meetingPointLocation = LatLng(latitude, longitude)
                        }

                        // Determinar si el usuario es `currentUser` o `receiverUser`
                        isCurrentUser = (currentUserId == event["idCurrentUser"])

                        startLocationUpdates()

                        if (activeEventId != null) {
                            // Configurar ubicación en tiempo real

                            listenToOtherUserLocation()
                        }

                        break // Solo puede haber un evento activo, detener búsqueda
                    }
                }

                // Si el evento activo ha cambiado, actualiza el marcador del punto de encuentro
                if (newActiveEventId != activeEventId) {
                    activeEventId = newActiveEventId

                    // Actualiza el marcador del punto de encuentro si hay un evento activo
                    if (meetingPointLocation != null) {
                        activeMarker?.remove() // Elimina el marcador previo si existe
                        activeMarker = mMap.addMarker(
                            MarkerOptions().position(meetingPointLocation).title("Punto de encuentro")
                        )
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(meetingPointLocation, 15f))
                    }
                }

                // Configurar visibilidad de los botones según el estado del evento
                if (activeEventId == null) {
                    confirmButton.visibility = View.VISIBLE
                    finishEventButton.visibility = View.GONE
                } else {
                    confirmButton.visibility = View.GONE
                    finishEventButton.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@Seller_Activity_Schedule_Location,
                    "Error al verificar eventos: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun saveEventToFirebase() {
        selectedLocation?.let { location ->
            val newEventId = eventsRef.push().key // Generar un ID único para el nuevo evento
            val eventData = mapOf(
                "idReceiverUser" to receiverUserId,
                "idCurrentUser" to currentUserId,
                "locationReceiverUser" to null,
                "locationCurrentUser" to null,
                "locationMeetingPoint" to mapOf(
                    "latitude" to location.latitude,
                    "longitude" to location.longitude
                ),
                "status" to "active"
            )

            // Guarda el evento en Firebase
            eventsRef.child(newEventId!!).setValue(eventData)
                .addOnSuccessListener {
                    activeMarker = mMap.addMarker(
                        MarkerOptions()
                            .position(location)
                            .title("Ubicación confirmada")
                    )

                    Toast.makeText(this, "Evento guardado correctamente", Toast.LENGTH_SHORT).show()

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))

                    confirmButton.visibility = View.GONE
                    finishEventButton.visibility = View.VISIBLE // Mostrar botón de finalizar
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al guardar el evento: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } ?: run {
            Toast.makeText(this, "Por favor selecciona una ubicación", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 5000 // Intervalo de actualizaciones (5 segundos)
            fastestInterval = 2000 // Intervalo más rápido (2 segundos)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permisos otorgados, solicitar actualizaciones de ubicación
            fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location = locationResult.lastLocation ?: return

                    val userLocation = LatLng(location.latitude, location.longitude)

                    // Actualizar la ubicación en Firebase
                    if (isCurrentUser) {
                        eventsRef.child(activeEventId!!).child("locationCurrentUser").setValue(
                            mapOf(
                                "latitude" to location.latitude,
                                "longitude" to location.longitude
                            )
                        )
                    } else {
                        eventsRef.child(activeEventId!!).child("locationReceiverUser").setValue(
                            mapOf(
                                "latitude" to location.latitude,
                                "longitude" to location.longitude
                            )
                        )
                    }

                    // Mostrar la ubicación en el mapa
                    if (currentUserMarker == null) {
                        currentUserMarker = mMap.addMarker(
                            MarkerOptions().position(userLocation).title("Mi ubicación").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                        )
                    } else {
                        currentUserMarker!!.position = userLocation
                    }
                }
            }, Looper.getMainLooper())
        } else {
            // Si los permisos no están otorgados, solicitarlos
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }
    }

    private fun listenToOtherUserLocation() {
        val otherUserLocationKey =
            if (isCurrentUser) "locationReceiverUser" else "locationCurrentUser"

        eventsRef.child(activeEventId!!).child(otherUserLocationKey)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val locationData = snapshot.value as? Map<String, Double>
                    val latitude = locationData?.get("latitude")
                    val longitude = locationData?.get("longitude")

                    if (latitude != null && longitude != null) {
                        val otherUserLocation = LatLng(latitude, longitude)

                        // Mostrar la ubicación del otro usuario en el mapa
                        if (otherUserMarker == null) {
                            otherUserMarker = mMap.addMarker(
                                MarkerOptions().position(otherUserLocation)
                                    .title("Ubicación del otro usuario")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            )
                        } else {
                            otherUserMarker!!.position = otherUserLocation
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@Seller_Activity_Schedule_Location,
                        "Error al obtener ubicación del otro usuario",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun finishActiveEvent() {
        if (activeEventId != null) {
            eventsRef.child(activeEventId!!).child("status").setValue("finished")
                .addOnSuccessListener {
                    Toast.makeText(this, "Evento finalizado correctamente", Toast.LENGTH_SHORT).show()
                    activeMarker?.remove() // Borra el marcador del evento
                    currentUserMarker?.remove() // Borra el marcador del usuario actual
                    otherUserMarker?.remove() // Borra el marcador del otro usuario
                    activeMarker = null
                    currentUserMarker = null
                    otherUserMarker = null
                    finishEventButton.visibility = View.GONE // Ocultar botón de finalizar
                    activeEventId = null
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al finalizar el evento: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "No hay evento activo para finalizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }
    }
}
