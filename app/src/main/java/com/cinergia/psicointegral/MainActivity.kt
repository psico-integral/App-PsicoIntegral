package com.cinergia.psicointegral

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.cinergia.psicointegral.Bienvenida.Bienvenida
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

  private lateinit var usuarioEditText: EditText
  private lateinit var contrasenaEditText: EditText
  private lateinit var iconoVisibilidad: ImageView
  private lateinit var tvErrorUsuario: TextView
  private lateinit var tvErrorContraseña: TextView
  private lateinit var tvErrorCredenciales: TextView
  private lateinit var database: FirebaseDatabase
  private lateinit var empresaRef: DatabaseReference
  private var esVisible = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    usuarioEditText = findViewById(R.id.usuario)
    contrasenaEditText = findViewById(R.id.contrasena)
    iconoVisibilidad = findViewById(R.id.iconoVisibilidad)
    tvErrorUsuario = findViewById(R.id.tvErrorUsuario)
    tvErrorContraseña = findViewById(R.id.tvErrorContraseña)
    tvErrorCredenciales = findViewById(R.id.tvErrorcredenciales)
    val btnEntrar = findViewById<Button>(R.id.btnEntrar)

    database = FirebaseDatabase.getInstance("https://psicointegral-encuestas-default-rtdb.firebaseio.com/")
    empresaRef = database.reference.child("empresa")

    // Alternar visibilidad de la contraseña
    iconoVisibilidad.setOnClickListener {
      esVisible = !esVisible
      if (esVisible) {
        contrasenaEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        iconoVisibilidad.setImageResource(R.drawable.ic_visibility_on)
      } else {
        contrasenaEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        iconoVisibilidad.setImageResource(R.drawable.ic_visibility_off)
      }
      contrasenaEditText.setSelection(contrasenaEditText.text.length) // Mantener cursor
    }

    btnEntrar.setOnClickListener {
      val usuario = usuarioEditText.text.toString().trim()
      val contrasena = contrasenaEditText.text.toString().trim()

      if (usuario.isNotEmpty() && contrasena.isNotEmpty()) {
        validarCredenciales(usuario, contrasena)
      } else {
        mostrarErrores(usuario, contrasena)
      }
    }
  }

  private fun mostrarErrores(usuario: String, contrasena: String) {
    tvErrorUsuario.visibility = if (usuario.isEmpty()) {
      tvErrorUsuario.text = "El usuario es requerido"
      TextView.VISIBLE
    } else TextView.GONE

    tvErrorContraseña.visibility = if (contrasena.isEmpty()) {
      tvErrorContraseña.text = "La contraseña es requerida"
      TextView.VISIBLE
    } else TextView.GONE
  }

  private fun validarCredenciales(usuario: String, contrasena: String) {
    tvErrorCredenciales.visibility = TextView.GONE

    empresaRef.get().addOnCompleteListener { task ->
      if (task.isSuccessful) {
        val dataSnapshot = task.result
        var credencialesValidas = false
        var nombreEmpresa = ""
        var empleadoKey: String? = null
        var empleadoSnapshot: DataSnapshot? = null

        dataSnapshot?.children?.forEach { empleado ->
          val storedUsuario = empleado.child("usuario").getValue(String::class.java)
          val storedContrasena = empleado.child("contrasena").getValue(String::class.java)

          if (storedUsuario == usuario && storedContrasena == contrasena) {
            empleadoKey = empleado.key
            empleadoSnapshot = empleado
            credencialesValidas = true
          }
        }

        if (credencialesValidas && empleadoSnapshot != null && empleadoKey != null) {
          val limLogeo = empleadoSnapshot!!.child("limLogeo").getValue(Int::class.java) ?: 0
          val noEmpresa = empleadoSnapshot!!.child("noEmpresa").getValue(String::class.java)?.toIntOrNull() ?: 0
          val nombre = empleadoSnapshot!!.child("nombre").getValue(String::class.java) ?: ""

          if (limLogeo >= noEmpresa) {
            tvErrorCredenciales.text = "Has alcanzado el límite de accesos 🚫"
            tvErrorCredenciales.visibility = TextView.VISIBLE
          } else {
            // Incrementar limLogeo en Firebase
            empresaRef.child(empleadoKey!!).child("limLogeo").setValue(limLogeo + 1)

            Toast.makeText(this, "Login exitoso ✅", Toast.LENGTH_SHORT).show()
            guardarNombreEmpresa(nombre)
            gotoBienvenida(nombre)
          }
        } else {
          tvErrorCredenciales.text = "Usuario o contraseña incorrectos ⚠️"
          tvErrorCredenciales.visibility = TextView.VISIBLE
        }
      } else {
        Toast.makeText(this, "Error al consultar la base de datos", Toast.LENGTH_SHORT).show()
      }
    }
  }


  private fun guardarNombreEmpresa(nombreEmpresa: String) {
    val sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("nombre_empresa", nombreEmpresa).apply()
  }

  private fun gotoBienvenida(nombreEmpresa: String) {
    val intent = Intent(this, Bienvenida::class.java)
    intent.putExtra("nombre_empresa", nombreEmpresa)
    startActivity(intent)
    finish()
  }
}

