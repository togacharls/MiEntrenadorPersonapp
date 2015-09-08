package togacharls.mientrenadorpersonapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.content.Intent;
import android.widget.Toast;

import togacharls.mientrenadorpersonapp.Application.MiEntrenadorPersonapp;
import togacharls.mientrenadorpersonapp.Dialogs.DialogBorrarDatos;
import togacharls.mientrenadorpersonapp.Listeners.ListenerBorrarDatos;
import togacharls.mientrenadorpersonapp.R;

public class OpcionesActivity extends AppCompatActivity implements ListenerBorrarDatos, View.OnClickListener{
	
	private CheckBox checkboxSound;
	private Button buttonBorrar, buttonAceptar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opciones);
		checkboxSound = (CheckBox)this.findViewById(R.id.sonido);
		buttonBorrar = (Button)this.findViewById(R.id.borrarBaseDatos);
		buttonAceptar = (Button)this.findViewById(R.id.cambiarOpciones);
		buttonAceptar.setOnClickListener(this);

		buttonBorrar.setOnClickListener(this);

		comprobarOpciones();

	}

	/*
	 * Comprueba si el usuario tiene activado o no el sonido
	 *
	 * */
	private void comprobarOpciones(){
		if(MiEntrenadorPersonapp.sonidoActivo()){
			checkboxSound.setChecked(true);
		}
	}
	
	/*
	 * Borra el registro definido por el usuario de la base de datos
	 * */
	@Override
	public void borrar() {
		MiEntrenadorPersonapp.borrarRegistro();
		Toast.makeText(MiEntrenadorPersonapp.getContext(), "Datos borrados correctamente", Toast.LENGTH_SHORT).show();
	}
	
	/*
	 * Actualiza los parámetros de configuración (el sonido es el único)
	 * */
	private void aceptar(){
    	if(MiEntrenadorPersonapp.perfilCreado()){
			String sonido;
			if(checkboxSound.isChecked()){
				sonido = "S";
			}
			else{
				sonido = "N";
			}
			MiEntrenadorPersonapp.actualizarSonido(sonido);
    		Toast.makeText(MiEntrenadorPersonapp.getContext(), "Cambios almacenados correctamente", Toast.LENGTH_SHORT).show();
    		Intent intent = new Intent(OpcionesActivity.this, MainActivity.class);
    		startActivity(intent);    		
    	}
    	else{
    		Toast.makeText(MiEntrenadorPersonapp.getContext(), "Para modificar la configuración antes debe crear un perfil",
    				Toast.LENGTH_LONG).show();
    	}
		
	}
	
	/*
	 * Muestra un diálogo de confirmación
	 * 
	 * */
	private void cargarBorrar(){
		DialogBorrarDatos dbd = new DialogBorrarDatos(this, this);
		dbd.show();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
			case R.id.cambiarOpciones:
										aceptar();
										break;
				
			case R.id.borrarBaseDatos:
										cargarBorrar();
										break;
		}
		
	}
}