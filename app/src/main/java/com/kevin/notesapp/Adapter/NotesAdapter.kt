package com.kevin.notesapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kevin.notesapp.Entity.NotesEntity
import com.kevin.notesapp.R
import com.kevin.notesapp.databinding.ItemnotesBinding

class NotesAdapter(updatePin: (NotesEntity) -> Unit) : Adapter<NotesAdapter.NotesHolder>() {

    var notes = ArrayList<NotesEntity>()
    var updatePin = updatePin

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

                if (pin){
                    imgpin.setImageResource(R.drawable.pin)
                }else{
                    imgpin.setImageResource(R.drawable.unpin)
                }

                imgpin.setOnClickListener {
                    updatePin.invoke(notes.get(position))
                }
            }
        }
    }

    fun update(notes: List<NotesEntity>) {
        this.notes = notes as ArrayList<NotesEntity>
        notifyDataSetChanged()
    }

    fun setNotes(notes: List<NotesEntity>) {
        this.notes = notes as ArrayList<NotesEntity>
    }
}