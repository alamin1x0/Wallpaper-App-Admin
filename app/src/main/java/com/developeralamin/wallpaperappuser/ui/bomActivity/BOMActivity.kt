package com.developeralamin.wallpaperappuser.ui.bomActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.developeralamin.loveadmindashaboar.model.BomModel
import com.developeralamin.wallpaperappuser.R
import com.developeralamin.wallpaperappuser.adapter.BomAdapter
import com.developeralamin.wallpaperappuser.databinding.ActivityBomactivityBinding
import com.google.firebase.firestore.FirebaseFirestore

class BOMActivity : AppCompatActivity() {
    lateinit var binding: ActivityBomactivityBinding
    lateinit var bd: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBomactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bd = FirebaseFirestore.getInstance()

        bd.collection("bestofmonth").addSnapshotListener { value, error ->
            val listBesOfTheMonth = arrayListOf<BomModel>()
            val data = value?.toObjects(BomModel::class.java)
            listBesOfTheMonth.addAll(data!!)

            binding.revBOM.layoutManager =
                GridLayoutManager(this, 3)
            binding.revBOM.adapter = BomAdapter(this, listBesOfTheMonth)

        }

        binding.btnDone.setOnClickListener {
            if (binding.editLink.text.toString().isEmpty()) {
                Toast.makeText(this, "Past Your Link", Toast.LENGTH_SHORT).show()
            } else {
                addLinkToDatabase(binding.editLink.text.toString())
            }
        }
    }

    private fun addLinkToDatabase(wallpaperLink: String) {
        val uid = bd.collection("bestofmonth").document().id
        val finalData = BomModel(uid, wallpaperLink)

        bd.collection("bestofmonth").document(uid).set(finalData)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Wallpaper Added Successful", Toast.LENGTH_SHORT).show()
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