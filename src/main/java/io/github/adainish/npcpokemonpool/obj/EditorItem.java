package io.github.adainish.npcpokemonpool.obj;

import io.github.adainish.npcpokemonpool.util.TextUtil;
import io.github.adainish.npcpokemonpool.util.Util;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class EditorItem {
    public ItemStack itemStack;
    public String display = "&cPool Editor";
    public List <String> lore = new ArrayList <>();

    public EditorItem() {
        lore.add("&4&m-----&c&lADMIN ITEM&4&m-----");
        lore.add("&7Right Click an NPC to either create a Pool NPC or Edit an Existing one");
        lore.add("&4&m-----&c&lADMIN ITEM&4&m-----");
        itemStack = new ItemStack(Items.STICK);
        itemStack.setDisplayName(new StringTextComponent(Util.getFormattedDisplayName(display)));
        ListNBT nbtLore = new ListNBT();
        for (String line : lore) {
            if (line != null) {
                nbtLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(TextUtil.parseHexCodes(Util.getFormattedDisplayName(line), false))));
            }
        }
        itemStack.getOrCreateChildTag("display").put("Lore", nbtLore);
    }

    public void giveItemStack(PlayerEntity entity) {
        entity.inventory.addItemStackToInventory(itemStack);
    }
}
