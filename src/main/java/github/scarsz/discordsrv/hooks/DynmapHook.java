package github.scarsz.discordsrv.hooks;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.DiscordUtil;
import github.scarsz.discordsrv.util.LangUtil;
import github.scarsz.discordsrv.util.MessageUtil;
import github.scarsz.discordsrv.util.PluginUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapWebChatEvent;

public class DynmapHook implements PluginHook {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDynmapWebChat(DynmapWebChatEvent event) {
        String format = LangUtil.Message.DYNMAP_DISCORD_FORMAT.toString()
                .replace("%message%", MessageUtil.strip(DiscordUtil.escapeMarkdown(event.getMessage())))
                .replace("%name%", MessageUtil.strip(DiscordUtil.escapeMarkdown(event.getName())));

        if (!DiscordSRV.config().getBoolean("DiscordChatChannelTranslateMentions")) {
            format = format.replace("@", "@\u200B"); // zero-width space
        }

        DiscordUtil.sendMessage(DiscordSRV.getPlugin().getOptionalTextChannel("dynmap"), format);
    }

    public void broadcastMessageToDynmap(String name, String message) {
        try {
            DynmapCommonAPI api = (DynmapCommonAPI) getPlugin();
            if (api == null) return;
            api.sendBroadcastToWeb(name, message);
        } catch (Throwable t) {
            DiscordSRV.warning("Failed to send message to dynmap: " + t.toString());
            DiscordSRV.debug(ExceptionUtils.getStackTrace(t));
        }
    }

    @Override
    public Plugin getPlugin() {
        return PluginUtil.getPlugin("dynmap");
    }

}
