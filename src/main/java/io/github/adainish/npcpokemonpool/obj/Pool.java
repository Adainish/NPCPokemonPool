package io.github.adainish.npcpokemonpool.obj;

import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import io.github.adainish.npcpokemonpool.NPCPokemonPool;

import java.util.ArrayList;
import java.util.List;

public class Pool {
    public String identifier;
    public List <PoolPokemon> pokemonList = new ArrayList <>();

    public Pool(String identifier) {
        this.identifier = identifier;
        this.loadPoolPokemon();
    }

    public void loadPoolPokemon() {
        pokemonList.clear();
        try {
            for (PoolPokemon poke: NPCPokemonPool.poolHandler.poolPokemonList) {
                if (poke == null)
                    continue;
                int weight = poke.weight;
                for (int i = 0; i < weight; i++) {
                    pokemonList.add(poke);
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }


    public void setRandomParty(NPCTrainer trainer) {
        for (int i = 0; i < trainer.getPokemonStorage().getAll().length; i++) {
            trainer.getPokemonStorage().set(i, null);
        }
        List<PoolPokemon> clonedList = new ArrayList <>();
        clonedList.addAll(pokemonList);

        for (int i = 0; i < 6; i++) {
            PoolPokemon poolPokemon = RandomHelper.getRandomElementFromCollection(clonedList);
            trainer.getPokemonStorage().add(poolPokemon.pokemon);
        }
    }
}
