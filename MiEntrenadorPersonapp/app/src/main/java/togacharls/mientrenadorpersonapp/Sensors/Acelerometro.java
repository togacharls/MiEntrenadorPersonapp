package togacharls.mientrenadorpersonapp.Sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class Acelerometro implements SensorEventListener {
	private boolean activo;
	private SensorManager manager;
	private Sensor sensor;
	private float ruido;
	private float Z, quieto;
	private static int repeticiones, lastSize;
	private ArrayList<Integer> vector;
	private ArrayList<Integer> vauxiliar;
	private ArrayList<Float> vectorValores;
	private long tiempo;
	private double frecuencia;
	
   public Acelerometro( SensorManager s){
    	ruido = (float)0.35;
		vector = new ArrayList<Integer>();		
		vauxiliar = new ArrayList<Integer>();
		vectorValores = new ArrayList<Float>();
        activo = false;
        manager= s;
        repeticiones = 0;
        lastSize = 0;
        tiempo = 0;
        sensor = manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }
    
    /*
     * Inicia el acelerómetro
     * 
     * */
    public void iniciar(){
    	try{
    		Thread.sleep(1000);
    	}catch(Exception e){
    		
    	}
    	lastSize = 0;
    	activo = true;
    	frecuencia = 0;
    	tiempo = 0;
    	manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

	/*
	* Devuelve el númerp de repeticiones después de calcular si corresponde o no incrementarlas.
	* */
    public int getRepeticiones(){
		contarRepeticiones();
    	return repeticiones;
    }
    
	@Override
	public void onSensorChanged(SensorEvent event) {
		Z = event.values[2];
		vectorValores.add(Z);
		
		// /*
		if(frecuencia == 0){
			long tS =  System.nanoTime();
			if(tiempo == 0){
				tiempo = tS;
			}
			else{
				frecuencia = 1000000000.0/((tS - tiempo));
			}			
		}else{

			if (!activo) {
				activo = true;
			}
			if(vector.size() == 0){
				quieto = Z;
				vector.add(0);
			}
			else if(Z > (quieto + ruido) && vector.get(vector.size()-1) <= 0){				
				vauxiliar.add(1);
			}
			else if(Z < (quieto - ruido) && vector.get(vector.size()-1) > 0){
				vauxiliar.add(-1);
			}
			else if(vector.get(vector.size()-1) <= 0){
				vauxiliar.add(0);
			}
			int constante = (int)(frecuencia*0.75);
		
			if(positivos() >= constante && vector.get(vector.size()-1) <= 0){
																			
				vauxiliar.clear();
				vector.add(1);
			}
			else if(negativos() >= constante && vector.get(vector.size()-1) > 0){
				vauxiliar.clear();
				vector.add(-1);
			}
			else if(ceros() >= constante && vector.get(vector.size()-1) < 0){
				vauxiliar.clear();
				vector.add(0);			
			}		
			contarRepeticiones();
		}
	}
	
	/*
	 * Devuelve el número de 1s registrados en el vector
	 * 
	 * */
	private int positivos(){
		int n = 0;
		for(int i=0; i<vauxiliar.size();i++){
			if(vauxiliar.get(i) == 1){
				n++;
			}
		}
		return n;
	}
	
	/*
	 * Devuelve el número de -1s registrados en el vector
	 * 
	 * */
	private int negativos(){
		int n = 0;
		for(int i=0; i<vauxiliar.size();i++){
			if(vauxiliar.get(i) == -1){
				n++;
			}
		}
		return n;
	}
	
	
	/*
	 * Devuelve el número de 0s registrados en el vector
	 * 
	 * */
	private int ceros(){
		int n = 0;
		for(int i=0; i<vauxiliar.size();i++){
			if(vauxiliar.get(i) == 0){
				n++;
			}
		}
		return n;
	}	
	
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    
    public SensorManager getManager(){
    	return manager;
    }

    /*
     * Apaga el acelerómetro.
     * 
     * */
    public void apagar(){
    	lastSize = 0;
    	repeticiones = 0;
    	vector.clear();
    	manager.unregisterListener(this, sensor);
    	activo = false;
    }
    
    public boolean getActivo(){
    	return activo;
    }
    
    /*
     * Devuelve		True si el Smartphone tiene acelerómetro
     * 				False si el Smartphone no tiene acelerómetro
     * */
    public boolean tieneAcelerometro(){
    	List<Sensor> sensores = manager.getSensorList(Sensor.TYPE_LINEAR_ACCELERATION);
    	if(sensores.size() > 0) return true;
    	else return false;
    }

    /*
     * Incrementa el número de repeticiones registradas si corresponde.
     * 
     * */
    private void contarRepeticiones(){
    	if(vector.size() >= 3 && vector.size() > lastSize){
    			if(vector.get(vector.size()-2) == 1 && vector.get(vector.size()-1) == -1){
    				repeticiones++;
    			}
    			lastSize = vector.size();		
    	}
    }
}
