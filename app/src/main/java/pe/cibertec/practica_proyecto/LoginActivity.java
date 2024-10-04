package pe.cibertec.practica_proyecto;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pe.cibertec.practica_proyecto.api.ApiClient;
import pe.cibertec.practica_proyecto.api.ApiService;
import pe.cibertec.practica_proyecto.api.LoginRequest;
import pe.cibertec.practica_proyecto.api.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Asegúrate de que 'activity_login.xml' sea el layout correcto

        // Inicializar vistas
        edtEmail = findViewById(R.id.edtEmail);  // Asegúrate de que estos IDs coincidan con tu archivo XML
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Configurar botón de inicio de sesión
        btnLogin.setOnClickListener(view -> {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();

            // Validar que los campos no estén vacíos
            if (!email.isEmpty() && !password.isEmpty()) {
                doLogin(email, password); // Ejecuta la función de login
            } else {
                Toast.makeText(LoginActivity.this, "Por favor, ingresa tus credenciales", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doLogin(String email, String password) {
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        LoginRequest loginRequest = new LoginRequest(email, password);

        Call<LoginResponse> call = apiService.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.isSuccess()) {
                        Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        // Aquí puedes redirigir a otra actividad o manejar el token
                    } else {
                        Toast.makeText(LoginActivity.this, loginResponse != null ? loginResponse.getMessage() : "Error en la respuesta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("API", "Error en la solicitud: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
