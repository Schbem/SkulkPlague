package me.schmeb.skulkplague;

import me.schmeb.skulkplague.Service.PlagueService;
import me.schmeb.skulkplague.Service._InfectPlayerConsumer;
import me.schmeb.skulkplague.Service._ReturnFilesFunction;
import me.schmeb.skulkplague.commands.Infector;
import me.schmeb.skulkplague.listeners.OnDrinkCureListener;
import me.schmeb.skulkplague.listeners.OnPickUp;
import me.schmeb.skulkplague.listeners.OnPlayerJoinListener;
import me.schmeb.skulkplague.listeners.PlayerKillEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Random;

public final class SkulkPlague extends JavaPlugin {
    @Override
    public void onEnable() {
        PlagueService plagueService = new PlagueService();

        PluginCommand infect = this.getCommand("infect");
        if(infect != null) infect.setExecutor(new Infector());

        getServer().getPluginManager().registerEvents(new PlayerKillEntityEvent(plagueService), this);
        getServer().getPluginManager().registerEvents(new OnPickUp(plagueService), this);
        getServer().getPluginManager().registerEvents(new OnDrinkCureListener(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoinListener(), this);

        Random random = new Random();
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    File file = _ReturnFilesFunction.returnFileOfPlayer.apply(player);
                    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

                    if(!yamlConfiguration.getBoolean("isInfected")) return;

                    int infectionStage = yamlConfiguration.getInt("Stage");
                    double RNG = random.nextDouble();
                    plagueService.calculatePlagueEffects(player, infectionStage, yamlConfiguration, file);

                    for(Player targetPlayer : Bukkit.getOnlinePlayers()) {
                        if(RNG < 0.5 || targetPlayer != player) return;
                        if(player.getWorld() != targetPlayer.getWorld()) return;
                        if(player.getGameMode() == GameMode.SPECTATOR || player.getGameMode() == GameMode.CREATIVE) return;

                        double distance = targetPlayer.getLocation().distance(player.getLocation());
                        if(distance > 10) return;

                        File filePlayer2 = _ReturnFilesFunction.returnFileOfPlayer.apply(targetPlayer);
                        YamlConfiguration yamlConfigurationTargetPlayer = YamlConfiguration.loadConfiguration(filePlayer2);
                        String lastCuredPlayer2 = yamlConfigurationTargetPlayer.getString("Last Cured");

                        if(!yamlConfigurationTargetPlayer.getBoolean("isInfected") && plagueService.returnIsAfter(lastCuredPlayer2)) {
                            _InfectPlayerConsumer.infectPlayer.accept(targetPlayer, yamlConfigurationTargetPlayer);
                        }
                    }
                }
            }
        }.runTaskTimer(this, (random.nextInt(120) + 60) * 20, (random.nextInt(120) + 60) * 20);
    }
}
