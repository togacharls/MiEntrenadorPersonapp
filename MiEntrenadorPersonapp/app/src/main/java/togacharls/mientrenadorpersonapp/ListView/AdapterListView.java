package togacharls.mientrenadorpersonapp.ListView;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import togacharls.mientrenadorpersonapp.Application.MiEntrenadorPersonapp;
import togacharls.mientrenadorpersonapp.Database.EjercicioRutina;
import togacharls.mientrenadorpersonapp.R;

public class AdapterListView extends BaseAdapter{

	//private Activity context;
	protected ArrayList<String> elementos;
	protected ArrayList<EjercicioRutina> ejerciciosrutina;
	private int layout;
	
	//public AdapterListView(Activity c, int l, ArrayList<String> e, ArrayList<EjercicioRutina>er){
	public AdapterListView(int l, ArrayList<String> e, ArrayList<EjercicioRutina>er){
		//context = c;
		elementos = e;
		layout = l;
		if(e == null){
			ejerciciosrutina = er;
			elementos = null;
		}
		else{
			elementos = e;
			ejerciciosrutina = null;
		}
	}
	
	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public int getCount() {
		if(elementos!=null )return elementos.size();
		else return ejerciciosrutina.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
       // LayoutInflater inflater = context.getLayoutInflater();
		LayoutInflater inflater = (LayoutInflater)MiEntrenadorPersonapp.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row;
        row = inflater.inflate(layout, parent, false);
        switch(layout){

			//Lista de músculos
        	case R.layout.elementolista:
        		TextView texto;
        		texto = (TextView) row.findViewById(R.id.nombreElemento);
        		texto.setText(elementos.get(position));
        		break;

        	//Lista de ejercicios (Rutina)
        	case R.layout.elementorutina:
        		TextView series, repeticiones, dseries, dfin;
        		ImageView imagen;
        		series = (TextView) row.findViewById(R.id.elementorutinaseries);
        		repeticiones = (TextView) row.findViewById(R.id.elementorutinarepeticiones);
        		dseries = (TextView) row.findViewById(R.id.elementorutinadescansoseries);
        		dfin = (TextView) row.findViewById(R.id.elementorutinadescansofin);
        		imagen = (ImageView)row.findViewById(R.id.elementorutinafoto);
        	
        		series.setText("Series: " + Integer.valueOf(ejerciciosrutina.get(position).getSeries()).toString());
        		repeticiones.setText("Repeticiones:\n" + ejerciciosrutina.get(position).getRepeticiones());
        		dseries.setText("Descanso entre series: "+ Integer.valueOf(ejerciciosrutina.get(position).getDescansoSeries()).toString()+"s");
				dfin.setText("Descanso final: " + Integer.valueOf(ejerciciosrutina.get(position).getDescansoEjercicio()).toString() + "s");
				int resource = MiEntrenadorPersonapp.getContext().getResources().getIdentifier(ejerciciosrutina.get(position).getFoto(), "drawable", MiEntrenadorPersonapp.getContext().getPackageName());
				imagen.setImageResource(resource);
        		//imagen.setImageDrawable(ejerciciosrutina.get(position).foto);
        		break;
        }
        return (row);
	}
}