package com.cinergia.psicointegral

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
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
  private lateinit var tvErrorContrasena: TextView
  private lateinit var tvErrorCredenciales: TextView
  private lateinit var database: FirebaseDatabase
  private lateinit var empresaRef: DatabaseReference
  private var esVisible = false

  private lateinit var layoutSinInternet: LinearLayout
  private lateinit var btnCerrarApp: Button
  private lateinit var btnEntrar: Button

  private lateinit var connectivityManager: ConnectivityManager
  private lateinit var networkCallback: ConnectivityManager.NetworkCallback

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    layoutSinInternet = findViewById(R.id.layoutSinInternet)
    btnCerrarApp = findViewById(R.id.btnCerrarApp)
    usuarioEditText = findViewById(R.id.usuario)
    contrasenaEditText = findViewById(R.id.contrasena)
    iconoVisibilidad = findViewById(R.id.iconoVisibilidad)
    tvErrorUsuario = findViewById(R.id.tvErrorUsuario)
    tvErrorContrasena = findViewById(R.id.tvErrorContrasena)
    tvErrorCredenciales = findViewById(R.id.tvErrorcredenciales)
    btnEntrar = findViewById(R.id.btnEntrar)

    database = FirebaseDatabase.getInstance("https://psicointegral-encuestas-default-rtdb.firebaseio.com/")
    empresaRef = database.reference.child("empresa")

    btnCerrarApp.setOnClickListener {
      finishAffinity()
    }

    iconoVisibilidad.setOnClickListener {
      esVisible = !esVisible
      if (esVisible) {
        contrasenaEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        iconoVisibilidad.setImageResource(R.drawable.ic_visibility_on)
      } else {
        contrasenaEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        iconoVisibilidad.setImageResource(R.drawable.ic_visibility_off)
      }
      contrasenaEditText.setSelection(contrasenaEditText.text.length)
    }

    connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    networkCallback = object : ConnectivityManager.NetworkCallback() {
      override fun onAvailable(network: Network) {
        runOnUiThread {
          habilitarControles(true)
          layoutSinInternet.visibility = LinearLayout.GONE
        }
      }

      override fun onLost(network: Network) {
        runOnUiThread {
          habilitarControles(false)
          layoutSinInternet.visibility = LinearLayout.VISIBLE
        }
      }
    }

    val networkRequest = NetworkRequest.Builder().build()
    connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

    val tieneInternet = hayInternet()
    habilitarControles(tieneInternet)
    layoutSinInternet.visibility = if (tieneInternet) LinearLayout.GONE else LinearLayout.VISIBLE

    btnEntrar.setOnClickListener {
      val usuario = usuarioEditText.text.toString().trim()
      val contrasena = contrasenaEditText.text.toString().trim()

      if (!hayInternet()) {
        layoutSinInternet.visibility = LinearLayout.VISIBLE
        habilitarControles(false)
        return@setOnClickListener
      } else {
        layoutSinInternet.visibility = LinearLayout.GONE
      }

      if (usuario.isNotEmpty() && contrasena.isNotEmpty()) {
        validarCredenciales(usuario, contrasena)
      } else {
        mostrarErrores(usuario, contrasena)
      }
    }
  }

  private fun habilitarControles(habilitar: Boolean) {
    usuarioEditText.isEnabled = habilitar
    contrasenaEditText.isEnabled = habilitar
    btnEntrar.isEnabled = habilitar
    iconoVisibilidad.isEnabled = habilitar
  }

  private fun hayInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
  }

  private fun mostrarErrores(usuario: String, contrasena: String) {
    tvErrorUsuario.visibility = if (usuario.isEmpty()) {
      tvErrorUsuario.text = "El usuario es requerido"
      TextView.VISIBLE
    } else TextView.GONE

    tvErrorContrasena.visibility = if (contrasena.isEmpty()) {
      tvErrorContrasena.text = "La contraseña es requerida"
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

  override fun onDestroy() {
    super.onDestroy()
    connectivityManager.unregisterNetworkCallback(networkCallback)
  }
}



