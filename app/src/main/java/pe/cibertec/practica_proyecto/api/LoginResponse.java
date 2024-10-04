package pe.cibertec.practica_proyecto.api;

public class LoginResponse {
    private boolean success;
    private String message;

    // Constructor
    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
