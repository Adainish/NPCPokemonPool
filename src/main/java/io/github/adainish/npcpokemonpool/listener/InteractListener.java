package io.github.adainish.npcpokemonpool.listener;

import ca.landonjw.gooeylibs2.api.UIManager;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import io.github.adainish.npcpokemonpool.NPCPokemonPool;
import io.github.adainish.npcpokemonpool.ui.UI;
import io.github.adainish.npcpokemonpool.util.PermissionManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InteractListener {

    @SubscribeEvent
    public void onNPCInteract(PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.isCanceled())
            return;
        if (event.getTarget() instanceof NPCTrainer) {
            NPCTrainer trainer = (NPCTrainer) event.getTarget();
            if (event.getItemStack().hasTag() && event.getItemStack().getTag().getBoolean("poolEditor")) {
                if (PermissionManager.isAdmin((ServerPlayerEntity) event.getPlayer())) {
                    UIManager.openUIForcefully((ServerPlayerEntity) event.getPlayer(), UI.editorUI(trainer));
                } else {
                    NPCPokemonPool.log.warn("Player %name% was caught attempting to use the NPC Pool EditorItem illegally! Please take care of this!");
                }
            }
        }
    }
}
