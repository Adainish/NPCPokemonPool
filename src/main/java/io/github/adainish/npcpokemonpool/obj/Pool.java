package io.github.adainish.npcpokemonpool.obj;

import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import info.pixelmon.repack.org.spongepowered.serialize.SerializationException;
import io.github.adainish.npcpokemonpool.NPCPokemonPool;
import io.github.adainish.npcpokemonpool.conf.PoolConfig;
import io.leangen.geantyref.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class Pool {
    public String identifier;
    public List <String> pokemonIDs = new ArrayList <>();
    public List <PoolPokemon> pokemonList = new ArrayList <>();

    public Pool(String identifier) {
        this.identifier = identifier;
        this.loadPoolPokemon();
    }

    public void loadPoolPokemon() {
        pokemonList.clear();
        pokemonIDs.clear();
        try {
            pokemonIDs.addAll(PoolConfig.getConfig().get().node("Pools", identifier, "PoolPokemon").getList(TypeToken.get(String.class)));
        } catch (SerializationException e) {
            e.printStackTrace();
        }
        try {
            for (PoolPokemon poke: NPCPokemonPool.poolHandler.poolPokemonList) {
                if (poke == null)
                    continue;
                if (pokemonIDs.contains(poke.identifier)) {
                    int weight = poke.weight;
                    for (int i = 0; i < weight; i++) {
                        pokemonList.add(poke);
                    }
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }


    public void setRandomParty(NPCTrainer trainer) {
        trainer.getPokemonStorage().heal();
        for (int i = 0; i < trainer.getPokemonStorage().getAll().length; i++) {
            trainer.getPokemonStorage().set(i, null);
        }
        List <PoolPokemon> clonedList = new ArrayList <>(pokemonList);
        for (int i = 1; i < 7; i++) {
            if (i >= clonedList.size()) {
                NPCPokemonPool.log.warn("Not enough pokemon existed in pool %id% to assign a full team of 6".replace("%id%", identifier));
                break;
            }
            PoolPokemon poolPokemon = RandomHelper.getRandomElementFromCollection(clonedList);
            clonedList.removeIf(pm -> pm.identifier.equals(poolPokemon.identifier));
            if (poolPokemon != null) {
                trainer.getPokemonStorage().add(poolPokemon.pokemon);
            }
        }
    }
}
