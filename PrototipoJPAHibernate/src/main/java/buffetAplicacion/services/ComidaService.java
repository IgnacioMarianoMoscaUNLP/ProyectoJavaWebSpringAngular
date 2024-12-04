package buffetAplicacion.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import buffetAplicacion.repository.ComidaRepository;
import buffetAplicacion.Modelo.Comida;
import buffetAplicacion.Modelo.Menu;
import buffetAplicacion.Modelo.Usuario;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComidaService {

	@Autowired
	private ComidaRepository comidaRepository;
	
	public Comida crearComida(Comida comida) {
		Optional<Comida> busqueda = comidaRepository.findByNombre(comida.getNombre());
		if(busqueda.isPresent()) throw new EntityExistsException("La comida ya existe");
		return  comidaRepository.save(comida);
	}

	public void guardarComida(Comida comida) {
		Optional<Comida> busqueda = comidaRepository.findByNombre(comida.getNombre());
		if(busqueda.isEmpty()) this.comidaRepository.save(comida);
	}

	public List<Comida> listarComidas() {
		return (List <Comida>) this.comidaRepository.findAll();
	}
	
	public Comida actualizarComida(Comida comida) {
		Optional<Comida> busqueda = comidaRepository.findByNombre(comida.getNombre());
		if (busqueda.isPresent()) {
			busqueda.get().setPrecio(comida.getPrecio());
			return comidaRepository.save(busqueda.get());
		}
		throw new EntityNotFoundException("La comida no existe");
	}
}
