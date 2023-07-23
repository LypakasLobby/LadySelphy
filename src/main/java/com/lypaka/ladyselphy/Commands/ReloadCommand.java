package com.lypaka.ladyselphy.Commands;

import com.lypaka.ladyselphy.ConfigGetters;
import com.lypaka.ladyselphy.LadySelphy;
import com.lypaka.ladyselphy.Listeners.MovementListener;
import com.lypaka.ladyselphy.Listeners.ServerStartedListener;
import com.lypaka.ladyselphy.StorageSaveTask;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : LadySelphyCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("reload")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "ladyselphy.command.admin")) {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUUID());
                                                        return 1;

                                                    }

                                                }

                                                try {

                                                    LadySelphy.configManager.load();
                                                    ConfigGetters.load();
                                                    if (!ServerStartedListener.isTickListenerEnabled) {

                                                        if (com.lypaka.lypakautils.ConfigGetters.tickListenerEnabled) {

                                                            ServerStartedListener.isTickListenerEnabled = true;
                                                            MinecraftForge.EVENT_BUS.register(new MovementListener());

                                                        }

                                                    }
                                                    StorageSaveTask.startTimer();
                                                    c.getSource().sendSuccess(FancyText.getFormattedText("&aSuccessfully reloaded Lady Selphy!"), true);
                                                    return 0;

                                                } catch (ObjectMappingException e) {

                                                    throw new RuntimeException(e);

                                                }

                                            })
                            )
            );

        }

    }

}
