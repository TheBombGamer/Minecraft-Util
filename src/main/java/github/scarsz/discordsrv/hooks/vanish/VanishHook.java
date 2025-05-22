package github.scarsz.discordsrv.hooks.vanish;

import github.scarsz.discordsrv.hooks.PluginHook;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

/**
 * @deprecated since 1.26.0. Please use the "vanished" metadata key instead ({@link Player#setMetadata(String, MetadataValue)}).
 */
@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public interface VanishHook extends PluginHook {

    /**
     * @deprecated since 1.26.0. Please use the "vanished" metadata key instead ({@link Player#setMetadata(String, MetadataValue)}).
     */
    @Deprecated
    boolean isVanished(Player player);

}
