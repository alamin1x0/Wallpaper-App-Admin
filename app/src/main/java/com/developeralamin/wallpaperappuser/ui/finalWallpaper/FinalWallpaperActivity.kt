package com.developeralamin.wallpaperappuser.ui.finalWallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.developeralamin.loveadmindashaboar.adapter.CatImageAdapter
import com.developeralamin.loveadmindashaboar.model.BomModel
import com.developeralamin.wallpaperappuser.databinding.ActivityFinalWallpaperBinding
import com.google.firebase.firestore.FirebaseFirestore

class FinalWallpaperActivity : AppCompatActivity() {

    lateinit var binding: ActivityFinalWallpaperBinding
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        val uid = intent.getStringExtra("uid")
        val name = intent.getStringExtra("name")

        binding.catTitle.text = name.toString()
        binding.catSubtitle.text = name.toString()

        binding.catTitle.text = name.toString()
        binding.catSubtitle.text = "${name.toString()} Wallpaper"

        db.collection("categories").document(uid!!).collection("wallpaper")
            .addSnapshotListener { value, error ->

                val listofCatWallpaper = arrayListOf<BomModel>()
                val data = value?.toObjects(BomModel::class.java)
                listofCatWallpaper.addAll(data!!)

                binding.revFinal.layoutManager =
                    GridLayoutManager(this, 3)
                binding.revFinal.adapter = CatImageAdapter(this, listofCatWallpaper, uid)

            }

        binding.btnDone.setOnClickListener {
            if (binding.editLink.text.toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Valid Data", Toast.LENGTH_SHORT).show()
            } else {

                val finalUniqId = db.collection("categories").document().id

                val finalData = BomModel(id = finalUniqId, link = binding.editLink.text.toString())


                db.collection("categories").document(uid)
                    .collection("wallpaper").document(finalUniqId).set(finalData)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Wallpaper Added Successful", Toast.LENGTH_SHORT)
                                .show()
                            binding.editLink.setText("")
                            binding.editLink.clearFocus()
                        } else {
                            Toast.makeText(this, it.exception?.localizedMessage, Toast.LENGTH_SHORT)
                                .show()
                        }
                        binding.editLink.setText("")
                        binding.editLink.clearFocus()
                    }
            }
        }
    }
}
