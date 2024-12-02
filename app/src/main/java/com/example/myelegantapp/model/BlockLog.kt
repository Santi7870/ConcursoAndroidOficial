package com.example.myelegantapp.model  // Colócala en el paquete que prefieras

// Clase de datos para el bloque de registro
data class BlockLog(
    val userName: String,           // Nombre de usuario
    val userLastName: String,       // Apellido del usuario
    val status: String,             // Estado de bloqueo/desbloqueo (ej. "Bloqueado", "Desbloqueado")
    val lockTime: String            // Hora y fecha de bloqueo/desbloqueo
)
