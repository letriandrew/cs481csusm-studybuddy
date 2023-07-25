package com.example.studybuddy20
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity2 : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        mAuth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.userEmailET)
        val passwordEditText = findViewById<EditText>(R.id.userPasswordET)
        val loginButton = findViewById<Button>(R.id.login_button)
        val createAccountButton = findViewById<Button>(R.id.create_account_button)
        val guest_button = findViewById<Button>(R.id.guestButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        createAccountButton.setOnClickListener {

            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Account created.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Account creation failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        //when guest button is clicked, go to main activity
        guest_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
