package com.momszil.rapbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends Activity {

	@InjectView(R.id.buttonNewSong) Button bNewSong;

    @OnClick(R.id.buttonNewSong)
    public void newSongActivity() {
        Intent intentNewSong = new Intent(MainActivity.this, NewSongActivity.class);
        startActivity(intentNewSong);
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



}
