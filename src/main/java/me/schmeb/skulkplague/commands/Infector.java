package me.schmeb.skulkplague.commands;

import me.schmeb.skulkplague.Service._InfectPlayerConsumer;
import me.schmeb.skulkplague.Service._ReturnFilesFunction;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Infector implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(command.getName().equals("infect") && commandSender.hasPermission("infector")) {
            if(Bukkit.getPlayerExact(strings[0]) != null) {
                Player target = Bukkit.getPlayerExact(strings[0]);

                if(target == null) return false;
                File file = _ReturnFilesFunction.returnFileOfPlayer.apply(target);
                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

                if(!yamlConfiguration.getBoolean("isInfected") && !yamlConfiguration.getBoolean("isCured")) {
                    _InfectPlayerConsumer.infectPlayer.accept(target, yamlConfiguration);
                    commandSender.sendMessage("Successfully infected " + target.getName());
                }
            }
        }
        return true;
    }
}

