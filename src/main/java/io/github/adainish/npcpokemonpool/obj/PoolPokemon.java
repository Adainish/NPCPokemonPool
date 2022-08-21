package io.github.adainish.npcpokemonpool.obj;

import com.pixelmonmod.pixelmon.api.battles.attack.AttackRegistry;
import com.pixelmonmod.pixelmon.api.pokemon.Nature;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonFactory;
import com.pixelmonmod.pixelmon.api.pokemon.ability.AbilityRegistry;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.registries.PixelmonItems;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
import info.pixelmon.repack.org.spongepowered.serialize.SerializationException;
import io.github.adainish.npcpokemonpool.NPCPokemonPool;
import io.github.adainish.npcpokemonpool.conf.PoolConfig;
import io.leangen.geantyref.TypeToken;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class PoolPokemon {
    public String identifier = "";
    public int weight = 0;
    public Pokemon pokemon;

    public PoolPokemon(String identifier) {
        this.identifier = identifier;
        this.weight = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Weight").getInt();
        pokemon = generateGymPokemon();
    }


    public Pokemon generateGymPokemon() {
        String pokemonname = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "PokemonName").getString();
        String form = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Form").getString();
        int level = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Level").getInt();
        String nickname = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "NickName").getString();
        boolean shiny = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Shiny").getBoolean();
        String texture = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Texture").getString();
        String nature = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Stats", "Nature").getString();
        String ability = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Stats", "Ability").getString();
        int dynamaxLevel = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Dynamax").getInt();
        String heldItem = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "HeldItem").getString();
        List <String> moves = new ArrayList <>();
        try {
            moves = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "MoveSet").getList(TypeToken.get(String.class));
        } catch (SerializationException e) {
            NPCPokemonPool.log.error(e);
        }

        List<String> specFlags = new ArrayList <>();
        try {
            specFlags = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "SpecFlags").getList(TypeToken.get(String.class));
        } catch (SerializationException e) {
            NPCPokemonPool.log.error(e);
        }
        //Evs
        int evsHP = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Stats", "EVS", "HP").getInt();
        int evsATK = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Stats", "EVS", "ATK").getInt();
        int evsSPA = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Stats", "EVS", "SPA").getInt();
        int evsDEF = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Stats", "EVS", "DEF").getInt();
        int evsSPDEF = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Stats", "EVS", "SPDEF").getInt();
        int evsSPD = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Stats", "EVS", "SPD").getInt();
        //IVS
        int ivsHP = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Stats", "IVS", "HP").getInt();
        int ivsATK = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Stats", "IVS", "ATK").getInt();
        int ivsSPA = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Stats", "IVS", "SPA").getInt();
        int ivsDEF = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Stats", "IVS", "DEF").getInt();
        int ivsSPDEF = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Stats", "IVS", "SPDEF").getInt();
        int ivsSPD = PoolConfig.getConfig().get().node("PoolPokemon", identifier, "Stats", "IVS", "SPD").getInt();

        if (pokemonname == null) {
            NPCPokemonPool.log.info("The PokemonName doesn't exist, please check your config!");
            return null;
        }

        if (!PixelmonSpecies.get(pokemonname).isPresent()) {
            NPCPokemonPool.log.info("The Pokemon Species %mon% doesn't exist!".replaceAll("%mon%", pokemonname));
            return null;
        }

        Pokemon pokemon = PokemonFactory.create(PixelmonSpecies.get(pokemonname).get().orElse(PixelmonSpecies.MAGIKARP)).toPokemon();
        pokemon.setLevel(level);

        pokemon.getIVs().setStat(BattleStatsType.HP, ivsHP);
        pokemon.getIVs().setStat(BattleStatsType.ATTACK, ivsATK);
        pokemon.getIVs().setStat(BattleStatsType.SPECIAL_ATTACK, ivsSPA);
        pokemon.getIVs().setStat(BattleStatsType.DEFENSE, ivsDEF);
        pokemon.getIVs().setStat(BattleStatsType.SPECIAL_DEFENSE, ivsSPDEF);
        pokemon.getIVs().setStat(BattleStatsType.SPEED, ivsSPD);


        pokemon.getEVs().setStat(BattleStatsType.HP, evsHP);
        pokemon.getEVs().setStat(BattleStatsType.ATTACK, evsATK);
        pokemon.getEVs().setStat(BattleStatsType.SPECIAL_ATTACK, evsSPA);
        pokemon.getEVs().setStat(BattleStatsType.DEFENSE, evsDEF);
        pokemon.getEVs().setStat(BattleStatsType.SPECIAL_DEFENSE, evsSPDEF);
        pokemon.getEVs().setStat(BattleStatsType.SPEED, evsSPD);

        pokemon.setGrowth(EnumGrowth.Ordinary);
        if (nickname != null) {
            if (!nickname.isEmpty())
                pokemon.setNickname(nickname);
        }

        if (heldItem != null)
            if (!heldItem.isEmpty()) {
                ResourceLocation location = new ResourceLocation(heldItem);
                Item op = ForgeRegistries.ITEMS.getValue(location);
                if (op == null) {
                    NPCPokemonPool.log.error("The Item for the pokemon held item could not be created, thus the pokemon was given an oran berry");
                    op = PixelmonItems.oran_berry;
                }
                ItemStack itemStack = new ItemStack(op);
                if (!itemStack.isEmpty()) //Ignore, this can still be null due to the String being editable by administrators
                    pokemon.setHeldItem(itemStack);
                else
                    NPCPokemonPool.log.error("The ItemStack couldn't be created for npc pool pokemon %pokemon%".replaceAll("%pokemon%", pokemonname));
            }

        if (form != null && !form.isEmpty())
            if (pokemon.getSpecies().hasForm(form))
                pokemon.setForm(form);
            else pokemon.setForm(pokemon.getSpecies().getDefaultForm());

        pokemon.setShiny(shiny);

        if (texture != null) {
            if (!texture.isEmpty()) {
                // TODO: 17/06/2022
                //  add texture support
            }
        }

        if (Nature.natureFromString(nature) != null)
            pokemon.setNature(Nature.natureFromString(nature));
        else {
            pokemon.setNature(Nature.getRandomNature());
            NPCPokemonPool.log.info("There was an issue generating the nature for %pokemon%, please check your config for any errors".replace("%pokemon%", pokemonname));
        }
        pokemon.getMoveset().clear();
        if (moves != null) {
            for (String s:moves) {
                Attack atk = new Attack(s);
                if (AttackRegistry.getAttackBase(s).isPresent()) {
                    pokemon.getMoveset().add(atk);
                } else {
                    NPCPokemonPool.log.info("The %move% for %pokemon% doesn't exist! skipping move!".replace("%pokemon%", pokemonname).replace("%move%", s));
                }
            }
        } else {
            pokemon.rerollMoveset();
            NPCPokemonPool.log.info("The moves for %pokemon% returned null, generating random movelist".replace("%pokemon%", pokemonname));
        }
        if (ability != null) {
            if (AbilityRegistry.getAbility(ability).isPresent()) {
                pokemon.setAbility(AbilityRegistry.getAbility(ability));
            } else {
                NPCPokemonPool.log.info("There was an issue generating the ability for %pokemon%, %ability% doesn't exist according to pixelmon. Please check your config for any errors".replace("%pokemon%", pokemonname).replace("%ability%", ability));
                pokemon.setAbility(pokemon.getForm().getAbilities().getRandomAbility());
            }
        } else {
            pokemon.setAbility(pokemon.getForm().getAbilities().getRandomAbility());
            NPCPokemonPool.log.info("There was an issue generating the ability for %pokemon% due to a nullpointer being detected in the config, please check your config for any errors".replace("%pokemon%", pokemonname));
        }
        pokemon.setDynamaxLevel(dynamaxLevel);
        pokemon.setDoesLevel(false);
        if (specFlags != null && !specFlags.isEmpty()) {
            for (String s : specFlags) {
                pokemon.addFlag(s);
            }
        }
        return pokemon;
    }
}
