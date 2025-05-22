package github.scarsz.discordsrv.objects.proxy;

import dev.vankka.dynamicproxy.processor.Proxy;
import org.bukkit.plugin.Plugin;

@Proxy(Plugin.class)
public abstract class AlwaysEnabledPluginDynamic implements Plugin {

    @Override
    public boolean isEnabled() {
        return true;
    }

}
