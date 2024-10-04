package pe.cibertec.practica_proyecto.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/auth/login") // Asegúrate de que esta sea la ruta correcta de tu API
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
