package io.github.adainish.npcpokemonpool.util;

import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static void send(ServerPlayerEntity player, String message) {
        if (player == null)
            return;
        player.sendMessage(new StringTextComponent((message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")), player.getUniqueID());
    }

    public static void send(CommandSource sender, String message) {
        sender.sendFeedback(new StringTextComponent((message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")), false);
    }

    private static GooeyButton filler() {
        return GooeyButton.builder()
                .display(new ItemStack(Blocks.GRAY_STAINED_GLASS_PANE, 1))
                .title("")
                .build();
    }


    public static String getFormattedDisplayName(String s) {
        return s.replaceAll("&", "§");
    }

    public static List <String> getFormattedList(List<String> lore) {
        List<String> formattedInfo = new ArrayList <>();
        for (String s: lore) {
            formattedInfo.add(s.replaceAll("&", "§"));
        }
        return formattedInfo;
    }
}
