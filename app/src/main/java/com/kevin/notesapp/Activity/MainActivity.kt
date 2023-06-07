package com.kevin.notesapp.Activity

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
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

        fun adddata(notesEntity: NotesEntity) {
            var dialog = Dialog(this)
            var bind = DialogaddBinding.inflate(layoutInflater)
            dialog.setContentView(bind.root)

        }
    }

    fun addNoteDialog() {
        var dialog = Dialog(this)
        var bind = DialogaddBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)
        bind.edtdate.setOnClickListener {

            var date = Date()

            var format1 = SimpleDateFormat("dd-MM-YYYY")
            var currentDate = format1.format(date)

            var dates = currentDate.split("-")
            bind.edtdate.text = currentDate

            var dialog =
                DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

                        var Year = p1
                        var Month = p2 + 1
                        var Date = p3

                        var selectedDate = "$p3-${(p2 + 1)}-$p1"
                        bind.edtdate.text = selectedDate
                    }

                }, dates[2].toInt(), dates[1].toInt() - 1, dates[0].toInt())
            dialog.show()
        }

        bind.edttime.setOnClickListener {

                var date = Date()

                var format2 = SimpleDateFormat("hh:mm a")
                var currentTime = format2.format(date)

                bind.edttime.text = currentTime
                var seleTime = currentTime

                var dialog1 = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {

                    }

                }, 10, 0, false)

                dialog1.show()
            }

            bind.btnsubmit.setOnClickListener {

            var title = bind.edttitle.text.toString()
            var text = bind.edttext.text.toString()
            var date = bind.edtdate.text.toString()
            var month = bind.edtdate.text.toString()
            var year = bind.edtdate.text.toString()
            var format = SimpleDateFormat("DD/MM/YYYY hh:mm")
            var current = format.format(Date())
            var data = NotesEntity(title, text, date , month , year)
            db.note().addNote(data)
            adapter.update(db.note().getNotes())
            dialog.dismiss()
        }
        dialog.show()
    }

}