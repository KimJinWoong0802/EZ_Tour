package com.example.ez_tour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class RecycleAdapter(private val item: ArrayList<RecycleData>) :
    RecyclerView.Adapter<RecycleAdapter.ViewHolder>(){


    override fun getItemCount(): Int = items.size
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecycleAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}