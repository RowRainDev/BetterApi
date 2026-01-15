package org.example.plugin.betterapi;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class Plr {

    public Location location;
    public Player hyplr;

    public Plr(CommandContext ctx) {
        Ref<EntityStore> ref = ctx.senderAsPlayerRef();
        Player player = ref.getStore().getComponent(ref, com.hypixel.hytale.server.core.entity.entities.Player.getComponentType());
        hyplr = player;
        location = Location.of(ctx);
    }

}
