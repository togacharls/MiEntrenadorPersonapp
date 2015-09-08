package togacharls.mientrenadorpersonapp.Database;

/**
 * Created by Carlos on 08/09/2015.
 */
public class Serie {
    private int nDiaria;
    private float carga;
    private int repeticiones;

    public Serie(int nDiaria, float carga, int repeticiones){
        this.nDiaria = nDiaria;
        this.carga = carga;
        this.repeticiones = repeticiones;
    }

    public int getnDiaria() {
        return nDiaria;
    }

    public void setnDiaria(int nDiaria) {
        this.nDiaria = nDiaria;
    }

    public float getCarga() {
        return carga;
    }

    public void setCarga(float carga) {
        this.carga = carga;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }
}
