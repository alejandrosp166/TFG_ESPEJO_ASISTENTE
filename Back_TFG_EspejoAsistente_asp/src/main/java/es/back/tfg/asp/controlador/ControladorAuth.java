package es.back.tfg.asp.controlador;

import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;
import es.back.tfg.asp.servicio.iservice.IServiceAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v0/api/auth")
public class ControladorAuth {
    @Autowired
    private IServiceAuth serviceAuth;
    @PostMapping("/registro")
    public ResponseEntity<DTOUsuarioOut> registrarUsuario(@RequestBody DTOUsuarioIn dtoUsuarioIn) {
        DTOUsuarioOut dtoUsuarioOut = serviceAuth.registrarUsuario(dtoUsuarioIn);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoUsuarioOut);
    }
}