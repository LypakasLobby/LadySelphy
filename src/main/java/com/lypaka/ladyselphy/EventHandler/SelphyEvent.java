package com.lypaka.ladyselphy.EventHandler;

import java.time.LocalDateTime;

public class SelphyEvent {

    private final LocalDateTime cooldownExpires;
    private int maxSteps;
    private int currentSteps;
    private LocalDateTime timerExpires;
    private final String speciesRequested;

    public SelphyEvent (LocalDateTime cooldownExpires, int maxSteps, int currentSteps, String speciesRequested) {

        this.cooldownExpires = cooldownExpires;
        this.maxSteps = maxSteps;
        this.currentSteps = currentSteps;
        this.speciesRequested = speciesRequested;

    }

    public SelphyEvent (LocalDateTime cooldownExpires, LocalDateTime timerExpires, String speciesRequested) {

        this.cooldownExpires = cooldownExpires;
        this.timerExpires = timerExpires;
        this.speciesRequested = speciesRequested;

    }

    public LocalDateTime getCooldownExpires() {

        return this.cooldownExpires;

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
