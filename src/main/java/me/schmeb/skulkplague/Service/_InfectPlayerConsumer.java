package me.schmeb.skulkplague.Service;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.function.BiConsumer;

public class _InfectPlayerConsumer {
    public static BiConsumer<Player, YamlConfiguration> infectPlayer = (player, yamlConfiguration) -> {
        player.sendMessage(Component.text("You suddenly feel ill...", NamedTextColor.RED));
        yamlConfiguration.set("isInfected", true);

        try {
            yamlConfiguration.save(_ReturnFilesFunction.returnFileOfPlayer.apply(player));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
}
