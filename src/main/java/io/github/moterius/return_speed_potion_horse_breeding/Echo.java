package io.github.moterius.return_speed_potion_horse_breeding;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class Echo extends JavaPlugin implements Listener {
    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void event(EntityBreedEvent e){
        if(e.getFather().getType() == EntityType.HORSE &&
        e.getMother().getType() == EntityType.HORSE){
            LivingEntity a = e.getBreeder();
            LivingEntity b = e.getEntity();
            double s_a = a.hasPotionEffect(PotionEffectType.SPEED) ?
                    a.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() :
                    a.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
            double s_b = b.hasPotionEffect(PotionEffectType.SPEED) ?
                    b.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() :
                    b.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
            double avg = (s_a + s_b) / 2;
            e.getEntity().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(avg);
            Bukkit.broadcast("Speed: "+avg,"*");
        }

    }
}
