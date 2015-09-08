package togacharls.mientrenadorpersonapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.view.View;

import java.util.HashMap;

import togacharls.mientrenadorpersonapp.Application.MiEntrenadorPersonapp;
import togacharls.mientrenadorpersonapp.R;

public class PerfilActivity extends AppCompatActivity {
	
	private TextView nombreTextView;
	private TextView edadTextView;
	private TextView pesoTextView;
	private TextView alturaTextView;
	private TextView sexoTextView;
	private TextView correoTextView;
	private Button imcButton;
	private Button modificarButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfil);

		nombreTextView = (TextView)this.findViewById(R.id.nombre);
		edadTextView = (TextView)this.findViewById(R.id.edad);
		sexoTextView = (TextView)this.findViewById(R.id.sexo);
		alturaTextView = (TextView)this.findViewById(R.id.altura);
		pesoTextView = (TextView)this.findViewById(R.id.peso);
		correoTextView = (TextView)this.findViewById(R.id.correoElectronico);
		imcButton = (Button)this.findViewById(R.id.calcimc);
		modificarButton = (Button)this.findViewById(R.id.modificarPerfil);

		imcButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PerfilActivity.this, IMCActivity.class);
				startActivity(intent);
			}
		});

		modificarButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PerfilActivity.this, ModificarPerfilActivity.class);
				startActivity(intent);
			}
		});

		//Se cargan los datos del perfil
		HashMap<String, String> perfil = MiEntrenadorPersonapp.obtenerPerfil();
		if(!perfil.isEmpty()){
			nombreTextView.setText(perfil.get("Nombre"));
			edadTextView.setText(perfil.get("Fecha de Nacimiento"));
			pesoTextView.setText(perfil.get("Peso"));
			alturaTextView.setText(perfil.get("Altura"));
			sexoTextView.setText(perfil.get("Sexo"));
			correoTextView.setText(perfil.get("Correo"));
		}
		else{
			Toast.makeText(MiEntrenadorPersonapp.getContext(), "No se han encontrado datos, pruebe a modificar su perfil", Toast.LENGTH_SHORT).show();
		}
	}
}
