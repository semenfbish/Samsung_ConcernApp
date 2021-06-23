package com.example.concern;


import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;

public class IME extends InputMethodService implements KeyboardView.OnKeyboardActionListener{

    private KeyboardView kv;
    private Keyboard keyboard;
    private final int MicroKey = 100012;
    private final int language = 1;
    int c = 1;
    AudioRecorder audioRecorder;

    private boolean caps = false;

    @Override
    public void onPress(int primaryCode) {
    }
    @Override
    public void onRelease(int primaryCode) {
    }
    @Override
    public void onKey(int primaryKey, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();

       //playClick(primaryCode);
        switch(primaryKey) {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case  MicroKey:
                if (!audioRecorder.usingMicro&&c==1){
                    audioRecorder.usingMicro = true;
                    Toast.makeText(getApplicationContext(),"Lat's Rock!",Toast.LENGTH_SHORT).show();
                    audioRecorder.start(ic);
                    c = 2;
                }else{
                    //audioRecorder.usingMicro = false;
                    Toast.makeText(getApplicationContext(),"No More Rock!",Toast.LENGTH_SHORT).show();

                    audioRecorder.stop();
                }
                if(c==2&&!audioRecorder.usingMicro) {
                    audioRecorder.usingMicro = true;
                    Toast.makeText(getApplicationContext(),"Lat's Rock!",Toast.LENGTH_SHORT).show();
                }
            case language:
                if (audioRecorder.language == 1){
                    audioRecorder.language = 2;
                    Toast.makeText(getApplicationContext(),"Ru",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (audioRecorder.language == 2){
                    audioRecorder.language = 1;
                    Toast.makeText(getApplicationContext(),"En",Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
            default:
                char code = (char) primaryKey;
                if (Character.isLetter(code) && caps) {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
        }
    }
    @Override
    public void onText(CharSequence text) {
    }
    @Override
    public void swipeLeft() {
    }
    @Override
    public void swipeRight() {
    }
    @Override
    public void swipeDown() {
    }
    @Override
    public void swipeUp() {
    }
    @Override
    public View onCreateInputView() {
        audioRecorder = new AudioRecorder();
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.layout.qwerty);
        kv.setKeyboard(keyboard);

        kv.setOnKeyboardActionListener(this);
        return kv;
    }

}
