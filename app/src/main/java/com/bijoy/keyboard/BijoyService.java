package com.bijoy.keyboard;

import android.inputmethodservice.InputMethodService;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;

public class BijoyService extends InputMethodService {
    private boolean isGPressed = false;
    private String pendingVowel = "";

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return super.onKeyDown(keyCode, event);
        boolean isShift = event.isShiftPressed();

        if (keyCode == KeyEvent.KEYCODE_R) {
            handleInput(ic, isShift ? "ফ" : "প");
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_Z) {
            ic.beginBatchEdit();
            ic.commitText("্", 1);
            ic.commitText(isShift ? "য" : "র", 1);
            checkPending(ic);
            ic.endBatchEdit();
            isGPressed = false;
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_G) {
            if (isShift) ic.commitText("।", 1); else isGPressed = true;
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_D) {
            pendingVowel = "ি";
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_J) {
            handleInput(ic, isShift ? "খ" : "ক");
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_K) {
            handleInput(ic, isShift ? "থ" : "ত");
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void handleInput(InputConnection ic, String text) {
        ic.beginBatchEdit();
        if (isGPressed) ic.commitText("্", 1);
        ic.commitText(text, 1);
        checkPending(ic);
        ic.endBatchEdit();
        isGPressed = false;
    }

    private void checkPending(InputConnection ic) {
        if (!pendingVowel.isEmpty()) {
            ic.commitText(pendingVowel, 1);
            pendingVowel = "";
        }
    }
}
