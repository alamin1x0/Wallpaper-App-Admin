package com.developeralamin.wallpaperappuser.ui.catActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.developeralamin.loveadmindashaboar.model.CatModel
import com.developeralamin.loveadmindashaboar.model.ColortoneModel
import com.developeralamin.wallpaperappuser.R
import com.developeralamin.wallpaperappuser.adapter.CatAdapter
import com.developeralamin.wallpaperappuser.databinding.ActivityCatactivityBinding
import com.google.firebase.firestore.FirebaseFirestore

class CATActivity : AppCompatActivity() {
    lateinit var binding: ActivityCatactivityBinding
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        db.collection("categories").addSnapshotListener { value, error ->

            val listOfCategory = arrayListOf<CatModel>()
            val data = value?.toObjects(CatModel::class.java)
            listOfCategory.addAll(data!!)

            binding.revCAT.layoutManager = GridLayoutManager(this, 2)
            binding.revCAT.adapter = CatAdapter(this, listOfCategory)

        }

        binding.btnDone.setOnClickListener {
            if (binding.editName.text.toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show()
            } else if (binding.editLink.text.toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Your Link", Toast.LENGTH_SHORT).show()
            } else {
                addDatatoDB(binding.editName.text.toString(), binding.editLink.text.toString())
            }
        }
    }


    private fun addDatatoDB(name: String, link: String) {

        val uid = db.collection("categories").document().id

        val finalData = CatModel(id = uid, name = name, link = link)

        db.collection("categories").document(uid).set(finalData).addOnCompleteListener {

            if (it.isSuccessful) {
                Toast.makeText(this, "Wallpaper Added Successful", Toast.LENGTH_SHORT).show()
                binding.editName.setText("")
                binding.editName.clearFocus()
                binding.editLink.setText("")
                binding.editLink.clearFocus()
            } else {
                Toast.makeText(this, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            binding.editLink.setText("")
            binding.editLink.clearFocus()
            binding.editName.setText("")
            binding.editName.clearFocus()
        }

    }
}