package buffetAplicacion.Mappers;

import buffetAplicacion.DTO.MenuDTO;
import buffetAplicacion.Modelo.Menu;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper{

    MenuDTO menuToMenuDTO(Menu menu);
    Menu menuDTOToMenu(MenuDTO menuDTO);
}