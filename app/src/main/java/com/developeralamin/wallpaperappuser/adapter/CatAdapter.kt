package com.developeralamin.wallpaperappuser.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developeralamin.loveadmindashaboar.model.CatModel
import com.developeralamin.wallpaperappuser.R
import com.developeralamin.wallpaperappuser.ui.finalWallpaper.FinalWallpaperActivity
import com.google.firebase.firestore.FirebaseFirestore


class CatAdapter(val requireContext: Context, val listOfCategory: ArrayList<CatModel>) :
    RecyclerView.Adapter<CatAdapter.bomViewHolder>() {

    val db = FirebaseFirestore.getInstance()

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.cat_img)
        val name = itemView.findViewById<TextView>(R.id.cat_name)
        val btnDelete = itemView.findViewById<ImageView>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {

        return bomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_cat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {
        holder.name.text = listOfCategory[position].name
        Glide.with(requireContext).load(listOfCategory[position].link).into(holder.imageView)

        holder.imageView.setOnClickListener {
            val intent = Intent(requireContext, FinalWallpaperActivity::class.java)
            intent.putExtra("uid",listOfCategory[position].id)
            intent.putExtra("name",listOfCategory[position].name)
            requireContext.startActivity(intent)
        }


        holder.btnDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext)
            dialog.setMessage("Are you sure want to delete?")
            dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->

                db.collection("categories").document(listOfCategory[position].id).delete()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(requireContext,
                                "Category Deleted Successful",
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

    override fun getItemCount() = listOfCategory.size
}