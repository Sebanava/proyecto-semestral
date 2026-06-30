package com.arriendo.login.LoginController;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arriendo.login.login;
import com.arriendo.login.DTO.LoginDTO;
import com.arriendo.login.DTO.RespuestaLoginDTO;
import com.arriendo.login.model.LoginModel;
import com.arriendo.login.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class LoginController {

    @Autowired
    private LoginService service;

    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Retorna la lista de todos los usuarios registrados con sus roles."
    )
    @GetMapping
    public ResponseEntity<List<login>> obtenertodo() {
        return ResponseEntity.ok(service.obtenertodo());
    }

    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica un usuario con email y password. " +
                      "Retorna un objeto JSON con nombre, email y rol del usuario. " +
                      "Retorna 401 si el usuario no existe o la contraseña es incorrecta."
    )
    @PostMapping("/login")
    public ResponseEntity<RespuestaLoginDTO> login(@RequestBody LoginDTO dto) {
        return service.login(dto);
    }

    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Crea un nuevo usuario en el sistema. " +
                      "El rol por defecto es CLIENTE. También puede ser ADMIN. " +
                      "Retorna 201 si el usuario fue creado exitosamente."
    )
    @PostMapping
    public ResponseEntity<login> guardar(@Valid @RequestBody login login) {
        return ResponseEntity.status(201).body(service.guardar(login));
    }

    @Operation(
        summary = "Actualizar usuario",
        description = "Actualiza los datos de un usuario existente. Solo accesible con rol ADMIN. " +
                      "Permite cambiar nombre, apellido, email, número, password y rol. " +
                      "Retorna 404 si el usuario no existe."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody LoginModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar usuarios");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @Operation(
        summary = "Eliminar usuario",
        description = "Elimina un usuario del sistema. Solo accesible con rol ADMIN. " +
                      "Retorna 403 si el rol no es ADMIN."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar usuarios");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Usuario eliminado con exito");
    }
}
