package togacharls.mientrenadorpersonapp.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import togacharls.mientrenadorpersonapp.Listeners.ListenerBorrarDatos;
import togacharls.mientrenadorpersonapp.R;

public class DialogBorrarDatos extends Dialogo implements OnClickListener {
	private Button aceptarButton;
	private Button cancelarButton;
	private ListenerBorrarDatos listener;
	
	public DialogBorrarDatos(Context c, ListenerBorrarDatos l){
		super(c, R.layout.dialog_borrar);
		listener = l;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		aceptarButton = (Button)findViewById(R.id.dbaceptar);
		cancelarButton = (Button)findViewById(R.id.dbcancelar);

		aceptarButton.setOnClickListener(this);
		cancelarButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.dbaceptar:
								listener.borrar();
								dismiss();
								break;
								
		case R.id.dbcancelar:
								dismiss();
								break;
								
		}
	}
}
