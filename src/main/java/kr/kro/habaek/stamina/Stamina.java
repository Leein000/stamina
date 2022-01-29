package kr.kro.habaek.stamina;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Stamina extends JavaPlugin implements Listener {

    private int num = 0;
    private int move_num = 0;
    private List<Player> temp = new ArrayList<>();

    public void onEnable(){
        super.onEnable();
        getServer().getPluginManager().registerEvents(this, this);

        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player p : getServer().getOnlinePlayers()) {
                    if(temp.contains(p)) {
                        if(p.getFoodLevel() < 20) {
                            int tempF = p.getFoodLevel();
                            tempF += 1;
                            p.setFoodLevel(tempF);
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0, 10);
    }
    public void onDisable() {
        super.onDisable();

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        temp.add(p);
        if(p.isSprinting()) {
            move_num++;
            if(num==20){
                if(p.getFoodLevel() < 20) {
                    int tempF = p.getFoodLevel();
                    tempF += 1;
                    p.setFoodLevel(tempF);
                    num = 0;
                }
            }

        }
        else if(!p.isSprinting()) {
            num++;
            if(num==20) {
                if(p.getFoodLevel() < 20) {
                    int tempF = p.getFoodLevel();
                    tempF += 1;
                    p.setFoodLevel((tempF));
                    num = 0;
                }
            } else {
                num = 0;

            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                temp.remove(p);
            }
        }.runTaskLater(this, 5);
    }
}
