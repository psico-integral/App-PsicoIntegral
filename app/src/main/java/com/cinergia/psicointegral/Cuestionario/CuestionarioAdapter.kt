package com.cinergia.psicointegral.Cuestionario

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cinergia.psicointegral.R
import com.cinergia.psicointegral.databinding.ItemPreguntaBinding

class CuestionarioAdapter(
    private val preguntas: List<Pair<String, Pregunta>>,
    private val onRespuestaSeleccionada: (String, String) -> Unit,
    var respuestasGuardadas: Map<String, String>,
    private val onAvanzarPregunta: () -> Unit,
    var indicePreguntaActual: Int
) : RecyclerView.Adapter<CuestionarioAdapter.PreguntaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntaViewHolder {
        val binding = ItemPreguntaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreguntaViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: PreguntaViewHolder, position: Int) {
        if (indicePreguntaActual in preguntas.indices) {
            val (id, pregunta) = preguntas[indicePreguntaActual]
            holder.bind(id, pregunta)
        }
    }

    fun actualizarRespuestas(nuevasRespuestas: Map<String, String>) {
        respuestasGuardadas = nuevasRespuestas
        notifyDataSetChanged()
    }

    inner class PreguntaViewHolder(private val binding: ItemPreguntaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(id: String, pregunta: Pregunta) {
            binding.textPregunta.text = pregunta.pregunta
            binding.textPregunta.setTextColor(
                ContextCompat.getColor(binding.root.context, R.color.morado_oscuro)
            )

            val radioGroup = binding.radioGroup
            radioGroup.removeAllViews()

            val opciones = when (pregunta.tipo.lowercase()) {
                "si_no" -> listOf("Sí", "No")
                "frecuencia" -> listOf("Nunca", "Rara vez", "Algunas veces", "Frecuentemente", "Siempre")
                else -> listOf("No contestó")
            }

            var selectedId = respuestasGuardadas[id] ?: ""

            for (opcion in opciones) {
                val radioButton = RadioButton(binding.root.context).apply {
                    text = opcion
                    textSize = 16f
                    setTextColor(ContextCompat.getColor(context, R.color.morado_oscuro))
                    isChecked = selectedId == opcion
                }

                radioGroup.addView(radioButton)
            }

            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val selected = group.findViewById<RadioButton>(checkedId)
                if (selected != null) {
                    val respuesta = selected.text.toString()
                    if (respuesta != selectedId) {
                        selectedId = respuesta
                        onRespuestaSeleccionada(id, respuesta)
                        onAvanzarPregunta()
                    }
                }
            }
        }
    }
}

