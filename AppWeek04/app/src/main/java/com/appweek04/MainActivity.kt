package com.appweek04

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val buttonGreet = findViewById<Button>(R.id.buttonGreet)
        val textviewGreeting = findViewById<TextView>(R.id.textViewGreeting)

        var greeting: String = ""
        buttonGreet.setOnClickListener{
            val name = editTextName.text.toString().trim()

            if(name.isNotEmpty()){
                greeting = "안녕, ${name}님~"
            } else {
                greeting = "너의 이름은?"
            }
            textviewGreeting.text = greeting
            textviewGreeting.visibility = View.VISIBLE
            Log.d("KotlinWeek04App", greeting)
        }
    }
}