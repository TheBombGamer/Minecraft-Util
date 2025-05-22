package github.scarsz.discordsrv.hooks.world;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.hooks.PluginHook;
import github.scarsz.discordsrv.util.PluginUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MultiverseCoreHook implements PluginHook {

    public static String getWorldAlias(String world) {
        try {
            if (!PluginUtil.pluginHookIsEnabled("Multiverse-Core")) return world;

            com.onarandombox.MultiverseCore.MultiverseCore multiversePlugin = (com.onarandombox.MultiverseCore.MultiverseCore) Bukkit.getPluginManager().getPlugin("Multiverse-Core");
            if (multiversePlugin != null) {
                MultiverseWorld multiverseWorld = multiversePlugin.getMVWorldManager().getMVWorld(world);
                if (multiverseWorld != null) {
                    String alias = multiverseWorld.getAlias();
                    return StringUtils.isNotBlank(alias) ? alias : world;
                }
            }
        } catch (Exception e) {
            DiscordSRV.error(e);
        }
        return world;
    }

    @Override
    public Plugin getPlugin() {
        return PluginUtil.getPlugin("Multiverse-Core");
    }

}
