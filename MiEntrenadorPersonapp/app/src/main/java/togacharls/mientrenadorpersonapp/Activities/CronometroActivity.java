package togacharls.mientrenadorpersonapp.Activities;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import togacharls.mientrenadorpersonapp.Application.MiEntrenadorPersonapp;
import togacharls.mientrenadorpersonapp.R;

public class CronometroActivity extends AppCompatActivity {
	
	private Button play, stop;
	private Chronometer crono;
	private static TextView horas, minutos;
	private static int h, m;
	private int estado;
	private long memo;
	private Thread threadTemporal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crono);

		h = 0;
		m = 0;
		estado = 0;	//0=stop, 1=On, 2=pause
		play = (Button)findViewById(R.id.startCrono);
		stop = (Button)findViewById(R.id.stopCrono);
		horas = (TextView)findViewById(R.id.cronoH);
		minutos = (TextView)findViewById(R.id.cronoM);
		crono = (Chronometer)findViewById(R.id.cronometro);

		play.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(estado == 0){
					crono.setBase(SystemClock.elapsedRealtime());
					crono.start();
					estado = 1;
					play.setText("Pausar");
				}
				else if(estado == 1){
					memo = SystemClock.elapsedRealtime();
					crono.stop();
					estado = 2;
					play.setText("Continuar");
				}
				else{
					crono.setBase((long)crono.getBase()-memo +SystemClock.elapsedRealtime());
					crono.start();
					estado = 1;
					play.setText("Pausar");
				}
			}
		});

		stop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				crono.stop();
				estado = 0;
				play.setText("Iniciar");
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
	
	@Override
	public void onResume(){
		super.onResume();

		threadTemporal = new Thread(){
			public void run(){
				try{
					long resultado, lastResultado;

					if(MiEntrenadorPersonapp.getTiempoSesion() != 0){
						calcularTiempo();
						lastResultado = System.currentTimeMillis();

						while (true) {
							resultado = System.currentTimeMillis();
							if ((resultado - lastResultado) >= 60000) {
								incrementarMinuto();
								lastResultado = resultado;
							}
						}
					}
					else{
						minutos.setText("XX");
						horas.setText("XX");
					}
					
				}catch(Exception e){
					timeException();
				}
			}
		};
		threadTemporal.start();
	}
	
	@Override
	public void onStop(){
		super.onStop();

		try {
			threadTemporal.join();
		}catch (Exception e){
			e.printStackTrace();
			timeException();
		}
	}	
	
	@Override
	public void onPause(){
		super.onPause();
		try {
			threadTemporal.join();
		}catch (Exception e){
			e.printStackTrace();
			timeException();
		}
	}
	
	/*
	 * Incrementa en 1 el tiempo transcurrido desde que se accedió por primera vez a "Sesión"
	 * 
	 */
	private void incrementarMinuto(){
	    CronometroActivity.this.runOnUiThread(new Runnable() {

	        public void run() {
	        	m++;
				if(m == 60){
					m = 0;
					h++;
					minutos.setText("00");
					horas.setText(String.valueOf(h));
				}
				else{
					if(m < 10){
						minutos.setText("0"+String.valueOf(m));
					}
					else{
						minutos.setText(String.valueOf(m));
					}
				}
	        }
	    });
	}
	
	/*
	 * Calcula el tiempo desde que se accedió por primera vez a "Sesión"
	 * 
	 */
	private void calcularTiempo(){
	    CronometroActivity.this.runOnUiThread(new Runnable() {
	        public void run() {
	        	long tiempoActual = System.currentTimeMillis() - MiEntrenadorPersonapp.getTiempoSesion();
	        
	        		if(tiempoActual >= 3600000){
	        			h = (int)(tiempoActual/360000);
	        			tiempoActual %= 3600000;
	        		}
	        		
	        		if(tiempoActual >= 60000){
	        			m = (int)(tiempoActual/60000);
	        		}
	        		
	        		if(m < 10){
	        			minutos.setText("0"+String.valueOf(m));
	        		}
	        		else{
	        			minutos.setText(String.valueOf(m));
	        		}
	        		horas.setText(String.valueOf(h));
	        }
	    });
	}
	
	/*
	 * Muestra un mensaje de error
	 *	
	 */
	private void timeException(){
	    CronometroActivity.this.runOnUiThread(new Runnable() {
	        public void run() {
	        	Toast.makeText(MiEntrenadorPersonapp.getContext(), "Ha ocurrido un error y el tiempo de la sesión no se está calculando como es debido", Toast.LENGTH_SHORT).show();
	        }
	    });
	}	
}