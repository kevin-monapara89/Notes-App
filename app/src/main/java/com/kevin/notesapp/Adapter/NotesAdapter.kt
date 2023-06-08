package com.kevin.notesapp.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.internal.NavigationMenuItemView
import com.kevin.notesapp.Entity.NotesEntity
import com.kevin.notesapp.databinding.ItemnotesBinding

class NotesAdapter(notes : List<NotesEntity>) : Adapter<NotesAdapter.NotesHolder>() {

    var notes = notes
    class  NotesHolder(itemView: ItemnotesBinding) : ViewHolder(itemView.root) {
        var binding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        var binding = ItemnotesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.binding.apply {
            notes.get(position).apply {
                txttitle.text = title
                txttext.text = text
                txtdate.text = date
                txtdate.text = month
                txtdate.text = year
                txttime.text = hour
                txttime.text = minute
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(notes: List<NotesEntity>) {
        this.notes = notes
        notifyDataSetChanged()
    }
}