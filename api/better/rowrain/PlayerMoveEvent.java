package dev.rowrain.plugin.api.better.rowrain;

import com.hypixel.hytale.server.core.entity.entities.Player;

public class PlayerMoveEvent {

    private final Player player;
    private final Location from;
    private final Location to;

    public PlayerMoveEvent(Player player, Location from, Location to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }
}
