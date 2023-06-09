package me.schmeb.skulkplague.Service;

import org.bukkit.entity.Player;

import java.io.File;
import java.util.function.Function;

public class _ReturnFilesFunction {
    public static Function<Player, File> returnFileOfPlayer = player ->
            new File("plugins" + File.separator + "PlayerCardsFiles" + File.separator + "users" + File.separator + player.getUniqueId() + ".yml");
}
