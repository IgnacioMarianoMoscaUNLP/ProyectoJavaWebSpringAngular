package buffetAplicacion.controllers;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import java.sql.SQLException;
import java.util.List;		
import buffetAplicacion.services.MenuesService;
import buffetAplicacion.Modelo.Menu;
import buffetAplicacion.Modelo.Usuario;
import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping(value = "/menu", produces =
MediaType.APPLICATION_JSON_VALUE)
public class MenuesRestController {
	@Autowired
	private MenuesService menuesService;
	 
	 @PostMapping
	    public ResponseEntity<Menu> crearMenu(@RequestBody Menu menu) {
	        Menu nuevoMenu = menuesService.crearMenu(menu);
	        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMenu);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Menu> actualizarMenu(@PathVariable("id") Long id, @RequestBody Menu menu) {
	     	menu.setIdmenu(id);
		 	Menu menuActualizado = menuesService.actualizarMenu(menu);
		 	return ResponseEntity.ok(menuActualizado);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Menu> obtenerMenuPorId(@PathVariable("id") Long id) {
	        Menu menu = menuesService.obtenerMenuPorId(id);
			return ResponseEntity.ok(menu);
	    }

	    @GetMapping
	    public ResponseEntity<List<Menu>> obtenerTodosLosMenus() {
	        List<Menu> menus = menuesService.obtenerTodosLosMenus();
	        return ResponseEntity.ok(menus);
	    }
	
}
