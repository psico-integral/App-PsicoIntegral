package com.cinergia.psicointegral.Cuestionario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

class CuestionarioViewModel : ViewModel() {

  private val cuestionariosMap = obtenerCuestionarios().cuestionario
  private val clavesCuestionarios = cuestionariosMap.keys.toList()

  private val _claveActual = MutableLiveData(clavesCuestionarios.first())
  val claveActual: LiveData<String> get() = _claveActual

  private val _indiceSeccion = MutableLiveData(0)
  val indiceSeccion: LiveData<Int> get() = _indiceSeccion

  private val _indicePreguntaActual = MutableLiveData(0)
  val indicePreguntaActual: LiveData<Int> get() = _indicePreguntaActual

  private val _respuestas = MutableLiveData<MutableMap<String, String>>(mutableMapOf())
  val respuestas: LiveData<MutableMap<String, String>> get() = _respuestas

  private val _mostrarSoloPrimeraPregunta = MutableLiveData(true)
  val mostrarSoloPrimeraPregunta: LiveData<Boolean> get() = _mostrarSoloPrimeraPregunta

  private val _finalizado = MutableLiveData(false)
  val finalizado: LiveData<Boolean> get() = _finalizado

  private var nombreEmpresa: String = ""
  private var nombreEmpleado: String = ""

  fun setNombreEmpresaEmpleado(empresa: String, empleado: String) {
    nombreEmpresa = empresa
    nombreEmpleado = empleado
  }

  fun iniciarCuestionario(clave: String) {
    _claveActual.value = clave
    _indiceSeccion.value = 0
    _respuestas.value = mutableMapOf()
    _mostrarSoloPrimeraPregunta.value = clave == "cuestionario_01"
    _finalizado.value = false
    reiniciarIndicePregunta()
  }

  fun reiniciarIndicePregunta() {
    _indicePreguntaActual.value = 0
  }

  fun avanzarPregunta() {
    val clave = _claveActual.value ?: return
    val secciones = cuestionariosMap[clave] ?: return
    val seccionIndex = _indiceSeccion.value ?: return
    val preguntasSeccion = secciones[seccionIndex].seccion.keys.toList()
    val actual = _indicePreguntaActual.value ?: 0

    if (actual < preguntasSeccion.size - 1) {
      _indicePreguntaActual.value = actual + 1
    } else {
      if (clave == "cuestionario_01" && seccionIndex == 0 && _mostrarSoloPrimeraPregunta.value == true) {
        val respuestasActuales = _respuestas.value ?: mutableMapOf()
        val todasNo = preguntasSeccion.all { id -> respuestasActuales[id]?.lowercase()?.trim() == "no" }
        if (todasNo) {
          guardarSinContestar()
          _claveActual.value = "fin"
          _finalizado.value = true
        } else {
          _mostrarSoloPrimeraPregunta.value = false
          avanzarSeccion()
        }
      } else {
        when (clave) {
          "cuestionario_02" -> manejarFlujoCuestionario02(seccionIndex)
          "cuestionario_03" -> manejarFlujoCuestionario03(seccionIndex)
          else -> avanzarSeccion()
        }
      }
    }
  }

  fun guardarRespuesta(id: String, respuesta: String, tipo: String) {
    val copia = _respuestas.value ?: mutableMapOf()
    copia[id] = respuesta.trim()
    _respuestas.value = copia.toMutableMap()

    guardarRespuestaEnFirebase(id, respuesta.trim())
  }

  private fun guardarRespuestaEnFirebase(idPregunta: String, respuestaTexto: String) {
    val clave = _claveActual.value ?: return
    val seccionIndex = _indiceSeccion.value ?: return
    val seccionId = "seccion_${seccionIndex + 1}"
    val respuestaNum = convertirRespuestaANumero(respuestaTexto)

    val database = FirebaseDatabase.getInstance("https://psicointegral-usuariorespuesta-default-rtdb.firebaseio.com/")
    val ref = database.reference
      .child(nombreEmpresa)
      .child(nombreEmpleado)
      .child("respuestas")
      .child(clave)
      .child(seccionId)
      .child(idPregunta)

    ref.setValue(respuestaNum)
  }

