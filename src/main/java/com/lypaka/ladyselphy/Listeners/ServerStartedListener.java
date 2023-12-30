package com.lypaka.ladyselphy.Listeners;

import com.lypaka.ladyselphy.ConfigGetters;
import com.lypaka.ladyselphy.LadySelphy;
import com.lypaka.ladyselphy.StorageSaveTask;
import com.lypaka.lypakautils.FancyText;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

@Mod.EventBusSubscriber(modid = LadySelphy.MOD_ID)
public class ServerStartedListener {

    public static boolean isTickListenerEnabled = false;

    @SubscribeEvent
    public static void onServerStarted (FMLServerStartedEvent event) {

        MinecraftForge.EVENT_BUS.register(new InteractionListener());
        Pixelmon.EVENT_BUS.register(new InteractionListener());
        if (ConfigGetters.mode == 1) {

            if (com.lypaka.lypakautils.ConfigGetters.tickListenerEnabled) {

                isTickListenerEnabled = true; // used for reload command
                MinecraftForge.EVENT_BUS.register(new MovementListener());

            } else {

                LadySelphy.logger.warn(FancyText.getFormattedString("&eWARNING: You have Lady Selphy's mode set to 1, but the tick listener in LypakaUtils is not enabled!"));
                LadySelphy.logger.warn(FancyText.getFormattedString("&ePlease adjust your values accordingly and run the reload commands for both Lady Selphy and LypakaUtils!"));

            }

        }
        StorageSaveTask.startTimer();

    }

}
