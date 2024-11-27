package com.example.myelegantapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login) // Enlaza con `login.xml`

        val etLoginEmail = findViewById<EditText>(R.id.etLoginEmail)
        val etLoginPassword = findViewById<EditText>(R.id.etLoginPassword)
        val btnLoginUser = findViewById<Button>(R.id.btnLoginUser)

        val db = DatabaseHelper(this)

        btnLoginUser.setOnClickListener {
            val email = etLoginEmail.text.toString()
            val password = etLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val isValidUser = db.checkUser(email, password)
                if (isValidUser) {
                    // Recuperar el nombre del usuario
                    val userDetails = db.getUserDetails(email)
                    val intent = Intent(this, HomeActivity::class.java).apply {
                        putExtra("USER_NAME", userDetails?.get(0)) // Nombre
                        putExtra("USER_LASTNAME", userDetails?.get(1)) // Apellido
                    }
                    startActivity(intent)
                    finish() // Cierra esta actividad
                } else {
                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
