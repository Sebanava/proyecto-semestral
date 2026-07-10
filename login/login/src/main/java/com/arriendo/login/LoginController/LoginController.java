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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Autenticación", description = "Gestión de usuarios y autenticación del sistema")
public class LoginController {

    @Autowired
    private LoginService service;

    @Operation(summary = "Obtener todos los usuarios", description = "Retorna la lista de todos los usuarios registrados con sus roles.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuarios retornada exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<login>> obtenertodo() {
        return ResponseEntity.ok(service.obtenertodo());
    }

    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario con email y password. Retorna nombre, email y rol del usuario.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login exitoso - retorna nombre, email y rol"),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas - usuario no existe o password inválido")
    })
    @PostMapping("/login")
    public ResponseEntity<RespuestaLoginDTO> login(@RequestBody LoginDTO dto) {
        return service.login(dto);
    }

    @Operation(summary = "Registrar nuevo usuario", description = "Crea un nuevo usuario. El rol por defecto es CLIENTE. También puede ser ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<login> guardar(@Valid @RequestBody LoginModel model) {
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza datos de un usuario existente. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody LoginModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar usuarios");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar usuarios");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Usuario eliminado con exito");
    }
}
