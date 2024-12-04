package buffetAplicacion.Mappers;

import buffetAplicacion.Modelo.Usuario;
import org.mapstruct.Mapper;
import buffetAplicacion.DTO.UsuarioDTO;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper  {

     UsuarioDTO UsuarioAUsuarioDTO(Usuario user);
     Usuario UsuarioDTOAUsuario(UsuarioDTO user);
}