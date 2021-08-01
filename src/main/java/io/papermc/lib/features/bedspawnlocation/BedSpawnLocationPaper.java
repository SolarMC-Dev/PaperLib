package io.papermc.lib.features.bedspawnlocation;

import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class BedSpawnLocationPaper implements BedSpawnLocation {

    @Override
    public CompletableFuture<Location> getBedSpawnLocationAsync(Player player, boolean isUrgent) {
/* Solar start
        Location bedLocation = player.getPotentialBedLocation();
        if (bedLocation == null || bedLocation.getWorld() == null) {
            return CompletableFuture.completedFuture(null);
        }
        return PaperLib.getChunkAtAsync(bedLocation.getWorld(), bedLocation.getBlockX() >> 4, bedLocation.getBlockZ() >> 4, false, isUrgent).thenCompose(chunk -> CompletableFuture.completedFuture(player.getBedSpawnLocation()));
*/      throw new IllegalStateException("Please fix me by compiling against a newer bukkit API version");
// Solar end
    }
}
