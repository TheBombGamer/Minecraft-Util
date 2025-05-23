package github.scarsz.discordsrv.hooks.vanish;

import de.myzelyam.api.vanish.PostPlayerHideEvent;
import de.myzelyam.api.vanish.PostPlayerShowEvent;
import de.myzelyam.api.vanish.VanishAPI;
import de.myzelyam.supervanish.SuperVanish;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.objects.MessageFormat;
import github.scarsz.discordsrv.util.GamePermissionUtil;
import github.scarsz.discordsrv.util.LangUtil;
import github.scarsz.discordsrv.util.PluginUtil;
import github.scarsz.discordsrv.util.SchedulerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unchecked")
public class SuperVanishHook implements VanishHook {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerShow(PostPlayerShowEvent event) {
        SuperVanish plugin = (SuperVanish) getPlugin();
        if (!plugin.getSettings().getBoolean("MessageOptions.FakeJoinQuitMessages.BroadcastFakeJoinOnReappear") ||
                event.isSilent()) {
            return;
        }

        final Player player = event.getPlayer();

        String joinMessage = plugin.replacePlaceholders("VanishMessage", player);

        MessageFormat messageFormat = DiscordSRV.getPlugin().getMessageFromConfiguration("MinecraftPlayerJoinMessage");

        // make sure join messages enabled
        if (messageFormat == null) return;

        final String name = player.getName();

        // check if player has permission to not have join messages
        if (GamePermissionUtil.hasPermission(event.getPlayer(), "discordsrv.silentjoin")) {
            DiscordSRV.info(LangUtil.InternalMessage.SILENT_JOIN.toString()
                    .replace("{player}", name)
            );
            return;
        }

        // player doesn't have silent join permission, send join message

        // schedule command to run in a second to be able to capture display name
        SchedulerUtil.runTaskLaterAsynchronously(DiscordSRV.getPlugin(), () ->
                DiscordSRV.getPlugin().sendJoinMessage(event.getPlayer(), joinMessage), 20);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerHide(PostPlayerHideEvent event) {
        SuperVanish plugin = (SuperVanish) getPlugin();
        if (!plugin.getSettings().getBoolean("MessageOptions.FakeJoinQuitMessages.BroadcastFakeQuitOnVanish") ||
                event.isSilent()) {
            return;
        }

        final Player player = event.getPlayer();

        MessageFormat messageFormat = DiscordSRV.getPlugin().getMessageFromConfiguration("MinecraftPlayerLeaveMessage");

        // make sure quit messages enabled
        if (messageFormat == null) return;

        final String name = player.getName();

        String joinMessage = plugin.replacePlaceholders("ReappearMessage", player);

        // no quit message, user shouldn't have one from permission
        if (GamePermissionUtil.hasPermission(event.getPlayer(), "discordsrv.silentquit")) {
            DiscordSRV.info(LangUtil.InternalMessage.SILENT_QUIT.toString()
                    .replace("{player}", name)
            );
            return;
        }

        // player doesn't have silent quit, show quit message
        SchedulerUtil.runTaskAsynchronously(DiscordSRV.getPlugin(),
                () -> DiscordSRV.getPlugin().sendLeaveMessage(event.getPlayer(), joinMessage));
    }

    @Override
    public boolean isVanished(Player player) {
        return VanishAPI.isInvisible(player);
    }

    @Override
    public Plugin getPlugin() {
        return PluginUtil.getPlugin("SuperVanish");
    }

}
