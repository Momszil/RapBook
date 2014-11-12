package com.momszil.rapbook;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.momszil.rapbook.Listener.SpeechRecognitionListener;
import com.momszil.rapbook.Network.RimujMeFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NewSongActivity extends FragmentActivity {

    private static final int SPEECH_REQUEST_CODE = 0;
    protected static final int RIMOVANJE = 1;

    private List<String> results;
    SpeechRecognizer sr;
    Bundle args = new Bundle();
    RimujMeFragment rimuj;

    @InjectView(R.id.editTextRijec)
    EditText etRijec;
    @InjectView(R.id.buttonRimuj)
    Button bRimujMe;
    @InjectView(R.id.buttonGlas)
    Button btnGlas;

    @OnClick(R.id.buttonRimuj)
    public void rimujMe() {
        if (etRijec.getText().toString().length() > 0) {
            findViewById(R.id.spinner).setVisibility(View.VISIBLE);
            args.putString("WORD", etRijec.getText().toString());
            rimuj.setArguments(args);
        }

        //Toast.makeText(this, results.get(0), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonGlas)
    public void slusajMe() {
        // Create an intent that can start the Speech Recognizer activity

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.momszil.rapbook.Listener");

        // Start the activity, the intent will be populated with the speech text
        //startActivityForResult(intent, SPEECH_REQUEST_CODE);
        sr.startListening(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_song);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        rimuj = new RimujMeFragment();
        fragmentTransaction.add(R.id.fragmentRimuj, rimuj);
        fragmentTransaction.commit();

        ButterKnife.inject(this);

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        SpeechRecognitionListener srListener = new SpeechRecognitionListener();
        sr.setRecognitionListener(srListener);
        //sr.startListening(RecognizerIntent.getVoiceDetailsIntent(this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // This callback is invoked when the Speech Recognizer returns.
                case SPEECH_REQUEST_CODE:
                    results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String spokenText = results.get(0);
                    btnGlas.setText(spokenText);
                    break;
                case RIMOVANJE:
                    break;

            }
        }
    }

}
