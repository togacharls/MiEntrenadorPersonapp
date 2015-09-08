package togacharls.mientrenadorpersonapp.Dialogs;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.os.AsyncTask;

import togacharls.mientrenadorpersonapp.Listeners.ListenerDescanso;
import togacharls.mientrenadorpersonapp.R;

public class DialogDescanso extends Dialogo implements OnClickListener{

	private ListenerDescanso listener;
	private Button cerrarButton;
	private TextView tiempoTextView;
	private int tipo;
	private int segundos;
	
	public DialogDescanso(Context context, ListenerDescanso l, int t){		
		super(context, R.layout.dialog_descanso);
		listener = l;
		tipo = t;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTitle("Descanso");
		cerrarButton = (Button)findViewById(R.id.ddok);
		tiempoTextView = (TextView)findViewById(R.id.ddtiempo);
		segundos = listener.descanso(tipo);

		cuentaAtras();
		cerrarButton.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.ddok:
				listener.finDescanso();
				dismiss();
		}		
	}
	
	private void cuentaAtras(){
		new Crono().execute();
	}

    private class Crono extends AsyncTask<Void, Integer, String> {
        @Override
        protected String doInBackground(Void... params) {
        	if(segundos >= 0){
        		do{
        			try {
        					Thread.sleep(1000);
        					publishProgress(segundos);
        					if(segundos == 1) Thread.sleep(1000);		
        			} catch (InterruptedException e) {
        				Thread.interrupted();
        			}    		
        			segundos --;
        		}while(segundos > 0);
        		if(segundos > 0){
        			return String.valueOf(segundos);
        		}
        		else{
        			return "0";
        		}
        	}
        	return "0";
        }

        @Override
        protected void onPostExecute(String result) {
        	tiempoTextView.setText(result);
        }

        @Override
        protected void onPreExecute() {
        	if(segundos <= 0){
        		tiempoTextView.setText("0");
        	}
        	else{	
        		tiempoTextView.setText("" + segundos + "");
        	}
        }

        @Override
        protected void onProgressUpdate(Integer... val) {
        	tiempoTextView.setText("" + val[0] + "");
        }
    }	
}
