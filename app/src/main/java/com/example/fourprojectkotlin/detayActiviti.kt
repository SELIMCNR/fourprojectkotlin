package com.example.fourprojectkotlin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fourprojectkotlin.databinding.ActivityDetayActivitiBinding

class detayActiviti : AppCompatActivity() {
    lateinit var binding: ActivityDetayActivitiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetayActivitiBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gelenVeri = intent.getStringExtra("isim")
        binding.materialTextView2.text = "Kaydedilen isim: ${gelenVeri}"
    }
}