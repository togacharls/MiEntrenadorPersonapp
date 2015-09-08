package togacharls.mientrenadorpersonapp.Database;
import android.graphics.drawable.*;

public class Ejercicio {
	private String nombre;
    private String musculoPrincipal;
    private String foto;

    public Ejercicio( String n, String mp, String f) {
        nombre = n;
        musculoPrincipal = mp;
        foto = f;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getMusculoPrincipal() {
        return musculoPrincipal;
    }

    public void setMusculoPrincipal(String musculoPrincipal) {
        this.musculoPrincipal = musculoPrincipal;
    }
}
