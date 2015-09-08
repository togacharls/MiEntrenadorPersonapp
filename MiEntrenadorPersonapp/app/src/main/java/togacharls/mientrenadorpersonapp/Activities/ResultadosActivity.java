package togacharls.mientrenadorpersonapp.Activities;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View.MeasureSpec;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

//TODO Facebook
import com.facebook.*;


import togacharls.mientrenadorpersonapp.Application.MiEntrenadorPersonapp;
import togacharls.mientrenadorpersonapp.Database.Serie;
import togacharls.mientrenadorpersonapp.Dialogs.DialogResultadosAcciones;
import togacharls.mientrenadorpersonapp.Dialogs.DialogResultadosSeleccionar;
import togacharls.mientrenadorpersonapp.Listeners.ListenerResultadosAcciones;
import togacharls.mientrenadorpersonapp.Listeners.ListenerResultadosSeleccionar;
import togacharls.mientrenadorpersonapp.Services.Mail;
import togacharls.mientrenadorpersonapp.R;

public class ResultadosActivity extends AppCompatActivity implements ListenerResultadosAcciones, ListenerResultadosSeleccionar {
	
	  private XYMultipleSeriesDataset mDataset;
	  private XYMultipleSeriesRenderer mRenderer;
	  private XYSeries mCurrentSeries;
	  private XYSeriesRenderer mCurrentRenderer;
	  private GraphicalView mChartView;
	  private Button acciones, ver;
	  private LinearLayout layout;
	  //private SimpleFacebook mSimpleFacebook;
	  private Mail m;
	  private boolean primero;
	  private ProgressDialog mProgress;
	  protected String fecha, ejercicio;
	  private ArrayList<Float> cargas;
	  
	/*private OnLoginListener mOnLoginListener = new OnLoginListener() {
			@Override
			public void onFail(String reason) {
				Log.w("MONLOGINLISTENER", "Failed to login");
			}

			@Override
			public void onException(Throwable throwable) {
				Log.e("MONLOGINLISTENER", "Bad thing happened", throwable);
			}

			@Override
			public void onThinking() {
				showDialog();
			}

			@Override
			public void onLogin() {
				hideDialog();
				Toast.makeText(pContext, "Estás conectado con tu cuenta de Facebook", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNotAcceptingPermissions(Permission.Type type) {
				Toast.makeText(pContext, "No aceptaste los permisos de publicación", Toast.LENGTH_SHORT).show();
			}
	};*/
	  
