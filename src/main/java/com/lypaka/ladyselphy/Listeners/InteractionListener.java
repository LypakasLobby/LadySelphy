package com.lypaka.ladyselphy.Listeners;

import com.lypaka.ladyselphy.ConfigGetters;
import com.lypaka.ladyselphy.EventHandler.SelphyEvent;
import com.lypaka.ladyselphy.EventHandler.SelphyEventHandler;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import com.pixelmonmod.pixelmon.entities.npcs.NPCChatting;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.time.LocalDateTime;
import java.util.List;

public class InteractionListener {

    @SubscribeEvent
    public void onInteract (PlayerInteractEvent.EntityInteract event) {

        if (event.getTarget() instanceof NPCChatting) {

            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            NPCChatting npc = (NPCChatting) event.getTarget();
            String world = ((ServerWorldInfo) player.getLevel().getLevelData()).getLevelName();
            int x = (int) npc.position().x;
            int y = (int) npc.position().y;
            int z = (int) npc.position().z;
            String location = world + "," + x + "," + y + "," + z;

            if (ConfigGetters.locations.contains(location)) {

                event.setCanceled(true);
                LocalDateTime now = LocalDateTime.now();
                if (!SelphyEventHandler.eventMap.containsKey(player.getUUID())) {

                    SelphyEventHandler.startEvent(player);

                } else {

                    // Checking if on cooldown first
                    if (ConfigGetters.cooldownStorageMap.containsKey(player.getUUID().toString())) {

                        LocalDateTime expires = LocalDateTime.parse(ConfigGetters.cooldownStorageMap.get(player.getUUID().toString()));
                        if (expires.isBefore(now)) {

                            List<Dialogue> messages = SelphyEventHandler.getDialogue("Cooldown", "");
                            Dialogue.setPlayerDialogueData(player, messages, true);
                            return;

                        }

                    }
                    SelphyEvent selphyEvent = SelphyEventHandler.eventMap.get(player.getUUID());
                    if (ConfigGetters.mode == 1) {

                        int maxSteps = selphyEvent.getMaxSteps();
                        int currentSteps = selphyEvent.getCurrentSteps();
                        if (currentSteps > maxSteps) {

                            SelphyEventHandler.removeEvent(player);

                        } else {

                            if (!SelphyEventHandler.hasPokemon(player)) {

                                List<Dialogue> messages = SelphyEventHandler.getDialogue("Reminder", selphyEvent.getSpeciesRequested());
                                Dialogue.setPlayerDialogueData(player, messages, true);

                            } else {

                                SelphyEventHandler.finishEvent(player, selphyEvent.getSpeciesRequested());

                            }

                        }

                    } else {

                        LocalDateTime timerExpires = selphyEvent.getTimerExpires();
                        if (now.isAfter(timerExpires)) {

                            SelphyEventHandler.removeEvent(player);

                        } else {

                            if (!SelphyEventHandler.hasPokemon(player)) {

                                List<Dialogue> messages = SelphyEventHandler.getDialogue("Reminder", selphyEvent.getSpeciesRequested());
                                Dialogue.setPlayerDialogueData(player, messages, true);

                            } else {

                                SelphyEventHandler.finishEvent(player, selphyEvent.getSpeciesRequested());

                            }

                        }

                    }

                }

            }

        }

    }

}
