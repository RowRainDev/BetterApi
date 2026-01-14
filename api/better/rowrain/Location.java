package org.example.plugin.betterapi;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.swing.text.html.parser.Entity;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class Location {

    public final double x;
    public final double y;
    public final double z;
    public final String world;

    public Location(double x, double y, double z, String world){
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public static String toString(Location loca){
        String loc = String.format("X= %.2f Y= %.2f Z= %.2f World= %s", loca.x, loca.y, loca.z, loca.world);
        return loc;
    }

    public static Location of(CommandContext ctx) {
        Ref<EntityStore> ref = ctx.senderAsPlayerRef();
        if (!ref.isValid()) {
            return new Location(0, 0, 0, "none");
        }

        EntityStore store = ref.getStore().getExternalData();
        CompletableFuture<Location> future = new CompletableFuture<>();

        store.getWorld().execute(() -> {
            try {
                TransformComponent transform = ref.getStore().getComponent(ref, TransformComponent.getComponentType());
                Vector3d pos = transform.getPosition();
                String worldName = store.getWorld().getName();
                future.complete(new Location(pos.x, pos.y, pos.z, worldName));
            } catch (Exception e) {
                future.complete(new Location(0, 0, 0, "none"));
            }
        });

        try {
            return future.get();
        } catch (Exception e) {
            return new Location(0, 0, 0, "none");
        }
    }

}
