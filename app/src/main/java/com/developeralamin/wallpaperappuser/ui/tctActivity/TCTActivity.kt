package com.developeralamin.wallpaperappuser.ui.tctActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.developeralamin.loveadmindashaboar.model.ColortoneModel
import com.developeralamin.wallpaperappuser.adapter.ColortoneAdapter
import com.developeralamin.wallpaperappuser.databinding.ActivityTctactivityBinding
import com.google.firebase.firestore.FirebaseFirestore

class TCTActivity : AppCompatActivity() {

    lateinit var binding: ActivityTctactivityBinding
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTctactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        db.collection("thecolortone").addSnapshotListener { value, error ->
            val listTheColorTone = arrayListOf<ColortoneModel>()
            val data = value?.toObjects(ColortoneModel::class.java)
            listTheColorTone.addAll(data!!)

            binding.revTCT.layoutManager =
                GridLayoutManager(this, 3)
            binding.revTCT.adapter = ColortoneAdapter(this, listTheColorTone)

        }

        binding.btnDone.setOnClickListener {
            if (binding.editColor.text.toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Color Code", Toast.LENGTH_SHORT).show()
            } else if (binding.editLink.text.toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Your Link", Toast.LENGTH_SHORT).show()
            } else {
                addWallpaperToneDB(binding.editColor.text.toString(),
                    binding.editLink.text.toString())
            }
        }
    }

    private fun addWallpaperToneDB(color: String, link: String) {
        val uid = db.collection("thecolortone").document().id

        val finalData = ColortoneModel(id = uid, link = link, color = color)

        db.collection("thecolortone").document(uid).set(finalData).addOnCompleteListener {

            if (it.isSuccessful) {
                Toast.makeText(this, "Color Added Successful", Toast.LENGTH_SHORT).show()
                binding.editLink.setText("")
                binding.editLink.clearFocus()
            } else {
                Toast.makeText(this, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            binding.editLink.setText("")
            binding.editLink.clearFocus()
        }

    }
}