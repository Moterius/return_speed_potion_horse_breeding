package io.github.moterius.return_speed_potion_horse_breeding;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.EnumSet;

public class Echo extends JavaPlugin implements Listener {
    static FileConfiguration config;

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        config = getConfig();
    }

    @EventHandler
    public void event(EntityBreedEvent e){
        if(!config.getBoolean("allow_everything")){
            EnumSet<EntityType> typeSet = EnumSet.noneOf(EntityType.class);
            if(config.getBoolean("allow_donkeys"))
                typeSet.add(EntityType.DONKEY);
            if(config.getBoolean("allow_horses"))
                typeSet.add(EntityType.HORSE);
            if(config.getBoolean("allow_mules"))
                typeSet.add(EntityType.MULE);
            if(config.getBoolean("allow_pigs"))
                typeSet.add(EntityType.PIG);
            if(config.getBoolean("allow_strider"))
                typeSet.add(EntityType.STRIDER);

            if(! typeSet.contains(e.getEntity().getType()) )
                return;
        }

        LivingEntity a = e.getBreeder();
        LivingEntity b = e.getEntity();

        if(config.getBoolean("effect_speed") &&
                (a.hasPotionEffect(PotionEffectType.SPEED)||b.hasPotionEffect(PotionEffectType.SPEED))){
            double s_a = a.hasPotionEffect(PotionEffectType.SPEED) ?
                    a.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() :
                    a.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
            double s_b = b.hasPotionEffect(PotionEffectType.SPEED) ?
                    b.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() :
                    b.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
            double avg = (s_a + s_b) / 2;
            e.getEntity().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(avg);
        }

        if(config.getBoolean("effect_jump") && e.getEntityType() == EntityType.HORSE &&
                (a.hasPotionEffect(PotionEffectType.JUMP)||b.hasPotionEffect(PotionEffectType.JUMP))){
            double s_a = a.hasPotionEffect(PotionEffectType.JUMP) ?
                    a.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getValue() :
                    a.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getBaseValue();
            double s_b = b.hasPotionEffect(PotionEffectType.JUMP) ?
                    b.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getValue() :
                    b.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getBaseValue();
            double avg = (s_a + s_b) / 2;
            e.getEntity().getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(avg);
        }
    }
}
