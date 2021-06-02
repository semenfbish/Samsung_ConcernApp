package com.example.concern;

import android.annotation.SuppressLint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.view.inputmethod.InputConnection;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class AudioRecorder extends AppCompatActivity {
    Thread audioThread;
    public boolean usingMicro = false;
    public int language = 1;
    private KeyboardView kv;
    private Keyboard keyboard;
    private boolean caps = false;
    public void start(InputConnection ic) {
        // getting pitch from live audio , and sending it to processPitch()

        int SAMPLE_RATE = 44100;
        int BUFFER_SIZE = 1024 * 4;
        int OVERLAP = 768 * 4;

        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(SAMPLE_RATE, BUFFER_SIZE, OVERLAP);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {

            @Override
            public void handlePitch(PitchDetectionResult res, AudioEvent e) {
                final float pitchInHz = res.getPitch();
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        display((float) pitchInHz, ic);
                    }
                });
            }
        };
        AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, SAMPLE_RATE, BUFFER_SIZE, pdh);
        dispatcher.addAudioProcessor(pitchProcessor);

        audioThread = new Thread(dispatcher, "Audio Thread");
        audioThread.start();
    }

    public void stop(){
        try{

            audioThread = null;
        }
        catch (Exception e){

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void display(float pitchInHz, InputConnection ic) {
        KaBoard((int)pitchInHz, ic);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void KaBoard(int pitchInHz, InputConnection ic) {
        //<EN>
            Map<Integer, Integer> states = new HashMap<>();
            states.put(328, 118);
            states.put(350, 110);
            states.put(370, 120);
            states.put(391, 111);
            states.put(415, 114);
            states.put(440, 112);
            states.put(466, 113);
            states.put(492, -5);
            states.put(523, 48);
            states.put(552, 49);
            states.put(585, 50);
            states.put(620, 51);
            states.put(660, 52);
            states.put(694, 53);
            states.put(737, 54);
            states.put(780, 55);
            states.put(827, 56);
            states.put(877, 57);
            states.put(246, 116);
            states.put(263, 107);
            states.put(277, 119);
            states.put(293, 108);
            states.put(311, 109);
            states.put(195, 104);
            states.put(209, 117);
            states.put(221, 105);
            states.put(233, 106);
            states.put(146, 101);
            states.put(156, 102);
            states.put(165, 121);
            states.put(175, 103);
            states.put(185, 122);
            states.put(109, 98);
            states.put(116, 99);
            states.put(123, 44);
            states.put(131, 100);
            states.put(138, 46);
            states.put(82, 47);
            states.put(87, 32);
            states.put(92, 13);
            states.put(98, 97);
            states.put(104, 115);
            states.put(925, -1);
            //<EN/>
            //<RU>
        Map<Integer, Integer> russes = new HashMap<>();
        russes.put(328, 1089);
        russes.put(350, 1084);
        russes.put(370, 1091);
        russes.put(391, 1085);
        russes.put(415, 1088);
        russes.put(440, 1086);
        russes.put(466, 1087);
        //russes.put(492, 1092);
        russes.put(523, 48);
        russes.put(552, 49);
        russes.put(585, 50);
        russes.put(620, 51);
        russes.put(659, 52);
        russes.put(694, 53);
        russes.put(737, 54);
        russes.put(780, 55);
        russes.put(827, 56);
        russes.put(877, 57);
        russes.put(925, 1081);
        russes.put(982, 1099);
        russes.put(1046, 1096);
        russes.put(1106, 1100);
        russes.put(246, 1090);
        russes.put(263, 1102);
        russes.put(277, 1095);
        russes.put(293, 1082);
        russes.put(311, 1083);
        russes.put(195, 1078);
        russes.put(209, 1101);
        russes.put(221, 1079);
        russes.put(233, 1080);
        russes.put(146, 1076);
        russes.put(156, 1077);
        russes.put(165, 1093);
        russes.put(175, 1103);
        russes.put(185, 1096);
        russes.put(109, 1073);
        russes.put(116, 1074);
        russes.put(123, 44);
        russes.put(131, 1075);
        russes.put(138, 46);
        russes.put(82, 47);
        russes.put(87, 32);
        russes.put(92, 13);
        russes.put(98, 1072);
        russes.put(104, 1094);
            //<RU/>
        if (usingMicro) {
            try {
                if (language == 1) {
                    int kk = states.get(pitchInHz);
                    char code = (char) kk;
                    if (pitchInHz == 492 || pitchInHz == 491 || pitchInHz == 493) {
                        ic.deleteSurroundingText(1, 0);
                    } else {
                        ic.commitText(String.valueOf(code), 1);
                    }
                }
            }catch (Exception E){
                System.out.println("cock");
            }
            try {
            if (language == 2){
                int kk = russes.get(pitchInHz);
                char code = (char) kk;
                if (pitchInHz == 492 || pitchInHz == 491 || pitchInHz == 493) {
                    ic.deleteSurroundingText(1, 0);
                }else{
                    ic.commitText(String.valueOf(code), 1);
                }

            }
            }catch (Exception E){
                System.out.println("cock");
            }
        }
    }
}
