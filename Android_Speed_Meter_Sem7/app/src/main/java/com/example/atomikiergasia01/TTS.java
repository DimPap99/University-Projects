package com.example.atomikiergasia01;


import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import androidx.annotation.RequiresApi;

import java.util.Locale;

public class TTS {

    private TextToSpeech tts;
    private TextToSpeech.OnInitListener initListener=
            new TextToSpeech.OnInitListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onInit(int i) {
                    tts.setLanguage(Locale.ENGLISH);
                } //choose english as language
            };
    public TTS(Context context) {
        tts = new TextToSpeech(context,initListener);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void speak(String message){
        tts.speak(message,TextToSpeech.QUEUE_ADD,null,null);
    } // use the tts object to speak the message provided on the method


}
