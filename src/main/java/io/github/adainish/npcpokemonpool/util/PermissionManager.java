package io.github.adainish.npcpokemonpool.util;

import io.github.adainish.npcpokemonpool.NPCPokemonPool;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

public class PermissionManager {

    public static String adminPerm = "npcpool.admin";

    public static void registerBasePermissions() {
        registerCommandPermission(adminPerm);
    }

    public static void registerCommandPermission(String s) {
        if (s == null || s.isEmpty()) {
            NPCPokemonPool.log.error("Trying to register a permission node failed, please check any configs for null/empty Configs");
            return;
        }
        PermissionAPI.registerNode(s, DefaultPermissionLevel.NONE, s);
    }

    public static boolean isAdmin(ServerPlayerEntity playerEntity) {
        return PermissionUtil.checkPerm(playerEntity, adminPerm);
    }
}
