package com.cinergia.psicointegral.Cuestionario

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cinergia.psicointegral.databinding.ActivityCuestionarioBinding
import com.google.firebase.FirebaseApp
import android.content.Intent
import com.cinergia.psicointegral.MainActivity

class CuestionarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCuestionarioBinding
    private val viewModel: CuestionarioViewModel by viewModels()
    private lateinit var adapter: CuestionarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        binding = ActivityCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombreEmpresa = intent.getStringExtra("nombre_empresa") ?: "empresa_desconocida"
        val nombreEmpleado = intent.getStringExtra("nombre_empleado") ?: "empleado_desconocido"

        viewModel.setNombreEmpresaEmpleado(nombreEmpresa, nombreEmpleado)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.isVerticalScrollBarEnabled = true
        binding.recyclerView.scrollBarFadeDuration = 0

        binding.btnEnviarFinal.setOnClickListener {
            viewModel.guardarRespuestasEnFirebase()
            Toast.makeText(this, "Respuestas enviadas correctamente", Toast.LENGTH_SHORT).show()
            viewModel.reiniciarCuestionario()
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        viewModel.claveActual.observe(this) { clave ->
            if (clave == "fin") {
                mostrarPantallaFinal()
            } else {
                actualizarAdapter()
            }
        }

        viewModel.indiceSeccion.observe(this) { actualizarAdapter() }
        viewModel.indicePreguntaActual.observe(this) { actualizarAdapter() }
        viewModel.mostrarSoloPrimeraPregunta.observe(this) { actualizarAdapter() }

        viewModel.finalizado.observe(this) { terminado ->
            if (terminado) mostrarPantallaFinal()
        }

        viewModel.respuestas.observe(this) {
            adapter.actualizarRespuestas(it ?: emptyMap())
            binding.btnEnviarFinal.visibility = if ((it ?: emptyMap()).isNotEmpty()) View.VISIBLE else View.GONE
        }

        if (viewModel.indiceSeccion.value == 0) {
            viewModel.reiniciarCuestionario()
        }
    }

    private fun actualizarAdapter() {
        binding.layoutFinal.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE

        val clave = viewModel.claveActual.value ?: return
        val indiceSeccion = viewModel.indiceSeccion.value ?: 0
        val indicePregunta = viewModel.indicePreguntaActual.value ?: 0

        val cuestionarios = obtenerCuestionarios().cuestionario
        val seccionActual = cuestionarios[clave]?.getOrNull(indiceSeccion) ?: return
        val listaPreguntas = seccionActual.seccion.toList()

        adapter = CuestionarioAdapter(
            preguntas = listaPreguntas,
            onRespuestaSeleccionada = { id, respuesta, tipo ->
                viewModel.guardarRespuesta(id, respuesta, tipo)
            },
            onAvanzarPregunta = {
                viewModel.avanzarPregunta()
            },
            respuestasGuardadas = viewModel.respuestas.value ?: emptyMap(),
            indicePreguntaActual = indicePregunta
        )

        binding.recyclerView.adapter = adapter
    }

    private fun mostrarPantallaFinal() {
        binding.recyclerView.visibility = View.GONE
        binding.layoutFinal.visibility = View.VISIBLE
        binding.txtFinalMensaje.text = "¡Felicidades! Has completado exitosamente el cuestionario."
    }

    override fun onBackPressed() {
        if (binding.layoutFinal.visibility == View.VISIBLE) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            super.onBackPressed()
        }
    }
}


