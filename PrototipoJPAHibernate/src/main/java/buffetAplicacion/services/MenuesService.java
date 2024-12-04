package buffetAplicacion.services;

import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import buffetAplicacion.Modelo.Comida;
import buffetAplicacion.Modelo.Menu;
import buffetAplicacion.repository.MenuRepository;
import buffetAplicacion.repository.ComidaRepository;

@Service
@Transactional
public class MenuesService {

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private ComidaService ComidaService;

	public Menu crearMenu(Menu menu) {
	    if (menu.getComidas() == null || menu.getComidas().isEmpty()) {
	        throw new IllegalArgumentException("El menú debe tener al menos una comida asociada.");
	    }

	    for (Comida comida : menu.getComidas()) {
	        this.ComidaService.guardarComida(comida);
	    }
	    return menuRepository.save(menu);
	}

	public Menu actualizarMenu(Menu menu) {
			Optional<Menu> busqueda = this.menuRepository.findById(menu.getIdmenu());
			if(busqueda.isEmpty()) throw new EntityExistsException("El id de menu otorga no existe en el sistema");

			for (Comida comida : menu.getComidas()) {
				this.ComidaService.guardarComida(comida);
			}
			busqueda.get().setTipo(menu.getTipo());

			busqueda.get().setDias(menu.getDias());
			busqueda.get().setPrecio(menu.getPrecio());
	    	busqueda.get().setComidas(menu.getComidas());
			return menuRepository.save(busqueda.get());

	}

	public Menu obtenerMenuPorId(Long id) {
		Optional<Menu> busqueda = this.menuRepository.findById(id);
		if(busqueda.isEmpty()) throw new EntityExistsException("El menu "+id+" no existe");
		return busqueda.get();
	}

	public List<Menu> obtenerTodosLosMenus() {
		return menuRepository.findAll();
	}
}


