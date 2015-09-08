package togacharls.mientrenadorpersonapp.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.ArrayList;

import togacharls.mientrenadorpersonapp.Listeners.ListenerResultadosSeleccionar;
import togacharls.mientrenadorpersonapp.R;

public class DialogResultadosSeleccionar extends Dialogo implements OnClickListener{
	
	private TextView ejercicioTextView;
	private Button aceptarButton;
	private Button cancelarButton;
	private Button siguienteButton;
	private Button anteriorButton;
	private Button buscarEjerciciosButton;
	private EditText fechaEditText;
	private ArrayList<String> ejs;
	private Context c;
	private ListenerResultadosSeleccionar lrs;
	
	public DialogResultadosSeleccionar(Context context, ListenerResultadosSeleccionar l){		
		super(context, R.layout.dialog_resultados_seleccionar);
		c = context;
		lrs = l;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTitle("ResultadosActivity");
		fechaEditText = (EditText)findViewById(R.id.drsfecha);
		ejercicioTextView = (TextView) findViewById(R.id.drsej);
		aceptarButton = (Button)findViewById(R.id.drsaceptar);
		cancelarButton = (Button)findViewById(R.id.drscancelar);
		siguienteButton = (Button)findViewById(R.id.drssigej);
		anteriorButton = (Button)findViewById(R.id.drsantej);
		buscarEjerciciosButton = (Button)findViewById(R.id.drsejs);

		aceptarButton.setOnClickListener(this);
		cancelarButton.setOnClickListener(this);
		buscarEjerciciosButton.setOnClickListener(this);
		siguienteButton.setOnClickListener(this);
		anteriorButton.setOnClickListener(this);
		ejs = new ArrayList<String>();
	}
	
	@Override
	public void onClick(View v) { 

		switch(v.getId()){

		case R.id.drsaceptar:
			if(fechaEditText.getText().toString().length() != 10
			|| fechaEditText.getText().toString().charAt(2)!= '.' || fechaEditText.getText().toString().charAt(5)!= '.'){
				Toast.makeText(c, "Introduzca la fecha en el formato válido: DD.MM.AAAA", Toast.LENGTH_SHORT).show();
			}
			
			else{
				lrs.ejercicio(ejercicioTextView.getText().toString());
				dismiss();
			}
			break;	

		case R.id.drscancelar:
			dismiss();
			break;

		case R.id.drsantej:
			for(int i=0; i < ejs.size();i++){
				if(ejercicioTextView.getText().toString() == ejs.get(i)){
					if(i > 0){
						ejercicioTextView.setText(ejs.get(i - 1));
						break;
					}
					else{
						ejercicioTextView.setText(ejs.get(ejs.size() - 1));
						break;
					}
				}
			}
			break;
			
		case R.id.drssigej:
			for(int i=0; i < ejs.size();i++){
				if(ejercicioTextView.getText().toString() == ejs.get(i)){
					if(i < ejs.size() - 1){
						ejercicioTextView.setText(ejs.get(i + 1));
						break;
					}
					else{
						ejercicioTextView.setText(ejs.get(0));
						break;
					}
				}
			}
			break;
			
		case R.id.drsejs:
			if(fechaEditText.getText().toString().length() != 10
			|| fechaEditText.getText().toString().charAt(2)!= '.' || fechaEditText.getText().toString().charAt(5)!= '.'){
				Toast.makeText(c, "Introduzca la fecha en el formato válido: DD.MM.AAAA", Toast.LENGTH_SHORT).show();
			}
			else{
				actualizar();
			}
			break;
		}
	}
	
	private void actualizar(){
		
		ejs = lrs.obtenerEjerciciosDia(fechaEditText.getText().toString());
		
		if(ejs.size() == 0){
			siguienteButton.setVisibility(View.GONE);
			anteriorButton.setVisibility(View.GONE);
			ejercicioTextView.setText("Ningún ejercicio");
		}
		
		else{
			siguienteButton.setVisibility(View.VISIBLE);
			anteriorButton.setVisibility(View.VISIBLE);
			ejercicioTextView.setText(ejs.get(0));
		}
	}
}