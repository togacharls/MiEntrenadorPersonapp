package togacharls.mientrenadorpersonapp.Listeners;
import java.util.ArrayList;

import togacharls.mientrenadorpersonapp.Database.EjercicioRutina;

public interface ListenerEjercicioRutina {
	public void actualizarLista(ArrayList<EjercicioRutina> lista);
	public boolean comprobarExistencia(String n);
}
