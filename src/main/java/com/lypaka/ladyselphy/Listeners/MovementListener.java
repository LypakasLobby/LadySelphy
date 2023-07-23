package com.lypaka.ladyselphy.Listeners;

import com.lypaka.ladyselphy.ConfigGetters;
import com.lypaka.ladyselphy.EventHandler.SelphyEvent;
import com.lypaka.ladyselphy.EventHandler.SelphyEventHandler;
import com.lypaka.lypakautils.API.PlayerMovementEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MovementListener {

    @SubscribeEvent
    public void onMove (PlayerMovementEvent event) {

        ServerPlayerEntity player = event.getPlayer();
        if (SelphyEventHandler.eventMap.containsKey(player.getUUID())) {

            SelphyEvent selphyEvent = SelphyEventHandler.eventMap.get(player.getUUID());
            if (ConfigGetters.mode == 1) {

                int currentSteps = selphyEvent.getCurrentSteps();
                int updated = currentSteps + event.getStepsTaken();
                selphyEvent.setCurrentSteps(updated);

            }

        }

    }

}
