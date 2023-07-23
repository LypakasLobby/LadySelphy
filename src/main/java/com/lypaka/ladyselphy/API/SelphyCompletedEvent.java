package com.lypaka.ladyselphy.API;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Event;

/**
 * Called when the player completes Lady Selphy's request. Can be used to count how many times the player has completed the challenge for shit like milestones or whatever
 * Can also be used to modify the rare chance value if desired, for shit like donor ranks or whatever
 */
public class SelphyCompletedEvent extends Event {

    private final ServerPlayerEntity player;
    private double rareChance;

    public SelphyCompletedEvent (ServerPlayerEntity player, double rareChance) {

        this.player = player;
        this.rareChance = rareChance;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public double getRareChance() {

        return this.rareChance;

    }

    public void setRareChance (double chance) {

        this.rareChance = chance;

    }

}
