package io.github.adainish.npcpokemonpool.listener;

import com.pixelmonmod.pixelmon.api.events.BeatTrainerEvent;
import com.pixelmonmod.pixelmon.api.events.LostToTrainerEvent;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import io.github.adainish.npcpokemonpool.NPCPokemonPool;
import io.github.adainish.npcpokemonpool.obj.Pool;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EndBattleListener {

    @SubscribeEvent
    public void onPlayerWonEvent(BeatTrainerEvent event) {
        NPCTrainer trainer = event.trainer;
        if (NPCPokemonPool.poolHandler.isPoolNPC(trainer)) {
            String npcPool = trainer.getPersistentData().getString("assignedPool");
            Pool pool = NPCPokemonPool.poolHandler.poolCache.get(npcPool);
            if (pool == null)
                return;
            pool.setRandomParty(trainer);
        }
    }

    @SubscribeEvent
    public void onPlayerLostEvent(LostToTrainerEvent event) {
        NPCTrainer trainer = event.trainer;
        if (NPCPokemonPool.poolHandler.isPoolNPC(trainer)) {
            String npcPool = trainer.getPersistentData().getString("assignedPool");
            Pool pool = NPCPokemonPool.poolHandler.poolCache.get(npcPool);
            if (pool == null)
                return;
            pool.setRandomParty(trainer);
        }
    }


}
