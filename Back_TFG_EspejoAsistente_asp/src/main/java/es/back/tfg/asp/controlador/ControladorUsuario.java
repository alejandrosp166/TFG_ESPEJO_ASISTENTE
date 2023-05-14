package es.back.tfg.asp.controlador;

import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;
import es.back.tfg.asp.servicio.iservice.IServiceUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v0/api/usuario")
public class ControladorUsuario {
    @Autowired
    private IServiceUsuario IServiceUsuario;

    @GetMapping
    public ResponseEntity<List<DTOUsuarioOut>> obtenerTodosLosUsuarios() {
        List<DTOUsuarioOut> listUsuarios = IServiceUsuario.obtenerUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(listUsuarios);
    }

    @PostMapping
    public ResponseEntity<DTOUsuarioOut> guardarUsuario(@RequestBody DTOUsuarioIn dtoUsuarioIn) {
        DTOUsuarioOut dtoUsuarioOut = IServiceUsuario.guardarUsuario(dtoUsuarioIn);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoUsuarioOut);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<DTOUsuarioOut> obtenerUsuarioPorId(@PathVariable String uuid) {
        DTOUsuarioOut dtoUsuarioOut = IServiceUsuario.obtenerUsuarioPorId(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(dtoUsuarioOut);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<DTOUsuarioOut> actualizarUsuario(@RequestBody DTOUsuarioIn dtoUsuarioIn, @PathVariable String uuid) {
        DTOUsuarioOut dtoUsuarioOut = IServiceUsuario.actualizarUsuario(dtoUsuarioIn, uuid);
        return ResponseEntity.status(HttpStatus.OK).body(dtoUsuarioOut);
    }

    @DeleteMapping()
    public ResponseEntity<String> eliminarUsuario(String uuid) {
        IServiceUsuario.eliminarUsuario(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("El usuario se eliminó con éxito!");
    }
}
