package com.momszil.rapbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NewSongActivity extends Activity{

    @InjectView(R.id.editTextRijec) EditText etRijec;
    @InjectView(R.id.buttonRimuj) Button bRimujMe;
    @InjectView(R.id.buttonGlas) Button btnGlas;

	protected static final int RIMOVANJE = 1;

    @OnClick(R.id.buttonRimuj)
    public void rimujMe() {
        Intent intentRimuj = new Intent(NewSongActivity.this, RhymeActivity.class);
        intentRimuj.putExtra("RIJEC", etRijec.getText());
        startActivityForResult(intentRimuj, RIMOVANJE);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_song);

        ButterKnife.inject(this);

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode) {
		case RIMOVANJE:
			
		}
	}

}
