package io.github.adainish.npcpokemonpool.wrapper;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import info.pixelmon.repack.org.spongepowered.CommentedConfigurationNode;
import io.github.adainish.npcpokemonpool.conf.PoolConfig;
import io.github.adainish.npcpokemonpool.obj.Pool;
import io.github.adainish.npcpokemonpool.obj.PoolPokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoolHandler {

    public HashMap<String, Pool> poolCache = new HashMap <>();

    public List <PoolPokemon> poolPokemonList = new ArrayList <>();

    public PoolHandler() {

    }

    public void loadPools() {
        poolCache.clear();
        CommentedConfigurationNode node = PoolConfig.getConfig().get().node("Pools");
        Map nodemap = node.childrenMap();
        for (Object obj: nodemap.keySet()) {
            if (obj == null) {
                continue;
            }
            String nodestring = obj.toString();
            Pool pool = new Pool(nodestring);
            poolCache.put(nodestring, pool);
        }
    }

    public void loadPoolPokemon() {
        poolPokemonList.clear();
        CommentedConfigurationNode node = PoolConfig.getConfig().get().node("PoolPokemon");
        Map nodemap = node.childrenMap();
        for (Object obj: nodemap.keySet()) {
            if (obj == null) {
                continue;
            }
            String nodestring = obj.toString();
            PoolPokemon pokemon = new PoolPokemon(nodestring);
            poolPokemonList.add(pokemon);
        }
    }

    public Pool getPool(NPCTrainer trainer) {
        return poolCache.get(trainer.getPersistentData().getString("poolName"));
    }

    public boolean isPoolNPC(NPCTrainer trainer) {
        return trainer.getPersistentData().getBoolean("poolNPC");
    }
}
