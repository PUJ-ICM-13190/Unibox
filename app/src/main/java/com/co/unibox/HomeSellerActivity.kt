package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class HomeSellerActivity: AppCompatActivity(){


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_seller)

        drawerLayout = findViewById(R.id.drawer_layout)
        topAppBar = findViewById(R.id.topAppBar)
        navigationView = findViewById(R.id.navigation_view)

        // Seleccionar la opción del dashboard al iniciar la actividad
        navigationView.setCheckedItem(R.id.nav_dashboard)

        // Configuración del botón del menú lateral (Drawer)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Manejo de las opciones del menú lateral
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_dashboard -> {
                    // Acción para el Dashboard
                }
                R.id.nav_cajitas -> {
                    val intent = Intent(this, MisCajitasSellerActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_productos -> {
                    val intent = Intent(this, ListProductsSeller::class.java)
                    startActivity(intent)
                }
                R.id.nav_ventas -> {
                    // Acción para ver las ventas
                }
                R.id.nav_logout -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

}