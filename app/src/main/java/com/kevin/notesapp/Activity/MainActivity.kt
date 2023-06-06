package com.kevin.notesapp.Activity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.kevin.notesapp.Adapter.NotesAdapter
import com.kevin.notesapp.Database.RoomDB
import com.kevin.notesapp.Entity.NotesEntity
import com.kevin.notesapp.R
import com.kevin.notesapp.databinding.ActivityMainBinding
import com.kevin.notesapp.databinding.DialogaddBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var db : RoomDB
    lateinit var binding: ActivityMainBinding
    lateinit var adapter : NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = RoomDB.init(this)

        initview()
    }

    private fun initview() {
        binding.add.setOnClickListener{
            addNoteDialog()
        }

        adapter = NotesAdapter(db.note().getNotes())
        binding.notelist.layoutManager = GridLayoutManager(this, 2)
        binding.notelist.adapter = NotesAdapter(db.note().getNotes())
    }

    private fun addNoteDialog() {
        var dialog = Dialog(this)
        var bind = DialogaddBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.btnsubmit.setOnClickListener {

            var title = bind.edttitle.text.toString()
            var text = bind.edttext.text.toString()
            var format = SimpleDateFormat("DD/MM/YYYY hh:mm")
            var current = format.format(Date())
            var data = NotesEntity(title, text, current)
            db.note().addNote(data)
            adapter.update(db.note().getNotes())
            dialog.dismiss()
        }
        dialog.show()
    }

}