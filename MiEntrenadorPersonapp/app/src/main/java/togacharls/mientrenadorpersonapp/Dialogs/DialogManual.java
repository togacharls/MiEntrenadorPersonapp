package togacharls.mientrenadorpersonapp.Dialogs;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import togacharls.mientrenadorpersonapp.Listeners.ListenerIntroduccionRepeticionesManual;
import togacharls.mientrenadorpersonapp.R;

public class DialogManual extends Dialogo implements OnClickListener {
	private ListenerIntroduccionRepeticionesManual listener;
	private Button aceptarButton;
	private Button cancelarButton;
	private EditText repeticiones;
	private Context contexto;
	
	public DialogManual(Context context, ListenerIntroduccionRepeticionesManual l){		
		super(context, R.layout.dialog_repeticiones_manual);
		listener = l;
		contexto = context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTitle("Registro manual");
		repeticiones = (EditText) findViewById(R.id.dimr);
		aceptarButton = (Button) findViewById(R.id.dimaceptar);
		cancelarButton = (Button) findViewById(R.id.dimcancelar);

		aceptarButton.setOnClickListener(this);
		cancelarButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.dimaceptar:
					if(repeticiones.getText().toString() != null){
						listener.repeticionesManual(Integer.valueOf(repeticiones.getText().toString()));
						dismiss();
					}
					else{
						Toast.makeText(contexto, "Introduzca una carga en formato válido", Toast.LENGTH_SHORT).show();
					}
					break;
					
			case R.id.dimcancelar:
					dismiss();
					break;
		}	
	}
}
