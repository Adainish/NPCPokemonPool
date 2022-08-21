package io.github.adainish.npcpokemonpool.ui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.pixelmonmod.pixelmon.api.registries.PixelmonItems;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import io.github.adainish.npcpokemonpool.NPCPokemonPool;
import io.github.adainish.npcpokemonpool.obj.Pool;
import io.github.adainish.npcpokemonpool.util.Util;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UI {
    public static GooeyButton fillerRed = GooeyButton.builder()
            .display(new ItemStack(Blocks.RED_STAINED_GLASS_PANE, 1))
            .build();

    public static GooeyButton fillerWhite = GooeyButton.builder()
            .display(new ItemStack(Blocks.WHITE_STAINED_GLASS_PANE, 1))
            .build();

    public static List <Button> pools(NPCTrainer trainer) {
        List<Button> buttons = new ArrayList <>();
        for (Pool pool:NPCPokemonPool.poolHandler.poolCache.values()) {
            GooeyButton button = GooeyButton.builder()
                    .title(Util.getFormattedDisplayName("&a" + pool.identifier))
                    .lore(Util.getFormattedList(Arrays.asList("")))
                    .display(new ItemStack(PixelmonItems.sonias_book))
                    .onClick(b -> {
                        trainer.getPersistentData().putString("poolName", pool.identifier);
                        Util.send(b.getPlayer(), "&aThis NPC's pool was set to: " + pool.identifier);
                        pool.setRandomParty(trainer);
                        UIManager.openUIForcefully(b.getPlayer(), editorUI(trainer));
                    })
                    .build();

            buttons.add(button);
        }
        return buttons;
    }

    public static LinkedPage PoolPage(NPCTrainer trainer) {
        PlaceholderButton placeHolderButton = new PlaceholderButton();

        LinkedPageButton previous = LinkedPageButton.builder()
                .display(new ItemStack(PixelmonItems.trade_holder_left))
                .title("Previous Page")
                .linkType(LinkType.Previous)
                .build();

        LinkedPageButton next = LinkedPageButton.builder()
                .display(new ItemStack(PixelmonItems.trade_holder_right))
                .title("Next Page")
                .linkType(LinkType.Next)
                .build();


        Template template;

        if (pools(trainer).size() > 20) {
            template = ChestTemplate.builder(6)
                    .checker(0, 0, 6, 9, fillerRed, fillerWhite)
                    .rectangle(1, 2, 4, 5, placeHolderButton)
                    .set(1, 7, previous)
                    .set(3, 7, next)
                    .build();
        } else {
            template = ChestTemplate.builder(6)
                    .checker(0, 0, 6, 9, fillerRed, fillerWhite)
                    .rectangle(1, 2, 4, 5, placeHolderButton)
                    .build();
        }

        return PaginationHelper.createPagesFromPlaceholders(template, pools(trainer), LinkedPage.builder().title(Util.getFormattedDisplayName("&cPools")).template(template));
    }

    public static GooeyPage editorUI(NPCTrainer trainer) {
        Pool pool = NPCPokemonPool.poolHandler.getPool(trainer);
        ChestTemplate.Builder template = ChestTemplate.builder(3);
        template.checker(0, 0, 3, 9, fillerRed, fillerWhite);
        if (NPCPokemonPool.poolHandler.isPoolNPC(trainer)) {
            GooeyButton setPool = GooeyButton.builder()
                    .display(new ItemStack(PixelmonItems.poison_barb))
                    .title(Util.getFormattedDisplayName("&bSet New Pool"))
                    .lore(Util.getFormattedList(Arrays.asList("&7Click to assign a Pool from the List of available Pools")))
                    .onClick(b -> {
                        UIManager.openUIForcefully(b.getPlayer(), PoolPage(trainer));
                    })
                    .build();

            String poolName = "Undefined";
            if (pool != null)
                poolName = pool.identifier;

            GooeyButton currentPool  = GooeyButton.builder()
                    .display(new ItemStack(PixelmonItems.sonias_book))
                    .title(Util.getFormattedDisplayName("The NPCs current Pool"))
                    .lore(Util.getFormattedList(Arrays.asList("&a: " + poolName)))
                    .build();

            GooeyButton refreshPokemon = GooeyButton.builder()
                    .display(new ItemStack(PixelmonItems.grass_gem))
                    .title(Util.getFormattedDisplayName("&aRefresh Pokemon"))
                    .lore(Util.getFormattedList(Arrays.asList("&7Click to refresh the current Pokemon the NPC has set")))
                    .onClick(b -> {
                        if (pool != null) {
                            pool.setRandomParty(trainer);
                        } else {
                            Util.send(b.getPlayer(), "&aThe pool needs to be set first!");
                        }
                    })
                    .build();
            template.set(1, 2, currentPool);
            template.set(1, 4, setPool);
            template.set(1, 6, refreshPokemon);
        } else {
            GooeyButton createPoolNPC = GooeyButton.builder()
                    .title(Util.getFormattedDisplayName("&aCreate Pool NPC"))
                    .display(new ItemStack(Items.EMERALD))
                    .onClick(b -> {
                        trainer.getPersistentData().putBoolean("poolNPC", true);
                        trainer.getPersistentData().putString("poolName", "");
                        Util.send(b.getPlayer(), "&aCreated a new Pool NPC");
                        UIManager.openUIForcefully(b.getPlayer(), editorUI(trainer));
                    })
                    .build();
            template.set(1, 4, createPoolNPC);
        }
        return GooeyPage.builder().template(template.build()).title(Util.getFormattedDisplayName("&cNPC Editor")).build();
    }
}
