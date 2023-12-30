package com.lypaka.ladyselphy.Listeners;

import com.lypaka.ladyselphy.ConfigGetters;
import com.lypaka.ladyselphy.EventHandler.SelphyEvent;
import com.lypaka.ladyselphy.EventHandler.SelphyEventHandler;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import com.pixelmonmod.pixelmon.api.events.npc.NPCEvent;
import com.pixelmonmod.pixelmon.entities.npcs.NPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.time.LocalDateTime;
import java.util.List;

public class InteractionListener {

    // Gotta love I need a different event listener for this because Pixelmon just *refuses* to use Forge's EVENT_BUS
    @SubscribeEvent
    public void onPixelmonNPCInteract (NPCEvent.Interact event) {

        ServerPlayerEntity player = (ServerPlayerEntity) event.player;
        NPCEntity entity = event.npc;
        String world = WorldMap.getWorldName(player);
        int x = (int) entity.position().x;
        int y = (int) entity.position().y;
        int z = (int) entity.position().z;
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

    @SubscribeEvent
    public void onInteract (PlayerInteractEvent.EntityInteract event) {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        Entity entity = event.getTarget();
        String world = WorldMap.getWorldName(player);
        int x = (int) entity.position().x;
        int y = (int) entity.position().y;
        int z = (int) entity.position().z;
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
