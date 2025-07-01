package com.cinergia.psicointegral.Terminos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cinergia.psicointegral.Cuestionario.CuestionarioActivity
import com.cinergia.psicointegral.R

class Terminos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_termininos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nombreEmpresa = intent.getStringExtra("nombre_empresa") ?: "empresa_desconocida"
        val nombreEmpleado = intent.getStringExtra("nombre_empleado") ?: "empleado_desconocido"

        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)
        val cbTerminos = findViewById<CheckBox>(R.id.cbTerminos)

        btnSiguiente.setOnClickListener {
            if (cbTerminos.isChecked) {
                goToEncuestas(nombreEmpleado, nombreEmpresa)
            } else {
                Toast.makeText(this, "Debe aceptar los términos y condiciones para continuar.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun goToEncuestas(nombreEmpleado: String?, nombreEmpresa: String?) {
        val intent = Intent(this, CuestionarioActivity::class.java)
        intent.putExtra("nombre_empresa", nombreEmpresa)
        intent.putExtra("nombre_empleado", nombreEmpleado)
        startActivity(intent)
        finish()
    }
}