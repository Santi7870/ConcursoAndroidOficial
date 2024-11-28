package com.example.myelegantapp

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var btnBlock: Button
    private lateinit var btnUnblock: Button
    private lateinit var tvWelcomeMessage: TextView
    private lateinit var tvLockStatus: TextView  // Nuevo TextView para mostrar la hora de bloqueo/desbloqueo
    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var adminComponentName: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inicialización de vistas
        btnBlock = findViewById(R.id.btnBlock)
        btnUnblock = findViewById(R.id.btnUnblock)
        tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage)
        tvLockStatus = findViewById(R.id.tvLockStatus)  // Referencia al nuevo TextView

        // Inicializar el administrador de dispositivos
        devicePolicyManager = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
        adminComponentName = ComponentName(this, MyAdminReceiver::class.java)

        // Obtener datos de la intención
        val userName = intent.getStringExtra("USER_NAME")
        val userLastName = intent.getStringExtra("USER_LASTNAME")
        tvWelcomeMessage.text = "Welcome, $userName $userLastName!"

        // Establecer el comportamiento del botón "Bloquear"
        btnBlock.setOnClickListener {
            // Activar el modo kiosco
            startLockTask() // Esto activa el bloqueo de la app
            btnBlock.visibility = View.GONE
            btnUnblock.visibility = View.VISIBLE

            // Actualizar el estado de bloqueo con la hora actual
            updateLockStatus("Bloqueado")
        }

        // Establecer el comportamiento del botón "Desbloquear"
        btnUnblock.setOnClickListener {
            // Salir del modo kiosco
            stopLockTask() // Esto desactiva el bloqueo de la app
            btnUnblock.visibility = View.GONE
            btnBlock.visibility = View.VISIBLE

            // Actualizar el estado de desbloqueo con la hora actual
            updateLockStatus("Desbloqueado")
        }
    }

    // Sobrescribir onBackPressed() para evitar que el usuario salga con el botón de retroceso
    override fun onBackPressed() {
        // No hacer nada al presionar el botón de retroceso
        // Esto deshabilita efectivamente el botón de retroceso
    }

    // Método para manejar la multitarea si es necesario
    override fun onPause() {
        super.onPause()
        // Verificar si la app está en modo kiosco y asegurarse de que no pueda salir
        if (isInLockTaskMode()) {
            startLockTask() // Reactivar el modo kiosco si la app se pausa
        }
    }

    // Método para verificar si la app está en modo kiosco
    private fun isInLockTaskMode(): Boolean {
        return devicePolicyManager.isLockTaskPermitted(packageName)
    }

    // Método para actualizar el TextView con la hora de bloqueo/desbloqueo
    private fun updateLockStatus(status: String) {
        // Obtener la hora y fecha actual
        val currentTime = System.currentTimeMillis()
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val formattedDate = formatter.format(Date(currentTime))

        // Actualizar el TextView con la hora y el estado
        tvLockStatus.text = "Estado de bloqueo: $status\nHora: $formattedDate"
    }
}




