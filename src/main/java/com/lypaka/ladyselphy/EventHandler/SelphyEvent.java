package com.lypaka.ladyselphy.EventHandler;

import java.time.LocalDateTime;

public class SelphyEvent {

    private int maxSteps;
    private int currentSteps;
    private LocalDateTime timerExpires;
    private final String speciesRequested;

    public SelphyEvent (int maxSteps, int currentSteps, String speciesRequested) {

        this.maxSteps = maxSteps;
        this.currentSteps = currentSteps;
        this.speciesRequested = speciesRequested;

    }

    public SelphyEvent (LocalDateTime timerExpires, String speciesRequested) {

        this.timerExpires = timerExpires;
        this.speciesRequested = speciesRequested;

    }

    public int getMaxSteps() {

        return this.maxSteps;

    }

    public int getCurrentSteps() {

        return this.currentSteps;

    }

    public void setCurrentSteps (int steps) {

        this.currentSteps = steps;

    }

    public LocalDateTime getTimerExpires() {

        return timerExpires;

    }

    public String getSpeciesRequested() {

        return this.speciesRequested;

    }

}
