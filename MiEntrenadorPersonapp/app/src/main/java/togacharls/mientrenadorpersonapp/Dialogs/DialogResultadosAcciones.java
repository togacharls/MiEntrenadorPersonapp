package togacharls.mientrenadorpersonapp.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import togacharls.mientrenadorpersonapp.Listeners.ListenerResultadosAcciones;
import togacharls.mientrenadorpersonapp.R;

public class DialogResultadosAcciones extends Dialogo implements OnClickListener{
	private Button facebookButton;
	private Button emailButton;
	private Button telefonoButton;
	private Button cancelarButton;
	private ListenerResultadosAcciones listener;
	
	public DialogResultadosAcciones(Context context, ListenerResultadosAcciones l){		
		super(context, R.layout.dialog_resultados_acciones);
		listener = l;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTitle("ResultadosActivity");
		cancelarButton = (Button)findViewById(R.id.dracancelar);
		facebookButton = (Button)findViewById(R.id.drafacebook);
		emailButton = (Button)findViewById(R.id.draemail);
		telefonoButton = (Button)findViewById(R.id.draguardarimagen);

		cancelarButton.setOnClickListener(this);
		facebookButton.setOnClickListener(this);
		emailButton.setOnClickListener(this);
		telefonoButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) { 

		switch(v.getId()){
		
		case R.id.drafacebook:
			listener.facebook();
			dismiss();
			break;		
		
		case R.id.draemail:
			listener.email();
			dismiss();
			break;		
			
		case R.id.draguardarimagen:
			listener.guardarimagen();
			dismiss();
			break;		
		
		case R.id.dracancelar:
			dismiss();
			break;
		}
	}	
}
