package com.lypaka.ladyselphy.EventHandler;

import com.lypaka.ladyselphy.API.SelphyCompletedEvent;
import com.lypaka.ladyselphy.API.SelphyRewardEvent;
import com.lypaka.ladyselphy.ConfigGetters;
import com.lypaka.ladyselphy.LadySelphy;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import com.pixelmonmod.pixelmon.api.pokedex.PlayerPokedex;
import com.pixelmonmod.pixelmon.api.pokedex.PokedexRegistrationStatus;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import java.time.LocalDateTime;
import java.util.*;

public class SelphyEventHandler {

    public static Map<UUID, SelphyEvent> eventMap = new HashMap<>();

    public static void startEvent (ServerPlayerEntity player) {

        int cooldown = ConfigGetters.cooldown;
        LocalDateTime now = LocalDateTime.now();

        // TODO MOVE THIS TO GET SET WHEN THE EVENT ACTUALLY STARTS IF NEEDED
        LocalDateTime cooldownExpires = now.plusSeconds(cooldown);
        ConfigGetters.cooldownStorageMap.put(player.getUUID().toString(), cooldownExpires.toString());

        PlayerPartyStorage storage = StorageProxy.getParty(player);
        PlayerPokedex dex = storage.playerPokedex;
        List<String> species = new ArrayList<>();
        for (Map.Entry<Integer, PokedexRegistrationStatus> entry : dex.getSeenMap().entrySet()) {

            if (entry.getValue() == PokedexRegistrationStatus.SEEN) {

                Species pokemon = PixelmonSpecies.fromDex(entry.getKey()).get();
                if (!species.contains(pokemon.getName())) {

                    species.add(pokemon.getName());

                }

            }

        }
        String randomSpecies = RandomHelper.getRandomElementFromList(species);
        SelphyEvent event;
        int value = ConfigGetters.modeValue;

        if (ConfigGetters.mode == 1) {

            event = new SelphyEvent(value, 0, randomSpecies);

        } else {

            LocalDateTime timerExpires = LocalDateTime.now().plusSeconds(value);
            event = new SelphyEvent(timerExpires, randomSpecies);

        }

        eventMap.put(player.getUUID(), event);
        List<Dialogue> dialogue = getDialogue("Upon-Arrival", randomSpecies);
        Dialogue.setPlayerDialogueData(player, dialogue, true);

    }

    // Player failed
    public static void removeEvent (ServerPlayerEntity player) {

        List<Dialogue> messages = SelphyEventHandler.getDialogue("Failed", "");
        Dialogue.setPlayerDialogueData(player, messages, true);
        eventMap.entrySet().removeIf(e -> e.getKey().toString().equalsIgnoreCase(player.getUUID().toString()));

    }

    // Player succeeded
    public static void finishEvent (ServerPlayerEntity player, String species) {

        List<Dialogue> messages = SelphyEventHandler.getDialogue("Completed", species);
        Dialogue.setPlayerDialogueData(player, messages, true);

        SelphyCompletedEvent completedEvent = new SelphyCompletedEvent(player, ConfigGetters.rareChance);
        MinecraftForge.EVENT_BUS.post(completedEvent);

        // This janky shit is to help prevent a case of "no reward" due to an empty or missing list from the config
        String mode = RandomHelper.getRandomChance(completedEvent.getRareChance()) ? "Rare" : "Common";
        List<String> valueOptions = new ArrayList<>();
        if (!LadySelphy.configManager.getConfigNode(0, "Prize-Pool", mode, "Commands").isVirtual()) {

            valueOptions.add("Commands");

        }
        if (!LadySelphy.configManager.getConfigNode(0, "Prize-Pool", mode, "Items").isVirtual()) {

            valueOptions.add("Items");

        }
        String value = RandomHelper.getRandomElementFromList(valueOptions);
        List<String> prizeList;

        if (mode.equals("Common")) {

            if (value.equals("Commands")) {

                prizeList = ConfigGetters.commonItems;

            } else {

                prizeList = ConfigGetters.commonCommands;

            }

        } else {

            if (value.equals("Commands")) {

                prizeList = ConfigGetters.rareItems;

            } else {

                prizeList = ConfigGetters.rareCommands;

            }

        }

        SelphyRewardEvent rewardEvent;
        if (value.equals("Items")) {

            String id = RandomHelper.getRandomElementFromList(prizeList);
            ItemStack item = ItemStackBuilder.buildFromStringID(id);
            item.setCount(1);
            rewardEvent = new SelphyRewardEvent(player, item);

        } else {

            rewardEvent = new SelphyRewardEvent(player, RandomHelper.getRandomElementFromList(prizeList));

        }

        MinecraftForge.EVENT_BUS.post(rewardEvent);
        if (!rewardEvent.isCanceled()) {

            if (value.equals("Items")) {

                player.addItem(rewardEvent.getItem());

            } else {

                player.getServer().getCommands().performCommand(player.getServer().createCommandSourceStack(), rewardEvent.getCommand().replace("%player%", player.getName().getString()));

            }

        }

    }

    public static boolean hasPokemon (ServerPlayerEntity player) {

        SelphyEvent event = eventMap.get(player.getUUID());
        String speciesRequested = event.getSpeciesRequested();

        PlayerPartyStorage storage = StorageProxy.getParty(player);
        boolean hasPokemon = false; // doing it this way in case the player has multiple of the same species on their team, to prevent false positives and negatives
        for (Pokemon pokemon : storage.getTeam()) {

            if (pokemon.getSpecies().getName().equalsIgnoreCase(speciesRequested)) {

                if (ConfigGetters.mustBeOT) {

                    if (pokemon.getOriginalTrainerUUID().toString().equalsIgnoreCase(player.getUUID().toString())) {

                        hasPokemon = true;
                        break;

                    }

                }

                hasPokemon = true;
                break;

            }

        }

        return hasPokemon;

    }

    public static List<Dialogue> getDialogue (String instance, String species) {

        List<String> messages = ConfigGetters.dialogueMap.get(instance);
        List<Dialogue> dialogues = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {

            Dialogue.DialogueBuilder builder = Dialogue.builder();
            builder.setName(FancyText.getFormattedString(ConfigGetters.dialogueTitle));
            String message = messages.get(i).replace("%pokemon%", species);
            builder.setText(FancyText.getFormattedString(message));
            dialogues.add(builder.build());

        }

        return dialogues;

    }

}
