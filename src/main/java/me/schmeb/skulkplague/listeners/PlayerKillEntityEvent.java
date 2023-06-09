package me.schmeb.skulkplague.listeners;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import me.schmeb.skulkplague.Service.PlagueService;
import me.schmeb.skulkplague.Service._InfectPlayerConsumer;
import me.schmeb.skulkplague.Service._ReturnFilesFunction;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.io.File;

public class PlayerKillEntityEvent implements Listener {
    PlagueService plagueService;

    public PlayerKillEntityEvent(PlagueService plagueService){
        this.plagueService = plagueService;
    }

    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        if(!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getDamager();
        Player targetPlayer = (Player) event.getEntity();

        if(NBTEditor.contains(player.getInventory().getItemInMainHand(), "MatriarchsWillSword")) {
            player.playSound(player, Sound.ENTITY_WARDEN_HEARTBEAT, 0.5F, 0.5F);

            File file = _ReturnFilesFunction.returnFileOfPlayer.apply(targetPlayer);
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            String lastCured = yamlConfiguration.getString("Last Cured");

            if(!yamlConfiguration.getBoolean("isInfected") && plagueService.returnIsAfter(lastCured)) {
                _InfectPlayerConsumer.infectPlayer.accept(targetPlayer, yamlConfiguration);
            }
        }
    }
}
