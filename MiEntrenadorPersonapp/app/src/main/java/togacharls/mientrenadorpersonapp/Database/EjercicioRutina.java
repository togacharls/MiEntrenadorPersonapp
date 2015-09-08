package togacharls.mientrenadorpersonapp.Database;

import android.graphics.drawable.Drawable;

public class EjercicioRutina {
	private String nombre;
	private String dia;
	private int repeticiones;
	private String foto;
	private int series;
	private int descansoSeries;
	private int descansoEjercicio;
	
    public EjercicioRutina(String n, String d, String f, int s, int r, int ds, int de) {
    	nombre = n;
    	dia = d;
    	foto = f;
    	series = s;
    	repeticiones = r;
    	descansoSeries = ds;
    	descansoEjercicio = de;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public int getRepeticiones() {
		return repeticiones;
	}

	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		this.series = series;
	}

	public int getDescansoSeries() {
		return descansoSeries;
	}

	public void setDescansoSeries(int descansoSeries) {
		this.descansoSeries = descansoSeries;
	}

	public int getDescansoEjercicio() {
		return descansoEjercicio;
	}

	public void setDescansoEjercicio(int descansoEjercicio) {
		this.descansoEjercicio = descansoEjercicio;
	}
}
