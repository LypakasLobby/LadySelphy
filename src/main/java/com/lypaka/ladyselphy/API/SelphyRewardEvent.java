package com.lypaka.ladyselphy.API;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * Called when the server goes to reward the player for completing Selphy's request BEFORE the reward is given
 * Can be used to modify the rewards
 * Can be canceled to run an entirely custom reward (like an entire list of commands or whatever)
 */
@Cancelable
public class SelphyRewardEvent extends Event {

    private final ServerPlayerEntity player;
    private String command;
    private ItemStack item;

    public SelphyRewardEvent (ServerPlayerEntity player, String command) {

        this.player = player;
        this.command = command;

    }

    public SelphyRewardEvent (ServerPlayerEntity player, ItemStack item) {

        this.player = player;
        this.item = item;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public String getCommand() {

        return this.command;

    }

    public void setCommand (String command) {

        this.command = command;

    }

    public ItemStack getItem() {

        return this.item;

    }

    public void setItem (ItemStack item) {

        this.item = item;

    }

}
