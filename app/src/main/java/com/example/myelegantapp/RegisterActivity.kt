package com.example.myelegantapp
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register) // Enlaza con `register.xml`

        val etName = findViewById<EditText>(R.id.etName)
        val etLastName = findViewById<EditText>(R.id.etLastName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegisterUser = findViewById<Button>(R.id.btnRegisterUser)

        val db = DatabaseHelper(this)

        btnRegisterUser.setOnClickListener {
            val name = etName.text.toString()
            val lastName = etLastName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (name.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val success = db.registerUser(name, lastName, email, password)
                if (success) {
                    Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show()
                    finish() // Cierra esta actividad y vuelve a WelcomeActivity
                } else {
                    Toast.makeText(this, "Registration Failed (Email already exists)", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