	  @Override
	  protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putSerializable("dataset", mDataset);
	    outState.putSerializable("renderer", mRenderer);
	    outState.putSerializable("current_series", mCurrentSeries);
	    outState.putSerializable("current_renderer", mCurrentRenderer);
	  }	 
	  
	  @Override
	  protected void onRestoreInstanceState(Bundle savedState) {
	    super.onRestoreInstanceState(savedState);
	    mDataset = (XYMultipleSeriesDataset) savedState.getSerializable("dataset");
	    mRenderer = (XYMultipleSeriesRenderer) savedState.getSerializable("renderer");
	    mCurrentSeries = (XYSeries) savedState.getSerializable("current_series");
	    mCurrentRenderer = (XYSeriesRenderer) savedState.getSerializable("current_renderer");
	  }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resultados);

		cargas = new ArrayList<Float>();
		primero = true;
		acciones = (Button)findViewById(R.id.resultadosacciones);
		ver = (Button)findViewById(R.id.resultadosverresultados);

		final ResultadosActivity r = this;
		acciones.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(ejercicio != null && fecha != null && !ejercicio.equals("") && !fecha.equals("")){
					DialogResultadosAcciones dra = new DialogResultadosAcciones(MiEntrenadorPersonapp.getContext(), r);
					dra.show();
				}
				else{
					Toast.makeText(MiEntrenadorPersonapp.getContext(), "No se han cargado ningún ejercicio ni fecha", Toast.LENGTH_SHORT).show();
				}
			}
		});

		ver.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				DialogResultadosSeleccionar drs = new DialogResultadosSeleccionar(MiEntrenadorPersonapp.getContext(), r);
				drs.show();
			}
		});
		init();

	}
     
	  /*
	   * Se inician los atributos que son necesarios para generar la gráfica
	   * 
	   * */

	private void init(){
		mDataset = new XYMultipleSeriesDataset();
		mRenderer = new XYMultipleSeriesRenderer();

		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.WHITE);
		mRenderer.setAxisTitleTextSize(16);
		mRenderer.setChartTitleTextSize(20);
		mRenderer.setLabelsTextSize(10);
		mRenderer.setLegendTextSize(20);
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setPointSize(5);
		  
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setDisplayChartValues(true);
		renderer.setDisplayChartValuesDistance(10);
	   	
		mCurrentSeries = new XYSeries("");
		obtenerDatos();
		mDataset.addSeries(mCurrentSeries);
		mRenderer.addSeriesRenderer(renderer);
		mCurrentSeries.setTitle("");
	      
		final String APP_ID = "828073903876122";
		final String APP_NAMESPACE = "mientrenadorpersonapp";
	      
		/*Logger.DEBUG_WITH_STACKTRACE = true;

		Permission[] permissions = new Permission[] {
				Permission.FRIENDS_EVENTS,
				Permission.PUBLISH_STREAM };

		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
			.setAppId(APP_ID)
			.setNamespace(APP_NAMESPACE)
			.setPermissions(permissions)
			.setDefaultAudience(SessionDefaultAudience.FRIENDS)
			.setAskForAllPermissionsAtOnce(false)
			.build();
	      
		SimpleFacebook.setConfiguration(configuration);
	    mSimpleFacebook = SimpleFacebook.getInstance(this);*/
	  }
	  
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
	}
    
    @Override
    protected void onResume(){
    	super.onResume();
    	cargarChart();
    }
    
    /*
     * Carga la imagen que contiene la gráfica
     * 
     * */
    private void cargarChart(){
    	if (mChartView == null) {
    		layout = (LinearLayout) findViewById(R.id.chartLayout);
    		mChartView = ChartFactory.getLineChartView(MiEntrenadorPersonapp.getContext(), mDataset, mRenderer);
    	    mRenderer.setClickEnabled(true);
    	    mRenderer.setSelectableBuffer(10);
    	       
    	    mChartView.setOnClickListener(new View.OnClickListener() {
    	    	public void onClick(View v) {
    	    		SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
    	    		if (seriesSelection != null) {
    	              // Mostrar la informaci�n al hacer click en un punto:
    	              Toast.makeText(MiEntrenadorPersonapp.getContext(),
    	            		  "Carga=" + cargas.get((int)seriesSelection.getXValue()) +
    	            		  ", Repeticiones="+ seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
    	            }
    	          }
    	        });
    	    layout.addView(mChartView);
    	} else {
    		mChartView.setZoomRate((float)3.0);
    		mChartView.repaint();
    	}    	
    }
    
   /*
    * Obtiene los resultados sobre el ejercicio y la fecha especificados
    * en el diálogo.
    * 
    * */
    private void obtenerDatos(){
		ArrayList<Serie> series = MiEntrenadorPersonapp.obtenerSeries(ejercicio, fecha);
    	mCurrentSeries.clear();
    	mCurrentSeries.setTitle("");
    	cargas.clear();
    	if(!series.isEmpty()){
			for (Serie serie: series){
				mCurrentSeries.add(serie.getnDiaria(), serie.getRepeticiones());
				cargas.add(serie.getCarga());
				mCurrentSeries.addAnnotation("\nC:"+ serie.getCarga() +"\nR:"+ serie.getRepeticiones(), serie.getnDiaria(), serie.getRepeticiones());
			}
    		mCurrentSeries.setTitle(ejercicio);
    	}
    	else{
    		if(primero == true){
    			primero = false;
    		}
    		else{
    			Toast.makeText(MiEntrenadorPersonapp.getContext(), "No se han encontrado series del ejercicio:\n" +
    					ejercicio +"\nEn la fecha: " + fecha, Toast.LENGTH_LONG).show();
    		}
    	}
    	cargarChart();
    }
    
    /*
     * Envía la gráfica por correo electrónico. En caso de error, notifica.
     * */
    public void sendEmail(final String asunto, final String mensaje, final String destino){    	
    	Thread th = new Thread(){
    		public void run(){
    			String[] toArr =  {destino};
    			m.setTo(toArr);
    			m.setSubject(asunto); 
    			m.setBody(mensaje); 
    	 
    			try {
    				m.addAttachment(Environment.getExternalStorageDirectory() + "/MiEntrenadorPersonapp/" +
    						"temp/"+ejercicio + fecha +".jpg");  
    			    ResultadosActivity.this.runOnUiThread(new Runnable() {
    			        public void run() {
    			        	Toast.makeText(MiEntrenadorPersonapp.getContext(), "Enviando mensaje...", Toast.LENGTH_SHORT).show();
    			        }
    			    });    				
    				
    				String mensaje = "";
    				switch(m.send())
    				{
    					case 1:		mensaje = "Mail enviado correctamente";
    								break;
    					
    					case 0:		mensaje = "Error interno 0: Algún campo vacío";
    								break;
    								
    					case -1:	mensaje = "Error interno -1: Sesión nula";
    								break;
    					
    					case -2:	mensaje = "Error interno -2: Correo electrónico del emisor erróneo";
    								break;					
    					
    					case -3:	mensaje = "Error -3: Correo electrónico de destino erróneo";
    								break;				
    					
    					case -4:	mensaje = "Error -4: Correo electrónico de destino erroóneo";
    								break;
    								
    					case -5:	mensaje = "Error -5: Error en el Asunto"; 
    								break;
    								
    					case -6:	mensaje = "Error -6: Error al definir la fecha"; 
    								break;
    								
    					case -7:	mensaje = "Error -7: Error al definir el contenido del mensaje"; 
    								break;
    								
    					case -8:	mensaje = "Error -8: Error al añadir BODYPART";
    								break;
    								
    					case -9:	mensaje = "Error -9: Error al añadir MULTIPART";
    								break;
    								
    					case -10:	mensaje = "Error -10: Error al definir protocolo de envío";
    								break;
    								
    					case -11:	mensaje = "Error -11: Error de conexión";
    								break;

    					case -12:	mensaje = "Error -12: Error al enviar el correo "; 
    								break;
    		
    					case -13:	mensaje = "Error -13: Error al cerrar sesión de correo";
    								break;	
    				}
    				final String m = mensaje;

    			    ResultadosActivity.this.runOnUiThread(new Runnable() {
    			        public void run() {
    			        	Toast.makeText(MiEntrenadorPersonapp.getContext(), m, Toast.LENGTH_SHORT).show();
    			        	eliminarImagen();		//Se elimina la imagen del dispositivo para que no ocupe espacio
    			        }
    			    });			
    			} catch(final Exception e) { 
    			    ResultadosActivity.this.runOnUiThread(new Runnable() {
    			        public void run() {
    			        	Toast.makeText(MiEntrenadorPersonapp.getContext(), "Ha ocurrido una excepción", Toast.LENGTH_SHORT).show();
    			        	Log.w("Excepción:" ,e.getMessage());
    			        }
    			    });	
    			}   			
    		}
    	};
    	th.start();
	}
    
    /*
     * Crea un fichero .jpg a partir de la imagen de la gr�fica. Este fichero puede ser almacenado
     * temporalmente o de forma permanente, seg�n elija el usuario.
     * 
     * */
    protected void convertToImage(int modo){
        if(ejercicio!= null && fecha != null && !ejercicio.equals("") && !fecha.equals("")){	
        	layout.setDrawingCacheEnabled(true);
        	layout.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
        	MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        	layout.buildDrawingCache(true);
    	
        	Bitmap image = Bitmap.createBitmap(layout.getDrawingCache());
        	layout.setDrawingCacheEnabled(false);
        	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        	image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        	File directorio;
        	boolean permanente;
        	if(modo == 0){
        		directorio = new File(Environment.getExternalStorageDirectory() + "/MiEntrenadorPersonapp/temp");
        		permanente = false;
        	}	
        	else{
        		directorio = new File(Environment.getExternalStorageDirectory() + "/MiEntrenadorPersonapp/");
        		 permanente = true;
        	}
        	if(!directorio.isDirectory()){
        		directorio.mkdir();
        	}
        
        	File f = new File(directorio, ejercicio + fecha + ".jpg");
        	boolean made = true;
        	try {
        		f.createNewFile();
        		FileOutputStream fo = new FileOutputStream(f);
        		fo.write(bytes.toByteArray());
        		fo.close();
        	} catch (Exception e) {
        		made = false;
        		Toast.makeText(MiEntrenadorPersonapp.getContext(), "Ha ocurrido un error al crear la imagen", Toast.LENGTH_SHORT).show();
        	}
        	if(made && permanente){
        		Toast.makeText(MiEntrenadorPersonapp.getContext(), "Imagen creada y almacenada", Toast.LENGTH_SHORT).show();
        	}
        }
    }
    
    /*
     * Borra la imagen temporal.
     * 
     * */
    protected void eliminarImagen(){
    	 if(ejercicio!= null && fecha != null && !ejercicio.equals("") && !fecha.equals("")){
			File directorio = new File(Environment.getExternalStorageDirectory() + "/MiEntrenadorPersonapp/temp");
			if(!directorio.isDirectory()){
				directorio.mkdir();
			}
		
			File f = new File(directorio, ejercicio + fecha +".jpg");
			if(f.exists()){
				try {
					if( f.delete()){
						//Toast.makeText(MiEntrenadorPersonapp.getContext(), "Imagen eliminada del teléfono", Toast.LENGTH_SHORT).show();
					}
					else{
						Toast.makeText(MiEntrenadorPersonapp.getContext(), "No se pudo eliminar la imagen temporal," +
        	   				" por lo que puede que siga almacenada en el dispositivo", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					Toast.makeText(MiEntrenadorPersonapp.getContext(), "Ha ocurrido un error al eliminar la imagen", Toast.LENGTH_SHORT).show();
				}
			}
    	}
    }
    
    /*
     * Publica la gráfica en Facebook
     * 
     * */
    /*private void publicarFoto(){
    	layout.setDrawingCacheEnabled(true);
		final Bitmap bitmap = Bitmap.createBitmap(layout.getDrawingCache());

		Privacy privacy = new Privacy.Builder()
			.setPrivacySettings(PrivacySettings.SELF)
			.build();
	
		Photo photo = new Photo.Builder()
			.setImage(bitmap)
			.setName("ResultadosActivity de mi seguimiento")
			.setPlace(null)	
			.setPrivacy(privacy)
			.build();


		mSimpleFacebook.publish(photo, new OnPublishListener() {
			@Override
			public void onFail(String reason) {
				hideDialog();
				Log.w("PUBLICAR FOTO", "Failed to publish");
				Toast.makeText(MiEntrenadorPersonapp.getContext(), reason, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onException(Throwable throwable) {
				hideDialog();
				Log.e("PUBLICAR FOTO", "Bad thing happened", throwable);
			}

			@Override
			public void onThinking() {
				showDialog();
			}

			@Override
			public void onComplete(String id) {
				hideDialog();
				Toast.makeText(MiEntrenadorPersonapp.getContext(),"Publicación llevada a cabo con éxito", Toast.LENGTH_SHORT).show();
			}
		});
    }*/
    
	private void showDialog() {
		mProgress = ProgressDialog.show(this, "Thinking", "Waiting for Facebook", true);
	}

	private void hideDialog() {
		if (mProgress != null) {
			mProgress.hide();
		}
	}
	//--------------------------------------------------------------------------------------
	//Implementación de los Listeners
	/**/
	@Override
	public void guardarimagen() {
		convertToImage(1);
	}

	/*
	* Se envía un correo electrónico al usuario
	* */
	@Override
	public void email() {
		  String asunto = "Mi Entrenador Personapp";
		  String texto = "Los resultados se encuentran adjuntos en este correo.";	
		  String destino = MiEntrenadorPersonapp.obtenerEmail();
		  
		  if(!destino.isEmpty()){
			  if(ejercicio!= null && fecha != null && !ejercicio.equals("") && !fecha.equals("")){
				  try {   
				  //Enviar el correo
					  m = new Mail();
					  convertToImage(0);
					  sendEmail(asunto, texto, destino);
				  } catch (Exception e) {
					  Toast.makeText(MiEntrenadorPersonapp.getContext(), "Ha ocurrido un error y no se ha podido " +
				  		"enviar el correo", Toast.LENGTH_SHORT).show();
				  }
			  }
			  else{
				  Toast.makeText(MiEntrenadorPersonapp.getContext(), "No se han cargado ningún ejercicio ni fecha", Toast.LENGTH_SHORT).show();
			  }
		  }
		  else{
			  Toast.makeText(MiEntrenadorPersonapp.getContext(), "Para recibir el correo electrónico, antes debes anotarlo en tu perfil", Toast.LENGTH_SHORT).show();
		  }
	}

	/*
	* Se encarga de conectarse a Facebook y de publicar la imagen
	* */
	@Override
	public void facebook() {
		/*
		  if(! mSimpleFacebook.isLogin()){
			  try{
				  mSimpleFacebook.login(mOnLoginListener);
			  }catch(Exception e){
				  
			  }
			  finally{
				  Toast.makeText(pContext, "Presione de nuevo el botón para realizar la publicación", Toast.LENGTH_SHORT).show();
			  }
		  }
		  else{
			  publicarFoto();
		  }
		  */
	}

	/*
	* Al cerrar la actividad, debe borrarse la imagen salvo que se haya pedido expresamente que se almacene en el dispositivo
	* */
	@Override
	public void onDestroy(){
		eliminarImagen();
		super.onDestroy();
	}
	
	@Override
	public ArrayList<String> obtenerEjerciciosDia(String date){
		ArrayList<String> ejs = MiEntrenadorPersonapp.obtenerEjerciciosSeries(date);
		return ejs;
	}
	@Override
	public void ejercicio(String ej){
		ejercicio = ej;
		mCurrentSeries.setTitle("\n" + ej + "\n" + fecha + "\n");
		obtenerDatos();
		 
	}
}