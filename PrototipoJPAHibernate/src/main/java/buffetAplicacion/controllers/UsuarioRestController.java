package buffetAplicacion.controllers;

import buffetAplicacion.DTO.UsuarioDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import buffetAplicacion.services.UserService;
import buffetAplicacion.Modelo.Usuario;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "/usuario", produces =
MediaType.APPLICATION_JSON_VALUE)
public class UsuarioRestController {

	@Autowired
	UserService userService;

	@PostMapping
	public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO user){
		userService.saveUser(user);
		return new ResponseEntity<UsuarioDTO>(HttpStatus.CREATED);
	}

	@PutMapping("/{dni}")
	public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable("dni") int dni, @RequestBody UsuarioDTO user,
			 @RequestHeader(HttpHeaders.AUTHORIZATION) String token
			){
		 // Extraer ID de usuario desde el token
        Long idUsuario = obtenerToken(token);
        if (idUsuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized
        }
		userService.actualizar(user,dni);
		System.out.println("llego a actualizar");
		return new ResponseEntity<UsuarioDTO>(user,HttpStatus.OK);
    }

	private Long obtenerToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            // Quitar "Bearer " del inicio del token
            token = token.substring(7);
        }
        // El token es "idUsuario+123456", extraemos la parte antes de '+'
        String[] parts = token.split("\\+");
        if (parts.length == 2 && parts[1].equals("123456")) {
            try {
                return Long.parseLong(parts[0]);
            } catch (NumberFormatException e) {
                return null; // Si el idUsuario no es válido
            }
        }
        return null; // Token no válido
    }

	@PostMapping("/Autenticacion")
	public ResponseEntity<UsuarioDTO> inicioSesion( @RequestHeader("dni") int dni,
            @RequestHeader("clave") String clave){
		Usuario recuperado = this.userService.recurperarUserClave(clave, dni);
		String token="nuevoToken";
		return ResponseEntity.status(HttpStatus.NO_CONTENT).header("token", token).build();
	}

	@GetMapping("/{dni}")
	public ResponseEntity<UsuarioDTO> recuperarUsuario(@PathVariable("dni") int dni,
													   @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		Long tokenUsuario = obtenerToken(token);
		if (tokenUsuario == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized
		}
		UsuarioDTO recuperado = this.userService.recuperar(dni);
		return ResponseEntity.ok(recuperado);
	}
}