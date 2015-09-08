package togacharls.mientrenadorpersonapp.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.text.InputType;

import togacharls.mientrenadorpersonapp.Listeners.ListenerSonarRepeticiones;
import togacharls.mientrenadorpersonapp.R;

public class DialogSonarRepeticiones extends Dialogo implements OnClickListener{
	
	private EditText vecesEditText;
	private EditText intervaloEditText;
	private Button aceptarButton;
	private Button cancelarButton;
	private ListenerSonarRepeticiones listener;
	
	public DialogSonarRepeticiones(Context context, ListenerSonarRepeticiones l){		
		super(context, R.layout.dialog_sonar_repeticiones);
		listener = l;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTitle("Sonar repeticiones");
		vecesEditText = (EditText)findViewById(R.id.dmrveces);
		intervaloEditText = (EditText)findViewById(R.id.dmrsegs);
		intervaloEditText.setInputType(InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL);
		aceptarButton = (Button)findViewById(R.id.dmraceptar);
		cancelarButton = (Button)findViewById(R.id.dmrcancelar);
		aceptarButton.setOnClickListener(this);
		cancelarButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) { 

		switch(v.getId()){

		case R.id.dmraceptar:

			int iveces;
			float itiempo;
			if(Integer.valueOf(vecesEditText.getText().toString()) == null ||
			Float.valueOf(intervaloEditText.getText().toString()) == null){
				listener.errorSonar();
			}
			else{
				iveces = Integer.valueOf(vecesEditText.getText().toString());
				itiempo = Float.valueOf(intervaloEditText.getText().toString());
				dismiss();
				listener.sonarRepeticiones(itiempo, iveces);
			}
			break;	

		case R.id.dmrcancelar:
			listener.sonarRepeticiones((float)0, 0);
			dismiss();
			break;			
		}
	}	
}