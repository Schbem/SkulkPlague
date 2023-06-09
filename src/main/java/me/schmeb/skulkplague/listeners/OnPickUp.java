package me.schmeb.skulkplague.listeners;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import me.schmeb.skulkplague.Service.PlagueService;
import me.schmeb.skulkplague.Service._InfectPlayerConsumer;
import me.schmeb.skulkplague.Service._ReturnFilesFunction;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import java.io.File;

public class OnPickUp implements Listener {
    PlagueService plagueService;

    public OnPickUp(PlagueService plagueService){
        this.plagueService = plagueService;
    }

    @EventHandler
    public void onPickUp(EntityPickupItemEvent event){
        if(!(event.getEntity() instanceof Player)) return;

        if(NBTEditor.contains(event.getItem().getItemStack(), "MatriarchsWillSword")) {
            Player targetPlayer = (Player) event.getEntity();

            File file = _ReturnFilesFunction.returnFileOfPlayer.apply(targetPlayer);
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            String lastCured = yamlConfiguration.getString("Last Cured");

            if(!yamlConfiguration.getBoolean("isInfected") && plagueService.returnIsAfter(lastCured)) {
                _InfectPlayerConsumer.infectPlayer.accept(targetPlayer, yamlConfiguration);
            }
        }
    }
}
