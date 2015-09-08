package togacharls.mientrenadorpersonapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;

import java.util.HashMap;

import togacharls.mientrenadorpersonapp.Application.MiEntrenadorPersonapp;
import togacharls.mientrenadorpersonapp.R;

public class ModificarPerfilActivity extends AppCompatActivity {
	
	private EditText nombre;
	private EditText edad;
	private EditText peso;
	private EditText altura;
	private RadioButton sexoM, sexoF;
	private EditText correo;
	private Button aceptar;
	private Button cancelar;
	private boolean datos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modificarperfil);

		nombre = (EditText)this.findViewById(R.id.editNombre);
		edad = (EditText)this.findViewById(R.id.editEdad);
		sexoM = (RadioButton)this.findViewById(R.id.sexoM);
		sexoF = (RadioButton)this.findViewById(R.id.sexoF);
		altura = (EditText)this.findViewById(R.id.editAltura);
		peso = (EditText)this.findViewById(R.id.editPeso);
		correo = (EditText)this.findViewById(R.id.editCorreo);
		aceptar = (Button)this.findViewById(R.id.aceptarPerfil);
		cancelar = (Button)this.findViewById(R.id.cancelarPerfil);

		datos = obtenerDatos();

		aceptar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				guardarDatos(datos);
				Intent intent = new Intent(ModificarPerfilActivity.this, PerfilActivity.class);
				startActivity(intent);
			}
		});

		cancelar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ModificarPerfilActivity.this, PerfilActivity.class);
				startActivity(intent);
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
	 * Guarda los datos del perfil		Si no hay ninguno creado -> Crea el perfil
	 * 									Si ya existe alguno creado -> Actualiza el perfil
	 * 
	 * */
	private void guardarDatos(boolean datos){
		if(!datos){
			if(edad.getText().toString().length() > 0 && edad.getText().toString().length() != 10){
				Toast.makeText(MiEntrenadorPersonapp.getContext(), "Si desea insertar la fecha de nacimiento,\n" +
						"hágalo en un formato válido:\n" +
						"DD.MM.AAAA", Toast.LENGTH_SHORT).show();
			}
			
			else if(edad.getText().toString().charAt(2) != '.' ||edad.getText().toString().charAt(5) != '.'){
				Toast.makeText(MiEntrenadorPersonapp.getContext(), "Si desea insertar la fecha de nacimiento,\n" +
						"hágalo en un formato válido:\n" +
						"DD.MM.AAAA", Toast.LENGTH_SHORT).show();				
			}
			
			else
			{
				insertarDatos();
				Toast.makeText(MiEntrenadorPersonapp.getContext(), "Datos almacenados correctamente", Toast.LENGTH_SHORT).show();
			}
		}
		
		else {
			if(edad.getText().toString().length() > 0 && edad.getText().toString().length() != 10){
				Toast.makeText(MiEntrenadorPersonapp.getContext(), "Si desea actualizar la fecha de nacimiento,\n" +
						"hágalo en un formato válido:\n" +
						"DD.MM.AAAA", Toast.LENGTH_SHORT).show();
			}
			
			else if(edad.getText().toString().charAt(2) != '.' ||edad.getText().toString().charAt(5) != '.'){
				Toast.makeText(MiEntrenadorPersonapp.getContext(), "Si desea actualizar la fecha de nacimiento,\n" +
						"hágalo en un formato válido:\n" +
						"DD.MM.AAAA", Toast.LENGTH_SHORT).show();				
			}
			else{
				actualizarDatos();
				Toast.makeText(MiEntrenadorPersonapp.getContext(), "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	/*
	 * Crea el perfil en la base de datos
	 * 
	 * */
	private void insertarDatos(){
		HashMap<String, String> datosPefil = new HashMap();
		datosPefil.put("Nombre", nombre.getText().toString());
		datosPefil.put("Altura", altura.getText().toString());
		datosPefil.put("Peso", peso.getText().toString());
		datosPefil.put("Fecha de Nacimiento", nombre.getText().toString());
		if(sexoM.isChecked()){
			datosPefil.put("Sexo", "M");
		}
		else if(sexoF.isChecked()){
			datosPefil.put("Sexo", "F");
		}
		datosPefil.put("Correo", correo.getText().toString());
		datosPefil.put("Sonido", "S");
		MiEntrenadorPersonapp.registrarPerfil(datosPefil);
	}
	
	
	/*
	 * Actualiza los datos del perfil en la base de datos
	 * 
	 * */
	private void actualizarDatos(){

		HashMap<String, String> datosPerfil = new HashMap();
		datosPerfil.put("Nombre", nombre.getText().toString());
		datosPerfil.put("Altura", altura.getText().toString());
		datosPerfil.put("Peso", peso.getText().toString());
		datosPerfil.put("Fecha de Nacimiento", nombre.getText().toString());
		if(sexoM.isChecked()){
			datosPerfil.put("Sexo", "M");
		}
		else if(sexoF.isChecked()){
			datosPerfil.put("Sexo", "F");
		}
		datosPerfil.put("Correo", correo.getText().toString());
		datosPerfil.put("Sonido", "S");

		MiEntrenadorPersonapp.actualizarPerfil(datosPerfil);
	}
	
	/*
	 * Devuelve 	True si existe y se ha podido cargar el perfil del usuario
	 * 				False en caso contrario
	 * */
	private boolean obtenerDatos(){
		HashMap<String, String> perfil = MiEntrenadorPersonapp.obtenerPerfil();
		if (!perfil.isEmpty()) {
			nombre.setText(perfil.get("Nombre"));
			edad.setText(perfil.get("Fecha de Nacimiento"));
			peso.setText(perfil.get("Peso"));
			altura.setText(perfil.get("Altura"));
			
			if(perfil.get("Sexo").equals("M")){
				sexoM.setChecked(true);
			}
			else if(perfil.get("Sexo").equals("F")){
				sexoF.setChecked(true);
			}
			correo.setText(perfil.get("Correo"));
			return true;
			
		} else{
			return false;
		}	
	}
}