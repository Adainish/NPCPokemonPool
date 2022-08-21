package io.github.adainish.npcpokemonpool;

import com.pixelmonmod.pixelmon.Pixelmon;
import io.github.adainish.npcpokemonpool.cmd.Command;
import io.github.adainish.npcpokemonpool.conf.PoolConfig;
import io.github.adainish.npcpokemonpool.listener.EndBattleListener;
import io.github.adainish.npcpokemonpool.listener.InteractListener;
import io.github.adainish.npcpokemonpool.util.PermissionManager;
import io.github.adainish.npcpokemonpool.wrapper.PoolHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod("npcpokemonpool")
public class NPCPokemonPool {
    public static final String MOD_NAME = "NPCPokemonPool";
    public static final String VERSION = "1.0.0-Snapshot";
    public static final String AUTHORS = "Winglet";
    public static final String YEAR = "2022";
    public static NPCPokemonPool instance;
    private static File configDir;
    public static final Logger log = LogManager.getLogger(MOD_NAME);
    public static PoolHandler poolHandler;
    public NPCPokemonPool() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
    }

    public static File getConfigDir() {
        return configDir;
    }

    public static void setConfigDir(File configDir) {
        NPCPokemonPool.configDir = configDir;
    }

    private void setup(final FMLCommonSetupEvent event) {
        log.info("Booting up %n by %authors %v %y"
                .replace("%n", MOD_NAME)
                .replace("%authors", AUTHORS)
                .replace("%v", VERSION)
                .replace("%y", YEAR)
        );
        initDirs();
    }

    @SubscribeEvent
    public void onCommandRegistry(RegisterCommandsEvent event) {
        PermissionManager.registerBasePermissions();
        event.getDispatcher().register(Command.getCommand());
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartedEvent event) {
        Pixelmon.EVENT_BUS.register(new EndBattleListener());
        MinecraftForge.EVENT_BUS.register(new InteractListener());
        initConfig();
        loadConfig();
        poolHandler = new PoolHandler();
    }

    public void initDirs() {
        setConfigDir(new File(FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()).toString()));
        getConfigDir().mkdir();
    }

    public void initConfig() {
        PoolConfig.getConfig().setup();
    }


    public void loadConfig() {
        PoolConfig.getConfig().load();
    }

    public void reload() {
        initConfig();
        loadConfig();
        poolHandler = new PoolHandler();
    }

}
