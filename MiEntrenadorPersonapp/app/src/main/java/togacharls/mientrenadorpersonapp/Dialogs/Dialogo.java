package togacharls.mientrenadorpersonapp.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.content.Context;

public class Dialogo extends Dialog{
	
	private int layout;
	
	public Dialogo(Context c, int l){
		super(c);
		layout = l;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(layout);
	}

}
