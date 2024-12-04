package buffetAplicacion.services;

import java.util.Objects;
import java.util.Optional;
import buffetAplicacion.exceptions.InvalidCredentialsException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.authenticator.BasicAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import buffetAplicacion.Modelo.Usuario;
import buffetAplicacion.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import javax.naming.AuthenticationException;


@Service
@Transactional
public class UserService {
	
	@Autowired
	private  UsuarioRepository usuarioRepository;
	
	public void saveUser(Usuario user) {
		Optional<Usuario> busqueda = usuarioRepository.findUsuarioByDni(user.getDni());
		if(busqueda.isPresent()) throw new DataIntegrityViolationException("existe Usuario");
		usuarioRepository.save(user);
	}
	
	public Usuario recurperarUserClave(String clave, int dni) {
		Optional<Usuario> resultadoBusqueda = this.usuarioRepository.findUsuarioByDni(dni);
		if(resultadoBusqueda.isEmpty() ) {
			throw new InvalidCredentialsException("Sus credenciales son incorrectas");
		}else{
			if(!resultadoBusqueda.get().getClave().equals(clave)){
				throw new InvalidCredentialsException("Sus Credenciales son incorrectas");
			}else{
				return resultadoBusqueda.get();
			}
		}
	}
	public Usuario recuperar(int dni){
		Optional<Usuario> resultadoBusqueda = this.usuarioRepository.findUsuarioByDni(dni);
		if(resultadoBusqueda.isEmpty() ) {
			throw new EntityNotFoundException("Usuario no encontrado");
		}
		return resultadoBusqueda.get();
	}

	public void actualizar(Usuario user) {
		Optional<Usuario> resultadoBusqueda = this.usuarioRepository.findUsuarioByDni(user.getDni());
		if(resultadoBusqueda.isEmpty()) throw new EntityNotFoundException("No existe Usuario");
		resultadoBusqueda.get().setClave(user.getClave());
		resultadoBusqueda.get().setEmail(user.getEmail());
		resultadoBusqueda.get().setApellido(user.getApellido());
		resultadoBusqueda.get().setNombres(user.getNombres());
		resultadoBusqueda.get().setFoto(user.getFoto());
		this.usuarioRepository.save(resultadoBusqueda.get());
	}
}
