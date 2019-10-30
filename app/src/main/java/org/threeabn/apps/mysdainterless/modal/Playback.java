package org.threeabn.apps.mysdainterless.modal;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class Playback implements Serializable {
    // current program position
    private int position;
    // program display, program path
    private LinkedHashMap<String, String> programRefs;

    private Mode mode;

    public Playback(int position, LinkedHashMap<String, String> programRefs, Mode mode) {
      this.position = position;
      this.programRefs = programRefs;
      this.mode = mode;
    }

    public int getPosition() {
        return position;
    }

    public LinkedHashMap<String, String> getProgramRefs() {
        return programRefs;
    }

    public Mode getMode() {
        return mode;
    }

    public enum Mode {
        PREVIEW, PREVIEW_AUTO_PLAY, FULL_SCREEN
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
