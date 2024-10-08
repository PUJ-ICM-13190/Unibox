package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.co.unibox.databinding.ViewSellerMisCajitasBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MisCajitasSellerActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_seller_mis_cajitas)

        drawerLayout = findViewById(R.id.drawer_layout)
        topAppBar = findViewById(R.id.topAppBar)
        navigationView = findViewById(R.id.navigation_view)


        // Configuración del botón del menú lateral (Drawer)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Manejo de las opciones del menú lateral
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this, HomeSellerActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_cajitas -> {
                    val intent = Intent(this, MisCajitasSellerActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_productos -> {
                    // Acción para ver los productos
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
