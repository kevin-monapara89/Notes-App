package com.kevin.notesapp.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import com.kevin.notesapp.Adapter.NotesAdapter
import com.kevin.notesapp.AddNote
import com.kevin.notesapp.Dao.NoteDao
import com.kevin.notesapp.Database.RoomDB
import com.kevin.notesapp.Entity.NotesEntity
import com.kevin.notesapp.databinding.ActivityAddNoteBinding
import com.kevin.notesapp.databinding.ActivityMainBinding
import com.kevin.notesapp.databinding.DialogaddBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var db: RoomDB
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = RoomDB.init(this)

        initview()
    }

    private fun initview() {

        adapter = NotesAdapter({
            var isPin = false
            if (it.pin) {
                isPin = false
            } else {
                isPin = true
            }
            var data = NotesEntity(it.title, it.text, it.date, isPin)
            data.id = it.id
            db.note().updateNote(data)
            adapter.update(filterNote(db.note().getNotes()))
        }, {
            editNoteDialog(it)
        }) {
            deletenoteDialog(it)
        }

        binding.add.setOnClickListener {
            val mainIntent = Intent(this, AddNote::class.java)
            startActivity(mainIntent)
        }

        adapter.update(db.note().getNotes())
        binding.notelist.layoutManager = GridLayoutManager(this, 2)
        binding.notelist.adapter = adapter

    }

    private fun deletenoteDialog(it: NotesEntity) {

            var dialog = AlertDialog.Builder(this)
                .setTitle("Delet Transaction!!")
                .setMessage("Are You Sure?")
                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        db.note().deleteNote(it)
                        adapter.update(filterNote(db.note().getNotes()))
                    }
                }).setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {

                    }
                }).create()
            dialog.show()
    }

    private fun editNoteDialog(it: NotesEntity) {
        var dialog = Dialog(this)
        var bind = DialogaddBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.txtdate.setText(
            SimpleDateFormat("EE, dd MMMM || hh:mm a", Locale.getDefault())
                .format(Date())
        )

        bind.btnsubmit.setOnClickListener {

            var title = bind.edttitle.text.toString()
            var text = bind.edttext.text.toString()
            var date = bind.txtdate.text.toString()
            var data = NotesEntity(title, text, date, false)
            db.note().addNote(data)
            adapter.update(db.note().getNotes())
            dialog.dismiss()
        }

        dialog.show()

    }

    fun filterNote(notes: List<NotesEntity>): List<NotesEntity> {

        var newlist = ArrayList<NotesEntity>()
        for (l in notes) {
            if (l.pin) {
                newlist.add(l)
            }
        }
        for (l in notes) {
            if (!l.pin) {
                newlist.add(l)
            }
        }
        return newlist
    }
}
