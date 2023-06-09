package me.schmeb.skulkplague.Service;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Random;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public class PlagueService {
    public void calculatePlagueEffects(Player player, int infectionStage, YamlConfiguration yamlConfiguration, File file) {
        Random random = new Random();
        switch (infectionStage){
            case 1: stage1(player, random); break;
            case 2: stage2(player, random); break;
            case 3: stage3(player, random); break;
        }

        int stage = yamlConfiguration.getInt("Stage");
        double RNG = random.nextDouble();
        if(RNG < 0.01 && stage < 3){
            player.sendMessage(Component.text("You feel your symptoms worsening...", NamedTextColor.RED));
            yamlConfiguration.set("Stage", stage + 1);
            try {
                yamlConfiguration.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void stage1(Player player, Random random){
        double RNG = random.nextDouble();
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, (random.nextInt(30) + 30) * 20, 0));
        if(RNG < 0.25)
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, (random.nextInt(15) + 15) * 20, 3));
        else if(RNG > 0.25 && RNG < 0.5)
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (random.nextInt(15) + 15) * 20, 2));
        else
            player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, (random.nextInt(15) + 15) * 20, 0));
    }

    private void stage2(Player player, Random random){
        double RNG = random.nextDouble();
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, (random.nextInt(45) + 45) * 20, 0));
        player.playSound(player, Sound.ENTITY_WARDEN_HEARTBEAT, 0.5f, 0.5f);
        if(RNG < 0.25){
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, (random.nextInt(15) + 15) * 20, 3));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (random.nextInt(15) + 15) * 20, 2));
        }
        else if(RNG > 0.25 && RNG < 0.5){
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (random.nextInt(5) + 5) * 20, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (random.nextInt(15) + 15) * 20, 2));
        }
        else{
            player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, (random.nextInt(15) + 15) * 20, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (random.nextInt(15) + 15) * 20, 2));
        }
    }

    private void stage3(Player player, Random random){
        double RNG = random.nextDouble();
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, (random.nextInt(60) + 60) * 20, 0));
        player.playSound(player, Sound.ENTITY_WARDEN_HEARTBEAT, 1f, 1f);
        if(RNG < 0.25){
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, (random.nextInt(10) + 10) * 20, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, (random.nextInt(15) + 15) * 20, 3));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (random.nextInt(20) + 20) * 20, 2));
        }
        else if(RNG > 0.25 && RNG < 0.5){
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (random.nextInt(10) + 5) * 20, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, (random.nextInt(10) + 10) * 20, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (random.nextInt(20) + 20) * 20, 2));
        }
        else if(RNG > 0.5 && RNG < 0.75){
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (random.nextInt(10) + 10) * 20, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, (random.nextInt(10) + 10) * 20, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (random.nextInt(20) + 20) * 20, 2));
        }else{
            player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (random.nextInt(5) + 5) * 20, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, (random.nextInt(10) + 10) * 20, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (random.nextInt(20) + 20) * 20, 2));
        }
    }

    public boolean returnIsAfter(String time){
        if(time != null) {
            return OffsetDateTime.now().isAfter(OffsetDateTime.parse(time, ISO_OFFSET_DATE_TIME));
        }
        return true;
    }
}
