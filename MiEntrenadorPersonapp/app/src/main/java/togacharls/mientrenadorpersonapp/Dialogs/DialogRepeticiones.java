package togacharls.mientrenadorpersonapp.Dialogs;

import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import togacharls.mientrenadorpersonapp.Listeners.ListenerRepeticiones;
import togacharls.mientrenadorpersonapp.R;

public class DialogRepeticiones extends Dialogo implements OnClickListener{
	private ListenerRepeticiones listener;
	private Button sonarButton;
	private Button medirButton;
	private Button cancelarButton;
	private Button manualButton;
	
	public DialogRepeticiones(Context c, ListenerRepeticiones l){
		super(c, R.layout.dialog_repeticiones);
		listener = l;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		sonarButton = (Button)findViewById(R.id.drsonarrepeticiones);
		medirButton = (Button)findViewById(R.id.drcontarrepeticiones);
		cancelarButton = (Button)findViewById(R.id.drcancelar);
		manualButton = (Button)findViewById(R.id.drmanual);

		sonarButton.setOnClickListener(this);
		medirButton.setOnClickListener(this);
		manualButton.setOnClickListener(this);
		cancelarButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) { 

		switch(v.getId()){
		
		case R.id.drcancelar:
			dismiss();
			break;		
		
		case R.id.drcontarrepeticiones:
			dismiss();
			listener.contarRepeticiones();
			break;		
			
		case R.id.drsonarrepeticiones:
			dismiss();
			listener.cargarSonarRepeticiones();
			break;		
		
		case R.id.drmanual:
			dismiss();
			listener.cargarManual();
			break;
		}
	}	
}
