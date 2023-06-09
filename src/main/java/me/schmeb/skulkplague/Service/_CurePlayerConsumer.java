package me.schmeb.skulkplague.Service;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.function.BiConsumer;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public class _CurePlayerConsumer {
    public static BiConsumer<Player, YamlConfiguration> curePlayer = (player, yamlConfiguration) -> {
        player.sendMessage(Component.text("You suddenly feel great!", NamedTextColor.GREEN));
        yamlConfiguration.set("Last Cured", ISO_OFFSET_DATE_TIME.format(OffsetDateTime.now().plus(Duration.ofHours(3))));
        yamlConfiguration.set("isInfected", false);
        yamlConfiguration.set("Stage", 1);

        try {
            yamlConfiguration.save(_ReturnFilesFunction.returnFileOfPlayer.apply(player));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
}
