package togacharls.mientrenadorpersonapp.Dialogs;

import android.content.Context;
import android.widget.ListView;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import android.view.View.OnClickListener;

import togacharls.mientrenadorpersonapp.Application.MiEntrenadorPersonapp;
import togacharls.mientrenadorpersonapp.ListView.AdapterListView;
import togacharls.mientrenadorpersonapp.Listeners.ListenerMusculos;
import togacharls.mientrenadorpersonapp.R;

public class DialogMusculos extends Dialogo implements OnClickListener{
	
	private ListView listView;
	private ArrayList<String> musculos;
	private ListenerMusculos listenerMusculos;
	private Button cancelarButton;
	private Context contexto;
	
	public DialogMusculos(Context context, ListenerMusculos l){
		//Igual context tiene que ser una Activity y no un Context
		super(context, R.layout.dialog_musculos);
		contexto = context;
		listenerMusculos = l;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		cancelarButton = (Button) findViewById(R.id.dmcancelar);
		listView = (ListView) findViewById(R.id.dmlista);
		setTitle("Grupo muscular");
		musculos = MiEntrenadorPersonapp.listaMusculos();

		//AdapterListView adapter = new AdapterListView(contexto, R.layout.elementolista, musculos, null);
		AdapterListView adapter = new AdapterListView(R.layout.elementolista, musculos, null);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String selected = ((TextView) view.findViewById(R.id.nombreElemento)).getText().toString();
				dismiss();
				listenerMusculos.mostrarEjGrupoMuscular(selected);
			}
		});

		cancelarButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		case R.id.dmcancelar:
			dismiss();
			break;
		}
	}	
}
