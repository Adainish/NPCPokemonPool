package io.github.adainish.npcpokemonpool.cmd;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.adainish.npcpokemonpool.NPCPokemonPool;
import io.github.adainish.npcpokemonpool.obj.EditorItem;
import io.github.adainish.npcpokemonpool.util.PermissionManager;
import io.github.adainish.npcpokemonpool.util.PermissionUtil;
import io.github.adainish.npcpokemonpool.util.Util;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;

public class Command {
    public static LiteralArgumentBuilder <CommandSource> getCommand() {
        return Commands.literal("npcpool")
                .requires(cs -> PermissionUtil.checkPermAsPlayer(cs, PermissionManager.adminPerm))
                .executes(context -> {
                    Util.send(context.getSource(), "&ePlease provide a valid argument");
                    return 1;
                })
                .then(Commands.literal("editor")
                        .requires(cs -> PermissionUtil.checkPermAsPlayer(cs, PermissionManager.adminPerm))
                        .executes(context -> {
                            EditorItem editor = new EditorItem();
                            PlayerEntity playerEntity = context.getSource().asPlayer();
                            editor.giveItemStack(playerEntity);
                            return 1;
                        })
                )
                .then(Commands.literal("reload")
                        .requires(cs -> PermissionUtil.checkPermAsPlayer(cs, PermissionManager.adminPerm))
                        .executes(context -> {
                            NPCPokemonPool.instance.reload();
                            Util.send(context.getSource(), "Reloaded files and regenerated Pool Data.");
                            return 1;
                        })
                )
                ;
    }
}
