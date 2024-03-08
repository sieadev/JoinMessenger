package siea.dev.joinmessenger.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import siea.dev.joinmessenger.JoinMessenger;

public class PlayerConnectionEvents implements Listener {
    private final String joinMessageTemplate;
    private final String firstJoinMessageTemplate;
    private final String quitMessageTemplate;

    public PlayerConnectionEvents(){
        Plugin plugin = JoinMessenger.getPlugin();
        FileConfiguration config = plugin.getConfig();

        joinMessageTemplate = config.getString("joinMessage").replace("&","§");
        firstJoinMessageTemplate = config.getString("firstJoinMessage").replace("&","§");
        quitMessageTemplate = config.getString("quitMessage").replace("&","§");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (player.hasPermission("joinMessenger.hideJoin")){
            e.setJoinMessage(null);
        }
        else if (player.hasPlayedBefore()){
            e.setJoinMessage(replacePlaceholdersJoin(joinMessageTemplate, player));
        }
        else{
            e.setJoinMessage(replacePlaceholdersJoin(firstJoinMessageTemplate, player));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        if (player.hasPermission("joinMessenger.hideJoin")){
            e.setQuitMessage(null);
        }
        else{
            e.setQuitMessage(replacePlaceholdersLeave(quitMessageTemplate, player));
        }

    }

    private String replacePlaceholdersJoin(String message, Player player){
        if (message == null || player == null) return null;
        message = message.replace("%player%", player.getDisplayName());
        message = message.replace("%playercount%", String.valueOf(player.getServer().getOnlinePlayers().size()));
        message = message.replace("%maxplayers%", String.valueOf(player.getServer().getMaxPlayers()));
        return message;
    }

    private String replacePlaceholdersLeave(String message, Player player){
        if (message == null || player == null) return null;
        message = message.replace("%player%", player.getDisplayName());
        message = message.replace("%playercount%", String.valueOf(player.getServer().getOnlinePlayers().size() - 1));
        message = message.replace("%maxplayers%", String.valueOf(player.getServer().getMaxPlayers()));
        return message;
    }
}
