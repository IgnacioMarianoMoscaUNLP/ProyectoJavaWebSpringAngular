package buffetAplicacion.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import java.sql.SQLException;
import java.util.List;
import buffetAplicacion.services.ComidaService;
import buffetAplicacion.Modelo.Comida;
import buffetAplicacion.Modelo.Menu;
import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping(value = "/comida", produces =
MediaType.APPLICATION_JSON_VALUE)
public class ComidaRestController {

	@Autowired
	private ComidaService comidaService;

	@PostMapping
    public ResponseEntity<Comida> crearComida(@RequestBody Comida comida) {
        Comida nuevaComida = comidaService.crearComida(comida);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaComida);
    }
	
	@GetMapping
    public ResponseEntity<List<Comida>> listarComidas() {
        List<Comida> comidas = comidaService.listarComidas();
        return ResponseEntity.ok(comidas);
    }
	
	@PutMapping
    public ResponseEntity<Comida> actualizarComida(@RequestBody Comida comida) {
        Comida comidaActualizada = comidaService.actualizarComida(comida);
        return ResponseEntity.ok(comidaActualizada);
    }
}
