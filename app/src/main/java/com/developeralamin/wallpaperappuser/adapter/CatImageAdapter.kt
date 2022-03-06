package com.developeralamin.loveadmindashaboar.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developeralamin.loveadmindashaboar.model.BomModel
import com.developeralamin.wallpaperappuser.R
import com.developeralamin.wallpaperappuser.ui.finalWallpaper.FinalWallpaperActivity
import com.google.firebase.firestore.FirebaseFirestore

import com.makeramen.roundedimageview.RoundedImageView

class CatImageAdapter(
    val requireContext: Context,
    val listBesOfTheMonth: ArrayList<BomModel>,
    val uid: String,
) :
    RecyclerView.Adapter<CatImageAdapter.bomViewHolder>() {

    val db = FirebaseFirestore.getInstance()

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.catImage)
        val btnDelete = itemView.findViewById<ImageView>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {

        return bomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_wallpaper, parent, false)
        )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {

        Glide.with(requireContext).load(listBesOfTheMonth[position].link).into(holder.imageView)

        holder.btnDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext)
            dialog.setMessage("Are you sure want to delete?")
            dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->

                db.collection("categories")
                    .document(uid).collection("wallpaper")
                    .document(listBesOfTheMonth[position].id).delete().addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(requireContext,
                                "Wallpaper Deleted Successful",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext,
                                it.exception?.localizedMessage,
                                Toast.LENGTH_SHORT).show()
                        }
                    }

                dialog.dismiss()
            })
            dialog.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            dialog.show()
        }
    }


    override fun getItemCount() = listBesOfTheMonth.size
}