  private fun manejarFlujoCuestionario02(seccionIndex: Int) {
    val idClave = cuestionariosMap[_claveActual.value]?.getOrNull(seccionIndex)?.seccion?.keys?.firstOrNull() ?: return
    val respuestaClave = _respuestas.value?.get(idClave)?.lowercase()?.trim() ?: return

    when (seccionIndex) {
      0 -> avanzarSeccion()
      1 -> {
        if (respuestaClave == "no") {
          _indiceSeccion.value = 3
          limpiarRespuestasDeSeccion(2)
          limpiarRespuestasDeSeccion(3)
        } else {
          _indiceSeccion.value = 2
          limpiarRespuestasDeSeccion(2)
        }
        reiniciarIndicePregunta()
      }
      3 -> {
        if (respuestaClave == "no") {
          avanzarCuestionario()
        } else {
          _indiceSeccion.value = 4
          limpiarRespuestasDeSeccion(4)
          reiniciarIndicePregunta()
        }
      }
      4 -> avanzarCuestionario()
      else -> avanzarSeccion()
    }
  }

  private fun manejarFlujoCuestionario03(seccionIndex: Int) {
    val idClave = cuestionariosMap[_claveActual.value]?.getOrNull(seccionIndex)?.seccion?.keys?.firstOrNull() ?: return
    val respuestaClave = _respuestas.value?.get(idClave)?.lowercase()?.trim() ?: return

    when (seccionIndex) {
      1 -> {
        if (respuestaClave == "no") {
          _indiceSeccion.value = 3
          limpiarRespuestasDeSeccion(2)
          limpiarRespuestasDeSeccion(3)
        } else {
          _indiceSeccion.value = 2
          limpiarRespuestasDeSeccion(2)
        }
        reiniciarIndicePregunta()
      }
      3 -> {
        if (respuestaClave == "no") {
          _claveActual.value = "fin"
          _finalizado.value = true
        } else {
          _indiceSeccion.value = 4
          limpiarRespuestasDeSeccion(4)
          reiniciarIndicePregunta()
        }
      }
      4 -> {
        _claveActual.value = "fin"
        _finalizado.value = true
      }
      else -> avanzarSeccion()
    }
  }

  fun avanzarSeccion() {
    val clave = _claveActual.value ?: return
    val indice = (_indiceSeccion.value ?: 0) + 1
    val secciones = cuestionariosMap[clave] ?: return

    if (indice < secciones.size) {
      limpiarRespuestasDeSeccion(indice)
      _indiceSeccion.value = indice
      reiniciarIndicePregunta()
    } else {
      avanzarCuestionario()
    }
  }

  fun avanzarCuestionario() {
    val actual = clavesCuestionarios.indexOf(_claveActual.value)
    val siguiente = actual + 1

    if (siguiente < clavesCuestionarios.size) {
      iniciarCuestionario(clavesCuestionarios[siguiente])
    } else {
      _claveActual.value = "fin"
      _finalizado.value = true
    }
  }

  private fun limpiarRespuestasDeSeccion(seccionIndex: Int) {
    val respuestasActuales = _respuestas.value ?: mutableMapOf()
    val clave = _claveActual.value ?: return
    val seccion = cuestionariosMap[clave]?.getOrNull(seccionIndex)

    val database = FirebaseDatabase.getInstance("https://psicointegral-usuariorespuesta-default-rtdb.firebaseio.com/")
    val refBase = database.reference
      .child(nombreEmpresa)
      .child(nombreEmpleado)
      .child("respuestas")
      .child(clave)
      .child("seccion_${seccionIndex + 1}")

    seccion?.seccion?.keys?.forEach { idPregunta ->
      respuestasActuales.remove(idPregunta)
      refBase.child(idPregunta).removeValue()
    }

    _respuestas.value = respuestasActuales.toMutableMap()
  }

  private fun guardarSinContestar() {
    val database = FirebaseDatabase.getInstance("https://psicointegral-usuariorespuesta-default-rtdb.firebaseio.com/")
    val ref = database.reference
      .child(nombreEmpresa)
      .child(nombreEmpleado)
      .child("respuestas")
      .child("123")

    ref.setValue("0")
  }

  private fun convertirRespuestaANumero(respuesta: String): Int {
    return when (respuesta.lowercase()) {
      "sí", "si" -> 1
      "no" -> 2
      "nunca" -> 3
      "rara vez" -> 4
      "algunas veces" -> 5
      "frecuentemente" -> 6
      "siempre" -> 7
      else -> 0
    }
  }

  fun reiniciarCuestionario() {
    _indiceSeccion.value = 0
    _respuestas.value = mutableMapOf()
    _mostrarSoloPrimeraPregunta.value = _claveActual.value == "cuestionario_01"
    _finalizado.value = false
    reiniciarIndicePregunta()
  }
}



