package es.back.tfg.asp.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> errorGeneral(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "ocurrió un error interno!"));
    }

    @ExceptionHandler(ExcepcionCorreoElectronico.class)
    public ResponseEntity<ApiResponse> errorCorreo(ExcepcionCorreoElectronico e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, e.getMensaje()));
    }

    @ExceptionHandler(ExcepcionCambiarContrasenna.class)
    public ResponseEntity<ApiResponse> cambiarContrasenna(ExcepcionCambiarContrasenna e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, e.getMensaje()));
    }

    @ExceptionHandler(ExcepcionInicioSesion.class)
    public ResponseEntity<ApiResponse> errorInicioSesion(ExcepcionInicioSesion e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, e.getMensaje()));
    }

    @ExceptionHandler(ExcepcionBuscarEntidad.class)
    public ResponseEntity<ApiResponse> errorBuscarEntidad(ExcepcionBuscarEntidad e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, e.getMensaje()));
    }
}
