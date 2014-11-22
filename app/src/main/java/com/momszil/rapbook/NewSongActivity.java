package com.momszil.rapbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.momszil.rapbook.fragment.LajneFragment;
import com.momszil.rapbook.fragment.RimujMeFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NewSongActivity extends FragmentActivity implements RecognitionListener {


    SpeechRecognizer sr;
    Bundle args = new Bundle();
    RimujMeFragment rimuj;
    LajneFragment lajne;
    InputMethodManager imm;

    @InjectView(R.id.editTextRijec)
    EditText etRijec;
    @InjectView(R.id.buttonRimuj)
    Button bRimujMe;
    @InjectView(R.id.buttonGlas)
    Button btnGlas;

    @OnClick(R.id.buttonRimuj)
    public void rimujMe() {
        if (etRijec.getText().toString().length() > 0) {

            imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etRijec.getWindowToken(), 0);

            args.putString("WORD", etRijec.getText().toString());
            rimuj = new RimujMeFragment();
            rimuj.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentRimuj, rimuj).commit();

        }
    }

    @OnClick(R.id.buttonGlas)
    public void slusajMe() {
        // Create an intent that can start the Speech Recognizer activity

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());

        // Start the activity, the intent will be populated with the speech text
        //startActivityForResult(intent, SPEECH_REQUEST_CODE);
        sr.startListening(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("CREATE", "on create called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_song);

        ButterKnife.inject(this);

    }

    @Override
    protected void onResume() {
        Log.i("RESUME", "on resume called");
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(this);
        //sr.startListening(RecognizerIntent.getVoiceDetailsIntent(this));

        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("PAUSE", "on pause called");
        if (sr!=null) {
            sr.stopListening();
            sr.cancel();
            sr.destroy();
        }
        sr = null;
        super.onPause();
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        //Log.d("Speech", "onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        //Log.d("Speech", "onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        //Log.d("Speech", "onRmsChanged");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        //Log.d("Speech", "onBufferReceived");
    }

    @Override
    public void onEndOfSpeech() {
        //Log.d("Speech", "onEndOfSpeech");
    }

    @Override
    public void onError(int error) {
        //Log.d("Speech", "onError");
    }

    @Override
    public void onResults(Bundle results) {
        Log.d("SpeechVraceno", "onResults");

        lajne = new LajneFragment();
        args.putStringArrayList("RESULTS", results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));
        lajne.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLajne, lajne).commit();
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        //Log.d("Speech", "onPartialResults");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        //Log.d("Speech", "onEvent");
    }
}
