package com.example.soundnova

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity

class HomeActivity : ComponentActivity() {
    private lateinit var editTextSearch: EditText
    private lateinit var tabRecent: TextView
    private lateinit var tabTop50 : TextView
    private lateinit var tabChill: TextView
    private lateinit var tabRnB : TextView
    private lateinit var tabFestival: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        editTextSearch = findViewById(R.id.editTextSearch)
        tabRecent = findViewById(R.id.tabRecent)
        tabTop50 = findViewById(R.id.tabTop50)
        tabChill = findViewById(R.id.tabChill)
        tabFestival = findViewById(R.id.tabFestival)
        tabRnB = findViewById(R.id.tabRnB)

        tabRecent.setOnClickListener {
            var keyword : String? = null
            keyword = editTextSearch.text.toString()

            //dataset =
        }
    }

}