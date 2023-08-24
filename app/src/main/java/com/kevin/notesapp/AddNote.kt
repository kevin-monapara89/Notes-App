package com.kevin.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kevin.notesapp.Activity.MainActivity
import com.kevin.notesapp.Adapter.NotesAdapter
import com.kevin.notesapp.Database.RoomDB
import com.kevin.notesapp.Entity.NotesEntity
import com.kevin.notesapp.databinding.ActivityAddNoteBinding
import com.kevin.notesapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNote : AppCompatActivity() {
    lateinit var binding: ActivityAddNoteBinding
    lateinit var db: RoomDB
    lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = RoomDB.init(this)

        binding.txtdate.setText(
            SimpleDateFormat("EE, dd MMMM | hh:mm a", Locale.getDefault())
                .format(Date())
        )

        binding.btnsubmit.setOnClickListener {

            var title = binding.edttitle.text.toString()
            var text = binding.edttext.text.toString()
            var date = binding.txtdate.text.toString()
            var data = NotesEntity(title, text, date, false)
            db.note().addNote(data)
            adapter.update(db.note().getNotes())
            finish()

            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }

        binding.CircleBtn.setOnClickListener {
        }
    }
}