package com.lypaka.ladyselphy.EventHandler;

import com.lypaka.ladyselphy.ConfigGetters;
import com.pixelmonmod.pixelmon.api.pokedex.PlayerPokedex;
import com.pixelmonmod.pixelmon.api.pokedex.PokedexRegistrationStatus;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.time.LocalDateTime;
import java.util.*;

public class SelphyEventHandler {

    public static Map<UUID, SelphyEvent> eventMap = new HashMap<>();

    public static void startEvent (ServerPlayerEntity player) {

        int cooldown = ConfigGetters.cooldown;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cooldownExpires = now.plusSeconds(cooldown);

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

            event = new SelphyEvent(cooldownExpires, value, 0, randomSpecies);

        } else {

            LocalDateTime timerExpires = LocalDateTime.now().plusSeconds(value);
            event = new SelphyEvent(cooldownExpires, timerExpires, randomSpecies);

        }

        eventMap.put(player.getUUID(), event);

    }

}
