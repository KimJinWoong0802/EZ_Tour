package com.example.ez_tour

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.net.URL


class RecyclerAdapter(private val items: ArrayList<RecycleData>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener {it ->
            Toast.makeText(it.context, "Clicked:", Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.viewholder, parent, false)
        return RecyclerAdapter.ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: View.OnClickListener, item: RecycleData) {
            view.view_reimage
            view.view_retag
            view.text_rename
            view.setOnClickListener(listener)
        }
    }
    }




    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(listener: View.OnClickListener, item: RecycleData) {
        }
    }
}