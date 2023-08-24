package com.kevin.notesapp.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.PopupMenu.OnMenuItemClickListener
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kevin.notesapp.Entity.NotesEntity
import com.kevin.notesapp.R
import com.kevin.notesapp.databinding.ItemnotesBinding

class NotesAdapter(updatePin: (NotesEntity) -> Unit, Update: (NotesEntity) -> Unit, Delete: (NotesEntity) -> Unit) : Adapter<NotesAdapter.NotesHolder>() {

    lateinit var context: Context
    var notes = ArrayList<NotesEntity>()
    var updatePin = updatePin
    var Update = Update
    var Delete = Delete

    class  NotesHolder(itemView: ItemnotesBinding) : ViewHolder(itemView.root) {
        var binding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        context= parent.context
        var binding = ItemnotesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesHolder, @SuppressLint("RecyclerView") position: Int) {
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

        holder.itemView.setOnLongClickListener(object : OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {

                var menu = PopupMenu(context, holder.itemView)
                menu.menuInflater.inflate(R.menu.itemmenu ,menu.menu)

                menu.setOnMenuItemClickListener(object : OnMenuItemClickListener{
                    override fun onMenuItemClick(p0: MenuItem): Boolean {

                        if (p0?.itemId == R.id.edit) {
                            Update.invoke(notes.get(position))
                        }
                        if (p0?.itemId == R.id.delete) {
                            Delete.invoke(notes.get(position))
                        }
                        return true
                    }
                })
                menu.show()
                return true
            }
        })
    }

    fun update(notes: List<NotesEntity>) {
        this.notes = notes as ArrayList<NotesEntity>
        notifyDataSetChanged()
    }

}