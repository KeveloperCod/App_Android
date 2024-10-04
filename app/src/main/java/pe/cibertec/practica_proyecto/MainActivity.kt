package pe.cibertec.practica_proyecto

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pe.cibertec.practica_proyecto.api.ApiClient
import pe.cibertec.practica_proyecto.api.ApiService
import pe.cibertec.practica_proyecto.api.LoginRequest
import pe.cibertec.practica_proyecto.api.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    // Cambia a 'lateinit' para asegurarte de que se inicialicen antes de su uso
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa las vistas aquí
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            } else {
                Toast.makeText(
                    this,
                    "Por favor, ingrese los datos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun login(email: String, password: String) {
        val apiService: ApiService = ApiClient.getRetrofit().create(ApiService::class.java)
        val loginRequest = LoginRequest(email, password)

        val call = apiService.login(loginRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.isSuccess) {
                        Toast.makeText(this@MainActivity, "Login exitoso", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this@MainActivity, "Error: " + response.body()!!.message, Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error en la solicitud", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
