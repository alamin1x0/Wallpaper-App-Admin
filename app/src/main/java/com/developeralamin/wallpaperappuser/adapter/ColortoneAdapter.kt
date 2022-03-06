package com.developeralamin.wallpaperappuser.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.developeralamin.loveadmindashaboar.model.ColortoneModel
import com.developeralamin.wallpaperappuser.R
import com.google.firebase.firestore.FirebaseFirestore

class ColortoneAdapter(
    val requireContext: Context,
    val listTheColorTone: ArrayList<ColortoneModel>,
) :
    RecyclerView.Adapter<ColortoneAdapter.bomViewHolder>() {

    val db = FirebaseFirestore.getInstance()

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardBack = itemView.findViewById<CardView>(R.id.item_card)
        val btnDelete = itemView.findViewById<ImageView>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {

        return bomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_color_tone, parent, false)
        )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {

        val color = listTheColorTone[position].color
        holder.cardBack.setCardBackgroundColor(Color.parseColor(color!!))

        holder.btnDelete.setOnClickListener {

            val dialog = AlertDialog.Builder(requireContext)
            dialog.setMessage("Are you sure want to delete?")
            dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                db.collection("thecolortone").document(listTheColorTone[position].id).delete()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(requireContext,
                                "Color Deleted Successful",
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

    override fun getItemCount() = listTheColorTone.size
}