package io.github.adainish.npcpokemonpool.conf;

import info.pixelmon.repack.org.spongepowered.serialize.SerializationException;
import io.github.adainish.npcpokemonpool.NPCPokemonPool;

import java.util.ArrayList;
import java.util.Arrays;

public class PoolConfig extends Configurable{
    private static PoolConfig config;

    public static PoolConfig getConfig() {
        if (config == null) {
            config = new PoolConfig();
        }
        return config;
    }

    public void setup() {
        super.setup();
    }

    public void load() {
        super.load();

    }
    public void save() {super.save();}
    @Override
    public void populate() {
        try {
            this.get().node("Pools", "Example", "PoolPokemon")
                    .set(Arrays.asList("Example"))
                    .comment("The String identifiers for which Pokemon can load into Example");
            this.get().node("PoolPokemon", "Example", "Weight").set(5).comment("The Heavier the weight, the more likely it is to get picked");
            this.get().node("PoolPokemon", "Example", "PokemonName").set("Vulpix");
            this.get().node("PoolPokemon", "Example", "HeldItem").set("pixelmon:leftovers");
            this.get().node("PoolPokemon", "Example", "Level").set(10).comment("The Pokemons level if raise to cap was not enabled");
            this.get().node("PoolPokemon", "Example", "Form").set("").comment("Decide the form for this pokemon, leave blank if none are to be set");
            this.get().node("PoolPokemon", "Example", "NickName").set("").comment("Set the Pokemons Nick Name in Battle!");
            this.get().node("PoolPokemon", "Example", "Shiny").set(false).comment("Is this Pokemon Shiny?");
            this.get().node("PoolPokemon", "Example", "Texture").set("").comment("Apply a pokemon Texture if these are installed");
            this.get().node("PoolPokemon", "Example", "Stats", "Nature").set("Timid").comment("What Nature should be applied to this Pokemon");
            this.get().node("PoolPokemon", "Example", "Stats", "Ability").set("Drought").comment("What Ability should this Pokemon have?");
            this.get().node("PoolPokemon", "Example", "Dynamax").set(1).comment("Set the dynamax level for this Pokemon");
            //EVS
            this.get().node("PoolPokemon", "Example", "Stats", "EVS", "HP").set(252);
            this.get().node("PoolPokemon", "Example", "Stats", "EVS", "ATK").set(252);
            this.get().node("PoolPokemon", "Example", "Stats", "EVS", "SPA").set(252);
            this.get().node("PoolPokemon", "Example", "Stats", "EVS", "DEF").set(252);
            this.get().node("PoolPokemon", "Example", "Stats", "EVS", "SPDEF").set(252);
            this.get().node("PoolPokemon", "Example", "Stats", "EVS", "SPD").set(252);
            //IVS
            this.get().node("PoolPokemon", "Example", "Stats", "IVS", "HP").set(31);
            this.get().node("PoolPokemon", "Example", "Stats", "IVS", "ATK").set(31);
            this.get().node("PoolPokemon", "Example", "Stats", "IVS", "SPA").set(31);
            this.get().node("PoolPokemon", "Example", "Stats", "IVS", "DEF").set(31);
            this.get().node("PoolPokemon", "Example", "Stats", "IVS", "SPDEF").set(31);
            this.get().node("PoolPokemon", "Example", "Stats", "IVS", "SPD").set(31);

            this.get().node("PoolPokemon", "Example", "MoveSet").set(Arrays.asList("Quick Attack", "Hidden Power", "Shadow Ball", "Incinerate"));
            this.get().node("PoolPokemon", "Example", "SpecFlags").set(Arrays.asList("unbreedable", "untradeable"));

        } catch (SerializationException e) {
            NPCPokemonPool.log.error(e);
        }
    }


    @Override
    public String getConfigName() {
        return "config.yaml";
    }
}
