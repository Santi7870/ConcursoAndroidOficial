package com.example.myelegantapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var btnBlock: Button
    private lateinit var btnUnblock: Button
    private lateinit var tvWelcomeMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnBlock = findViewById(R.id.btnBlock)
        btnUnblock = findViewById(R.id.btnUnblock)
        tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage)

        val userName = intent.getStringExtra("USER_NAME")
        val userLastName = intent.getStringExtra("USER_LASTNAME")

        tvWelcomeMessage.text = "Welcome, $userName $userLastName!"

        // Al hacer clic en el botón BLOCK, ocultamos la barra de navegación y de notificaciones
        btnBlock.setOnClickListener {
            blockUI()
            btnBlock.visibility = View.GONE
            btnUnblock.visibility = View.VISIBLE
        }

        // Al hacer clic en el botón UNBLOCK, restauramos la visibilidad normal
        btnUnblock.setOnClickListener {
            unblockUI()
            btnUnblock.visibility = View.GONE
            btnBlock.visibility = View.VISIBLE
        }
    }

    // Método para bloquear la interfaz: ocultar las barras de navegación y de notificaciones
    private fun blockUI() {
        // Hacemos que la interfaz sea completamente inmersiva, ocultando las barras de estado y de navegación
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }

    // Método para desbloquear la interfaz: restaurar las barras de navegación y de notificaciones
    private fun unblockUI() {
        // Restauramos la visibilidad de las barras
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}



