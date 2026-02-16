package me.siam.smartclear;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class SmartNaturalItemClear extends JavaPlugin {

    private final int intervalSeconds = 120;

    private final Set<EntityType> hostileMobs = Set.of(
            EntityType.ZOMBIE,
            EntityType.SKELETON,
            EntityType.CREEPER,
            EntityType.SPIDER,
            EntityType.CAVE_SPIDER,
            EntityType.ENDERMAN,
            EntityType.WITCH,
            EntityType.SLIME,
            EntityType.DROWNED,
            EntityType.HUSK,
            EntityType.STRAY
    );

    @Override
    public void onEnable() {
        startTimers();
    }

    private void startTimers() {

        new BukkitRunnable() {

            int timeLeft = intervalSeconds;

            @Override
            public void run() {

                timeLeft--;

                if (timeLeft == 60) {
                    Bukkit.broadcastMessage("§e[Clear] Items & hostile mobs will be cleared in 60 seconds!");
                }

                if (timeLeft == 30) {
                    Bukkit.broadcastMessage("§c[Clear] Items & hostile mobs will be cleared in 30 seconds!");
                }

                if (timeLeft <= 0) {

                    int itemCount = 0;
                    int mobCount = 0;

                    for (var world : Bukkit.getWorlds()) {

                        for (Entity entity : world.getEntities()) {

                            if (entity instanceof Item item) {
                                if (item.getItemStack().getType() != Material.AIR) {
                                    item.remove();
                                    itemCount++;
                                }
                            }

                            else if (hostileMobs.contains(entity.getType())) {
                                entity.remove();
                                mobCount++;
                            }
                        }
                    }

                    Bukkit.broadcastMessage("§a[Clear] Removed "
                            + itemCount + " items and "
                            + mobCount + " hostile mobs.");

                    timeLeft = intervalSeconds;
                }
            }

        }.runTaskTimer(this, 20, 20);
    }
}
