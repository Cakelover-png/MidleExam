package com.example.midleexam

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PrintDataActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences


    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    lateinit var town1TextView: TextView
    lateinit var town2TextView: TextView
    lateinit var town3TextView: TextView
    lateinit var btnshow:Button
    var town1:String?=null
    var town2:String?=null
    var town3:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print_data)

        town1TextView = findViewById(R.id.town1_text_view)
        town2TextView = findViewById(R.id.town2_text_view)
        town3TextView = findViewById(R.id.town3_text_view)
        btnshow=findViewById(R.id.btnshowWather)

        sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)


        ///

        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val userRef = database.child("users").child(currentUserUid).child("towns")
        userRef.get()
            .addOnSuccessListener { snapshot ->
                val towns = snapshot.value as Map<String, String>?
                if (towns != null) {
                    town1 = towns["town1"]
                    town2 = towns["town2"]
                    town3= towns["town3"]

                    town1TextView.text=town1.toString()
                    town2TextView.text=town2.toString()
                    town3TextView.text=town3.toString()
                }
            }
            .addOnFailureListener {
                Log.e("TAG", "Failed to retrieve user data: ${it.message}")
            }

        ////

        btnshow.setOnClickListener{
            // Save a string to shared preferences
            val editor = sharedPreferences.edit()
            editor.putString("town_1", town1)
            editor.putString("town_2", town2)
            editor.putString("town_3", town3)
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)




        }


    }
}