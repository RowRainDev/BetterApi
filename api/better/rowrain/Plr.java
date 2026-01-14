package org.example.plugin.betterapi;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.concurrent.CompletableFuture;

public class Plr {

    public Location location;
    public Player hyplr;

    public Plr(CommandContext ctx) {
        Ref<EntityStore> ref = ctx.senderAsPlayerRef();
        Player player = ref.getStore().getComponent(ref, com.hypixel.hytale.server.core.entity.entities.Player.getComponentType());
        hyplr = player;
        CompletableFuture<Location> future = new CompletableFuture<>();
        player.getWorld().execute(() -> {
            try{
                TransformComponent transform = ref.getStore().getComponent(ref, TransformComponent.getComponentType());
                Vector3d pos = transform.getPosition();
                String worldName = player.getWorld().getName();
                future.complete(new Location(pos.x, pos.y, pos.z, worldName));
            }catch (Exception e) {
                future.complete(new Location(0,0,0, "none(error)"));
            }
        });

        try { location = future.get(); } catch (Exception e) { location = new Location(0, 0, 0, "none(error)"); }
    }

    public static void moveTo(Plr plr, Location loc) {
        Player plrr = plr.hyplr;
        Ref<EntityStore> ref = plr.hyplr.getReference();
        plrr.getWorld().execute(() ->{
            TransformComponent transform = ref.getStore().getComponent(ref, TransformComponent.getComponentType());
            transform.setPosition(new Vector3d(loc.x, loc.y, loc.z));
        });
    }
}
