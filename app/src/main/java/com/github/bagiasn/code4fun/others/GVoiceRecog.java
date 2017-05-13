package com.github.bagiasn.code4fun.others;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

/**
 * This class handles all the NLP functionality.
 */

public class GVoiceRecog implements Runnable {
    private Context context;
    private SpeechRecognizer speechRecognizer;
    private Intent recognitionIntent;

    public GVoiceRecog(Context context) {
        this.context = context;
        setupIntent();
    }
    private void setupIntent() {
        recognitionIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "el-GR");
        recognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "el-GR");
        recognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognitionIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());
    }

    private void close() {
        if (speechRecognizer != null) {
            speechRecognizer.setRecognitionListener(null);
            speechRecognizer.destroy();
            speechRecognizer = null;
        }
    }
    @Override
    public void run() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechRecognizer.setRecognitionListener(new NlpListener());
        speechRecognizer.startListening(recognitionIntent);
    }

    private class NlpListener implements RecognitionListener {
        private final String TAG = getClass().getSimpleName();

        public void onReadyForSpeech(Bundle params) {
        }

        public void onBeginningOfSpeech() {
            Log.d(TAG, "BOS");
        }

        public void onRmsChanged(float rmsdB) {
        }

        public void onBufferReceived(byte[] buffer) {
        }

        public void onEndOfSpeech() {
            Log.d(TAG, "EOS");
        }

        public void onError(int error) {
            Log.d(TAG, "Error " + String.valueOf(error));
            close();
        }

        public void onResults(Bundle results) {
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (data == null || data.size() == 0) {
                close();
                return;
            }
            String text = (String) data.get(0);
            Log.d(TAG, text);
            close();
        }

        public void onPartialResults(Bundle partialResults) {
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, String.valueOf(eventType));
        }
    }
}
