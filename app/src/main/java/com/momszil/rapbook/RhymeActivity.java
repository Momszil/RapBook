package com.momszil.rapbook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class RhymeActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.activity_rhyme);

	    String rijec = getIntent().getExtras().getString("RIJEC");
        Toast.makeText(getApplicationContext(), rijec, Toast.LENGTH_LONG).show();
	    
	}
}
