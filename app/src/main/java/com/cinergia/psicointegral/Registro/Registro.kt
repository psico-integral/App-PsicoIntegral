package com.cinergia.psicointegral.Registro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cinergia.psicointegral.R
import com.cinergia.psicointegral.Terminos.Terminos

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nombreEmpresa = intent.getStringExtra("nombre_empresa")
        Log.d("Login", "Nombre de la empresa: $nombreEmpresa")

        val Empleado = findViewById<AppCompatEditText>(R.id.EtNombre)
        val btnRegistro = findViewById<Button>(R.id.btnResgistro)

        btnRegistro.setOnClickListener {
            val nombreEmpleado = Empleado.text.toString().trim()
            if (nombreEmpleado.isNotEmpty()) {
                goToEncuesta(nombreEmpleado, nombreEmpresa)
            } else {
                Empleado.error = "Por favor ingresa tu nombre"
            }
        }
    }

    private fun goToEncuesta(nombreEmpleado: String?, nombreEmpresa: String?) {
        val intent = Intent(this, Terminos::class.java)
        intent.putExtra("nombre_empresa", nombreEmpresa)
        intent.putExtra("nombre_empleado", nombreEmpleado)
        startActivity(intent)
        finish()
    }
}