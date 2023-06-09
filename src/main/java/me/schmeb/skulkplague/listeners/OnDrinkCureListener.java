package me.schmeb.skulkplague.listeners;

import me.schmeb.skulkplague.Service._CurePlayerConsumer;
import me.schmeb.skulkplague.Service._ReturnFilesFunction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.io.File;

public class OnDrinkCureListener implements Listener {
    @EventHandler
    public void onDrinkCure(PlayerItemConsumeEvent event){
        if(event.getItem().getType().equals(Material.POTION)) {
            if(!event.getItem().hasItemMeta()) return;
            if(!event.getItem().getItemMeta().hasCustomModelData()) return;

            Player player = event.getPlayer();
            File file = _ReturnFilesFunction.returnFileOfPlayer.apply(player);
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

            if(event.getItem().getItemMeta().getCustomModelData() == 1234567890) {
                if(yamlConfiguration.getInt("Stage") != 3) {
                    _CurePlayerConsumer.curePlayer.accept(player, yamlConfiguration);
                } else {
                    player.sendMessage(Component.text("The cure seems to not have an effect...", NamedTextColor.RED));
                }
            }
            else if(event.getItem().getItemMeta().getCustomModelData() == 123456789) {
                _CurePlayerConsumer.curePlayer.accept(player, yamlConfiguration);
            }
        }
    }
}
