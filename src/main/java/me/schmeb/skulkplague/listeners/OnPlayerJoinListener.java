package me.schmeb.skulkplague.listeners;

import me.schmeb.skulkplague.Service._ReturnFilesFunction;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public class OnPlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        File file = _ReturnFilesFunction.returnFileOfPlayer.apply(event.getPlayer());
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

        if (!yamlConfiguration.contains("isInfected")) {
            yamlConfiguration.set("Last Cured", ISO_OFFSET_DATE_TIME.format(OffsetDateTime.now()));
            yamlConfiguration.set("isInfected", false);
            yamlConfiguration.set("Stage", 1);
            try {
                yamlConfiguration.save(file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
