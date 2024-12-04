package buffetAplicacion.DTO;

import buffetAplicacion.Modelo.Comida;
import buffetAplicacion.Modelo.Menu;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;


import java.util.List;
@Getter
@Setter
public class MenuDTO {
    private Long idmenu;

    private Double precio;

    private String tipo;

    private List<Comida> comidas;

    public MenuDTO() {
    }

    public MenuDTO(Long idMenu, Double precio, String tipo, List<Comida> comidas) {
        this.idmenu = idMenu;
        this.precio = precio;
        this.tipo = tipo;
        this.comidas = comidas;
    }


}