package github.scarsz.discordsrv.hooks.vanish;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;

@Deprecated
public class PhantomAdminHook implements VanishHook {

    @Override
    public boolean isVanished(Player player) {
        try {
            Object phantomPlugin = Bukkit.getPluginManager().getPlugin("PhantomAdmin");
            Method isInvisible = phantomPlugin.getClass().getDeclaredMethod("isInvisible", Player.class);
            isInvisible.setAccessible(true);

            return (boolean) isInvisible.invoke(phantomPlugin, player);
        } catch (Exception e) {
            DiscordSRV.error(e);
            return false;
        }
    }

    @Override
    public Plugin getPlugin() {
        return PluginUtil.getPlugin("PhantomAdmin");
    }

}
