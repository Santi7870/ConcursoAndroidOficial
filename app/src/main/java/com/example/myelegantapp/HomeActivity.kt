package com.example.myelegantapp

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myelegantapp.model.BlockLog
import com.example.myelegantapp.network.ApiService
import com.example.myelegantapp.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var btnBlock: Button
    private lateinit var btnUnblock: Button
    private lateinit var tvWelcomeMessage: TextView
    private lateinit var tvLockStatus: TextView  // Nuevo TextView para mostrar la hora de bloqueo/desbloqueo
    private lateinit var devicePolicyManager: DevicePolicyManager
    private var isBlocked = false  // Variable para saber si la app está bloqueada

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

        // Obtener datos de la intención
        val userName = intent.getStringExtra("USER_NAME") ?: "Juan"
        val userLastName = intent.getStringExtra("USER_LASTNAME") ?: "Pérez"
        tvWelcomeMessage.text = "Welcome, $userName $userLastName!"

        // Establecer el comportamiento del botón "Bloquear"
        btnBlock.setOnClickListener {
            // Activar el modo kiosco
            startLockTask()  // Esto activa el bloqueo de la app
            btnBlock.visibility = View.GONE
            btnUnblock.visibility = View.VISIBLE
            sendDataToApi("Bloqueado")  // Enviar datos con Retrofit

            // Actualizar el estado de bloqueo con la hora actual
            updateLockStatus("Bloqueado")
            isBlocked = true
        }

        // Establecer el comportamiento del botón "Desbloquear"
        btnUnblock.setOnClickListener {
            // Salir del modo kiosco
            stopLockTask()  // Esto desactiva el bloqueo de la app
            btnUnblock.visibility = View.GONE
            btnBlock.visibility = View.VISIBLE
            sendDataToApi("Desbloqueado")  // Enviar datos con Retrofit

            // Actualizar el estado de desbloqueo con la hora actual
            updateLockStatus("Desbloqueado")
            isBlocked = false
        }
    }

    // Sobrescribir onBackPressed() para evitar que el usuario salga con el botón de retroceso
    override fun onBackPressed() {
        // No hacer nada al presionar el botón de retroceso
        // Esto deshabilita efectivamente el botón de retroceso
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

    // Método para enviar datos de bloqueo/desbloqueo a la API
    fun sendDataToApi(status: String) {
        // Obtener los datos del usuario y la fecha actual
        val userName = intent.getStringExtra("USER_NAME") ?: "Juan"
        val userLastName = intent.getStringExtra("USER_LASTNAME") ?: "Pérez"
        val currentTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(Date())

        // Crear el objeto BlockLog con los datos obtenidos
        val blockLog = BlockLog(
            userName = userName,
            userLastName = userLastName,
            status = status,
            lockTime = currentTime
        )

        // Llamada a Retrofit para enviar los datos
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Enviar el objeto BlockLog a la API
                val response = RetrofitInstance.retrofitService.postBlockLog(blockLog)

                // Regresar al hilo principal para actualizar la UI
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        // Los datos se enviaron correctamente
                        Log.d("API", "Datos enviados correctamente: ${response.body()}")
                    } else {
                        // Ocurrió un error al enviar los datos
                        Log.e("API", "Error al enviar los datos: ${response.errorBody()?.string()}")
                    }
                }
            } catch (e: Exception) {
                // En caso de un error en la conexión
                Log.e("API", "Error al hacer la solicitud: ${e.message}")
            }
        }
    }
}














