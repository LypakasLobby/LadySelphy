package com.lypaka.ladyselphy.Listeners;

import com.pixelmonmod.pixelmon.entities.npcs.NPCChatting;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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

        }

    }

}
