package com.example.fourprojectkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fourprojectkotlin.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    var alinanVeri: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        sharedPreferences =
            getSharedPreferences("com.example.fourprojectkotlin", Context.MODE_PRIVATE)

        alinanVeri = sharedPreferences.getString("isim", "")

        // Eğer veri varsa, ekrana yansıt
        binding.materialTextView.text = "Kaydedilen isim: $alinanVeri"

        binding.materialButtonEkle.setOnClickListener {
            animasyonBaslat(it)
            kaydet(it)
        }

        binding.materialButtonSil.setOnClickListener {
            animasyonBaslat(it)
            sil(it)
        }

    }


    fun sharedPreferencesKaydet(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun animasyonBaslat(view: View) {
        view.animate().apply {
            duration = 300
            scaleX(1.1f)
            scaleY(1.1f)
            withEndAction {
                view.scaleX = 1f
                view.scaleY = 1f
            }
        }
    }

    fun kaydet(view: View) {
        try {
            val kullaniciIsmi = binding.textInputEditText.text.toString()

            if (kullaniciIsmi.isEmpty()) {
                Snackbar.make(view, "İsminizi boş bırakmayın!", Snackbar.LENGTH_LONG).show()
            } else {
                sharedPreferencesKaydet("isim", kullaniciIsmi)
                binding.materialTextView.text = "Kaydedilen İsim = $kullaniciIsmi"
                val intent = Intent(this, detayActiviti::class.java)
                intent.putExtra("isim", sharedPreferences.getString("isim", ""))
                startActivity(intent)

                Snackbar.make(view, "İsim başarıyla kaydedildi!", Snackbar.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Snackbar.make(view, "Bir hata oluştu: ${e.localizedMessage}", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    fun sil(view: View) {
        try {
            val builder = androidx.appcompat.app.AlertDialog.Builder(this)
            builder.setTitle("Silme İşlemi")
            builder.setMessage("Kaydedilmiş ismi silmek istediğinize emin misiniz?")
            builder.setPositiveButton("Evet") { _, _ ->
                sharedPreferences.edit().remove("isim").apply()
                binding.materialTextView.text = "Kaydedilen isim:"
                val intent = Intent(this, detayActiviti::class.java)
                intent.putExtra("isim1", sharedPreferences.getString("isim", ""))
                startActivity(intent)
                Snackbar.make(view, "İsim silindi!", Snackbar.LENGTH_LONG)

                    .setAction("Geri Al") {
                        sharedPreferences.edit().putString("isim", alinanVeri).apply()
                        binding.materialTextView.text = "Kaydedilen İsim = $alinanVeri"
                    }
                    .show()
            }
            builder.setNegativeButton("Hayır", null)
            builder.show()
        } catch (e: Exception) {
            Toast.makeText(this, "Bir hata oluştu : ${e.localizedMessage}", Toast.LENGTH_SHORT)
                .show()
        }

    }
}


