package siea.dev.joinmessenger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import siea.dev.joinmessenger.listeners.PlayerConnectionEvents;

public final class JoinMessenger extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerConnectionEvents(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getPlugin(){
        return plugin;
    }
}
