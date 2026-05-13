    package com.arriendo.login.service;

    import java.util.List;
    import java.util.Optional;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;

    import com.arriendo.login.login;
    import com.arriendo.login.DTO.LoginDTO;
    import com.arriendo.login.model.LoginModel;
    import com.arriendo.login.repository.LoginRepository;

    @Service
    public class LoginService {

        @Autowired
        private LoginRepository repository;

        public List<login> obtenertodo(){
            return repository.findAll();
        }

        public ResponseEntity<String> login(LoginDTO dto) {
            Optional<login> usuario = repository.findByEmail(dto.getEmail());
            
            if (usuario.isEmpty()) {
                return ResponseEntity.status(401).body("Usuario no encontrado");
            }
            
            if (!usuario.get().getPassword().equals(dto.getPassword())) {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }
            
            return ResponseEntity.ok("Bienvenido " + usuario.get().getNombre());
        }

        public login guardar(login login){
            return repository.save(login);
        }

        public void eliminar(long id){
            repository.deleteById(id);
        }

        public login actualizar(Long id, LoginModel model){
            login login = repository.findById(id)
            .orElseThrow(()-> new RuntimeException("Usuario no encontrado "));
            login.setApellido(model.getApellido());
            login.setEmail(model.getEmail());
            login.setNombre(model.getNombre());
            login.setNumero(model.getNumero());
            login.setPassword(model.getPassword());
            login.setRut(model.getRut());
            return repository.save(login);

        }
    }   