package togacharls.mientrenadorpersonapp.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

import togacharls.mientrenadorpersonapp.Listeners.ListenerCarga;
import togacharls.mientrenadorpersonapp.R;

public class DialogCarga extends Dialogo implements OnClickListener{
	
	private EditText carga;
	private Button aceptar, cancelar;
	private ListenerCarga listener;
	
	public DialogCarga(Context context, ListenerCarga l){		
		super(context, R.layout.dialog_carga);
		listener = l;

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTitle("Seleccionar carga");
		carga =(EditText)findViewById(R.id.dck);
		carga.setInputType(InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL);
		aceptar = (Button)findViewById(R.id.dcaceptar);
		cancelar = (Button)findViewById(R.id.dccancelar);
		aceptar.setOnClickListener(this);
		cancelar.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) { 

		switch(v.getId()){

		case R.id.dcaceptar:
			if(Float.valueOf(carga.getText().toString()) != null){
				listener.carga( Float.valueOf(carga.getText().toString() ));
			}
			else{
				listener.carga((float)0.0);
			}
			dismiss();
			break;	

		case R.id.dccancelar:
			dismiss();
			break;			
		}
	}	
}
