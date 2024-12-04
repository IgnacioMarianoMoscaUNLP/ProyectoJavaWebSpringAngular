package buffetAplicacion.DTO;

public class UsuarioDTO{

    private int dni;
    private String apellido;
    private String nombres;
    private String email;
    private byte[] foto;  // Cambiado a byte[] para almacenar como BLOB

    public UsuarioDTO() {
    }
    public UsuarioDTO(int dni, String apellido, String nombres, String email, byte[] foto) {
        this.dni = dni;
        this.apellido = apellido;
        this.nombres = nombres;
        this.email = email;
        this.foto = foto;
    }
}