package togacharls.mientrenadorpersonapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import togacharls.mientrenadorpersonapp.Application.MiEntrenadorPersonapp;
import togacharls.mientrenadorpersonapp.ListView.AdapterListView;
import togacharls.mientrenadorpersonapp.Database.EjercicioRutina;
import togacharls.mientrenadorpersonapp.R;

public class RutinaActivity extends AppCompatActivity {
	private Button definirRutinaButton;
	private Button nextDayButton;
	private Button previousDayButton;
	private TextView diaSemanaTextView;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rutina);

	}


	@Override
	public void onResume(){
		super.onResume();
		if(diaSemanaTextView != null){
			actualizarDia(diaSemanaTextView.getText().toString());
		}

		definirRutinaButton = (Button)findViewById(R.id.rutinaPersonalizada);
		listView = (ListView)findViewById(R.id.rutinaListView);
		nextDayButton = (Button)findViewById(R.id.rutinanextday);
		previousDayButton = (Button)findViewById(R.id.rutinaprevday);
		diaSemanaTextView = (TextView)findViewById(R.id.rutinadiaSemana);

		ArrayList<EjercicioRutina> ejs = MiEntrenadorPersonapp.listaEjerciciosDia(diaSemanaTextView.getText().toString());

		if(ejs.isEmpty()){
			Toast.makeText(MiEntrenadorPersonapp.getContext(), "No se han encontrado ejercicios para este día", Toast.LENGTH_SHORT).show();
		}

		AdapterListView adapter = new AdapterListView(R.layout.elementorutina, null, ejs);
		listView.setAdapter(adapter);

		definirRutinaButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(RutinaActivity.this, DefinirRutinaActivity.class);
				startActivity(intent);
			}
		});

		nextDayButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				diaSiguiente();
			}
		});

		previousDayButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				diaAnterior();
			}
		});
	}
	
	/*
	 * Carga los resultados del día siguiente al actual
	 * 
	 * */
	private void diaSiguiente(){
    	if(diaSemanaTextView.getText().toString().equals("Lunes")){
    		diaSemanaTextView.setText("Martes");
    		actualizarDia(diaSemanaTextView.getText().toString());
    	}
    	
    	else if(diaSemanaTextView.getText().toString().equals("Martes")){
    		diaSemanaTextView.setText("Miércoles");
    		actualizarDia(diaSemanaTextView.getText().toString());
    	}
    	
    	else if(diaSemanaTextView.getText().toString().equals("Miércoles")){
    		diaSemanaTextView.setText("Jueves");
    		actualizarDia(diaSemanaTextView.getText().toString());
    	}    	       	
    	
    	else if(diaSemanaTextView.getText().toString().equals("Jueves")){
    		diaSemanaTextView.setText("Viernes");
    		actualizarDia(diaSemanaTextView.getText().toString());
    	}        	
    	
    	else if(diaSemanaTextView.getText().toString().equals("Viernes")){
    		diaSemanaTextView.setText("Sábado");
    		actualizarDia(diaSemanaTextView.getText().toString());
    	}        	
    	
    	else if(diaSemanaTextView.getText().toString().equals("Sábado")){
    		diaSemanaTextView.setText("Domingo");
    		actualizarDia(diaSemanaTextView.getText().toString());
    	}        	
    	
    	else if(diaSemanaTextView.getText().toString().equals("Domingo")){
    		diaSemanaTextView.setText("Lunes");
    		actualizarDia(diaSemanaTextView.getText().toString());
    	}        	
	}
	/*
	 * Carga los resultados del día anterior al actual
	 * 
	 * */
	private void diaAnterior(){
	   	if(diaSemanaTextView.getText().toString().equals("Lunes")){
    		diaSemanaTextView.setText("Domingo");
    		actualizarDia(diaSemanaTextView.getText().toString());
    	}
    	
    	else if(diaSemanaTextView.getText().toString().equals("Martes")){
    		diaSemanaTextView.setText("Lunes");
    		actualizarDia(diaSemanaTextView.getText().toString());
    	}
    	
    	else if(diaSemanaTextView.getText().toString().equals("Miércoles")){
    		diaSemanaTextView.setText("Martes");
    		actualizarDia(diaSemanaTextView.getText().toString());
    	}    	
    	
    	else if(diaSemanaTextView.getText().toString().equals("Jueves")){
    		diaSemanaTextView.setText("Miércoles");
    		actualizarDia(diaSemanaTextView.getText().toString());
    	}        	       	
    	
    	else if(diaSemanaTextView.getText().toString().equals("Viernes")){
    		diaSemanaTextView.setText("Jueves");
    		actualizarDia(diaSemanaTextView.getText().toString());
    	}        	
    	
    	else if(diaSemanaTextView.getText().toString().equals("Sábado")){
    		diaSemanaTextView.setText("Viernes");
    		actualizarDia(diaSemanaTextView.getText().toString());
    	}        	
    	
    	else if(diaSemanaTextView.getText().toString().equals("Domingo")){
    		diaSemanaTextView.setText("Sábado");
    		actualizarDia(diaSemanaTextView.getText().toString());
    	} 		
	}
	
	/*
	 * Actualiza la pantalla con los datos registrados ese día en la rutina
	 * */
	private void actualizarDia(String day){
		ArrayList<EjercicioRutina> ejs = MiEntrenadorPersonapp.listaEjerciciosDia(day);
		
		if(ejs.isEmpty()){
			Toast.makeText(MiEntrenadorPersonapp.getContext(), "No se han encontrado ejercicios para este día", Toast.LENGTH_SHORT).show();
		}
		
		AdapterListView adapter = new AdapterListView(R.layout.elementorutina, null, ejs);
		listView.setAdapter(adapter);
	}
}