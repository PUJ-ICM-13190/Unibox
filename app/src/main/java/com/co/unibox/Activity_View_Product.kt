package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.co.unibox.databinding.ShopperActivityProductViewGeneralBinding

class Activity_View_Product : AppCompatActivity() {

    private lateinit var binding: ShopperActivityProductViewGeneralBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShopperActivityProductViewGeneralBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val productName = intent.getStringExtra("product_name") ?: ""
        val productCategory = intent.getStringExtra("product_category") ?: ""
        val productPrice = intent.getStringExtra("product_price") ?: ""
        val productImage = intent.getStringExtra("product_image") ?: ""

        binding.apply {

            productNameTextView.text = productName
            categoryTextView.text = productCategory
            priceTextView.text = productPrice

            val drawableResourceId = resources.getIdentifier(productImage, "drawable", packageName)
            if (drawableResourceId != 0) {
                Glide.with(this@Activity_View_Product)
                    .load(drawableResourceId)
                    .into(imageView15)
            }
        }

        binding.compra.setOnClickListener{
            val intent = Intent(this, Shopper_Activity_Pay::class.java)
            startActivity(intent)
        }

        binding.btnRegresar.setOnClickListener {
            finish()
        }
    }
}