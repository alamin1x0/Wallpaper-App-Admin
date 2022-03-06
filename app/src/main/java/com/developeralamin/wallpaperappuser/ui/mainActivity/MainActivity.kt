package com.developeralamin.wallpaperappuser.ui.mainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.developeralamin.wallpaperappuser.R
import com.developeralamin.wallpaperappuser.databinding.ActivityMainBinding
import com.developeralamin.wallpaperappuser.ui.bomActivity.BOMActivity
import com.developeralamin.wallpaperappuser.ui.catActivity.CATActivity
import com.developeralamin.wallpaperappuser.ui.tctActivity.TCTActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardBOM.setOnClickListener {
            startActivity(Intent(this@MainActivity, BOMActivity::class.java))
        }

        binding.cardCAT.setOnClickListener {
            startActivity(Intent(this@MainActivity, CATActivity::class.java))
        }

        binding.cardTCT.setOnClickListener {
            startActivity(Intent(this@MainActivity, TCTActivity::class.java))
        }
    }
}