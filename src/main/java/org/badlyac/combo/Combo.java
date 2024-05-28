package org.badlyac.combo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Combo extends JavaPlugin implements CommandExecutor, Listener {

    private int maxNoDamageTicks = 20; // 預設值

    @Override
    public void onEnable() {
        this.getCommand("setMaxNoDamageTicks").setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("setMaxNoDamageTicks")) {
            if (args.length != 1) {
                sender.sendMessage("Usage: /setMaxNoDamageTicks <ticks>");
                return false;
            }

            try {
                maxNoDamageTicks = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid number of ticks.");
                return false;
            }

            for (Entity entity : Bukkit.getWorlds().get(0).getEntities()) {
                if (entity instanceof LivingEntity) {
                    ((LivingEntity) entity).setMaximumNoDamageTicks(maxNoDamageTicks);
                }
            }

            sender.sendMessage("Set MaxNoDamageTicks to " + maxNoDamageTicks + " for all entities.");
            return true;
        }
        return false;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).setMaximumNoDamageTicks(maxNoDamageTicks);
        }
    }
}