package com.example.midleexam

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import com.example.midleexam.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private lateinit var userEmail:String
    lateinit var saveButton: Button
    lateinit var town1EditText: EditText
    lateinit var town2EditText: EditText
    lateinit var town3EditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        saveButton=findViewById(R.id.button_save)
        town3EditText=findViewById(R.id.editText_town3)
        town2EditText=findViewById(R.id.editText_town2)
        town1EditText=findViewById(R.id.editText_town1)

        database = Firebase.database.reference

        auth = FirebaseAuth.getInstance()




        userEmail= intent.getStringExtra("usrEmail").toString()
        saveButton.setOnClickListener {
            val town1 = town1EditText.text.toString().trim()
            val town2 = town2EditText.text.toString().trim()
            val town3 = town3EditText.text.toString().trim()

            val towns = mapOf(
                "town1" to town1,
                "town2" to town2,
                "town3" to town3)

                val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid


            val userRef = database.child("users").child(currentUserUid.toString())


            userRef.child("towns").setValue(towns)
                .addOnSuccessListener {
                    // handle success event
                    Log.d(TAG, "User data saved successfully!")
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    // handle failure event
                    Log.e(TAG, "Failed to save user data: ${it.message}")
                }
        }
    }
}
