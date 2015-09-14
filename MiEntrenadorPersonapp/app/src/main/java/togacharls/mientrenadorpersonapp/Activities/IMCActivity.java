package togacharls.mientrenadorpersonapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Toast;
import android.view.View;

import togacharls.mientrenadorpersonapp.Application.MiEntrenadorPersonapp;
import togacharls.mientrenadorpersonapp.R;

public class IMCActivity extends AppCompatActivity {

	private EditText pesoEditText;
	private EditText alturaEditText;
	private TextView Resultado;
	private Button imcButton;
	private CheckBox checkIMC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calcularimc);

		alturaEditText = (EditText)this.findViewById(R.id.editAlturaIMC);
		pesoEditText = (EditText)this.findViewById(R.id.editPesoIMC);
		imcButton = (Button)this.findViewById(R.id.obtenerIMC);
		Resultado = (TextView)this.findViewById(R.id.resultadoIMC);
		checkIMC = (CheckBox)this.findViewById(R.id.perfilIMC);

		imcButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (checkIMC.isChecked()) obtenerDatos();
				calcularIMC();
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		this.getMenuInflater().inflate(R.menu.main, menu);
		/*Inflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.main, menu);*/
		return true;
	}

	/*
	* Se lleva a cabo el cálcul para obtener el IMC
	* */
	private void calcularIMC(){
    	if(!alturaEditText.getText().toString().isEmpty() && !pesoEditText.getText().toString().isEmpty()){
			double altura = Double.parseDouble(alturaEditText.getText().toString()) / 100;
			double peso = Double.parseDouble(pesoEditText.getText().toString());
    		double result = peso/(altura * altura);
    		result = Math.rint(result*100)/100;
    		Resultado.setText(Double.toString(result));
    	}
    	else{
    		camposVacios();
    	}		
	}

	/*
	* Obtiene los datos del perfil de la base de datos
	* */
	private void obtenerDatos(){
		Pair<Integer, Integer> pesoAltura= MiEntrenadorPersonapp.pesoAltura();
		if(pesoAltura.first < 0 || pesoAltura.second < 0) {
			Toast.makeText(this, "No se han encontrado datos, pruebe a modificar su perfil", Toast.LENGTH_SHORT).show();
		}
		else{
			pesoEditText.setText(pesoAltura.first.toString());
			alturaEditText.setText(pesoAltura.second.toString());
		}
	}

	/*
	* Muestra un mensaje para que el usuario introduzca su peso y su altura
	* */
	private void camposVacios(){
		Toast.makeText(this, "Introce la altura en centímetros y el peso en kilogramos.", Toast.LENGTH_SHORT).show();
	}
}
