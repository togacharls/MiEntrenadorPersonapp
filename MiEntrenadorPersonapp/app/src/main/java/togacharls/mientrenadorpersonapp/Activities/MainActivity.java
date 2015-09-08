package togacharls.mientrenadorpersonapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Button;

import android.content.Intent;
import android.view.View;

import togacharls.mientrenadorpersonapp.R;

public class MainActivity extends AppCompatActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Button iniciarEntrenamiento = (Button)this.findViewById(R.id.buttonSesion);
		final Button rutinas = (Button)this.findViewById(R.id.buttonRutinas);
		final Button condicionFisica = (Button)this.findViewById(R.id.buttonPerfil);
		final Button resultados = (Button)this.findViewById(R.id.buttonResultados);

		iniciarEntrenamiento.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SesionActivity.class);
				startActivity(intent);
			}
		});

		rutinas.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, RutinaActivity.class);
				startActivity(intent);
			}
		});

		condicionFisica.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
				startActivity(intent);
			}
		});

		resultados.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ResultadosActivity.class);
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
}