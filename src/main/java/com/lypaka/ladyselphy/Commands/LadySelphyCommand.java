package com.lypaka.ladyselphy.Commands;

import com.lypaka.ladyselphy.LadySelphy;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = LadySelphy.MOD_ID)
public class LadySelphyCommand {

    public static List<String> ALIASES = Arrays.asList("ladyselphy", "selphy", "lselphy");

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new ReloadCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}
