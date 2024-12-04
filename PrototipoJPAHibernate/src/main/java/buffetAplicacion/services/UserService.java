package buffetAplicacion.services;

import java.util.Optional;

import buffetAplicacion.DTO.UsuarioDTO;
import buffetAplicacion.Mappers.UsuarioMapper;
import buffetAplicacion.exceptions.InvalidCredentialsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import buffetAplicacion.Modelo.Usuario;
import buffetAplicacion.repository.UsuarioRepository;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserService {
	
	@Autowired
	private  UsuarioRepository usuarioRepository;
	@Autowired
	private UsuarioMapper usuarioMapper;

	public void saveUser(UsuarioDTO user) {
		Usuario user2 =  usuarioMapper.UsuarioDTOAUsuario(user);
		Optional<Usuario> busqueda = usuarioRepository.findUsuarioByDni(user2.getDni());
		if(busqueda.isPresent()) throw new DataIntegrityViolationException("existe Usuario");
		usuarioRepository.save(user2);
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


	public UsuarioDTO recuperar(int dni){
		Optional<Usuario> resultadoBusqueda = this.usuarioRepository.findUsuarioByDni(dni);
		resultadoBusqueda.orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
		return usuarioMapper.UsuarioAUsuarioDTO(resultadoBusqueda.get());
	}

	public void actualizar(UsuarioDTO user,int dni) {
		Usuario user2 =  usuarioMapper.UsuarioDTOAUsuario(user);
		Optional<Usuario> resultadoBusqueda = this.usuarioRepository.findUsuarioByDni(user2.getDni());
		if(resultadoBusqueda.isEmpty()) throw new EntityNotFoundException("No existe Usuario");
		resultadoBusqueda.get().setClave(user2.getClave());
		resultadoBusqueda.get().setEmail(user2.getEmail());
		resultadoBusqueda.get().setApellido(user2.getApellido());
		resultadoBusqueda.get().setNombres(user2.getNombres());
		resultadoBusqueda.get().setFoto(user2.getFoto());
		this.usuarioRepository.save(resultadoBusqueda.get());
	}
}
