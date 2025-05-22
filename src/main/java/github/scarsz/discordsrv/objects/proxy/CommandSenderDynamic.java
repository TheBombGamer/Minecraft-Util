package github.scarsz.discordsrv.objects.proxy;

import dev.vankka.dynamicproxy.processor.Original;
import dev.vankka.dynamicproxy.processor.Proxy;
import github.scarsz.discordsrv.util.DiscordChatChannelCommandFeedbackForwarder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Proxy(CommandSender.class)
public abstract class CommandSenderDynamic implements CommandSender {

    @Original
    private final CommandSender original;
    private final DiscordChatChannelCommandFeedbackForwarder sendUtil;

    public CommandSenderDynamic(CommandSender original, GuildMessageReceivedEvent event) {
        this.original = original;
        this.sendUtil = new DiscordChatChannelCommandFeedbackForwarder(event);
    }

    private void doSend(String message) {
        sendUtil.send(message);
    }

    private void doSend(ComponentLike componentLike) {
        doSend(BukkitComponentSerializer.legacy().serialize(componentLike.asComponent()));
    }
//
//    @Override
//    public void sendMessage(@NotNull Component message) {
//        original.sendMessage(message);
//        doSend(message);
//    }
//
//    @Override
//    public void sendMessage(@NotNull ComponentLike message) {
//        original.sendMessage(message);
//        doSend(message);
//    }
//
//    @Override
//    public void sendMessage(@NotNull Identity source, @NotNull Component message) {
//        original.sendMessage(source, message);
//        doSend(message);
//    }
//
//    @Override
//    public void sendMessage(@NotNull Component message, @NotNull MessageType type) {
//        original.sendMessage(message, type);
//        doSend(message);
//    }
//
//    @Override
//    public void sendMessage(@NotNull Identified source, @NotNull Component message) {
//        original.sendMessage(source, message);
//        doSend(message);
//    }
//
//    @Override
//    public void sendMessage(@NotNull Identity source, @NotNull ComponentLike message) {
//        original.sendMessage(source, message);
//        doSend(message);
//    }
//
//    @Override
//    public void sendMessage(@NotNull ComponentLike message, @NotNull MessageType type) {
//        original.sendMessage(message, type);
//        doSend(message);
//    }
//
//    @Override
//    public void sendMessage(@NotNull Identified source, @NotNull ComponentLike message) {
//        original.sendMessage(source, message);
//        doSend(message);
//    }
//
//    @Override
//    public void sendMessage(@NotNull Identified source, @NotNull Component message, @NotNull MessageType type) {
//        original.sendMessage(source, message, type);
//        doSend(message);
//    }
//
//    @Override
//    public void sendMessage(@NotNull Identity source, @NotNull ComponentLike message, @NotNull MessageType type) {
//        original.sendMessage(source, message, type);
//        doSend(message);
//    }
//
//    @Override
//    public void sendMessage(@NotNull Identified source, @NotNull ComponentLike message, @NotNull MessageType type) {
//        original.sendMessage(source, message, type);
//        doSend(message);
//    }
//
//    @Override
//    public void sendMessage(@NotNull Identity identity, @NotNull Component message, @NotNull MessageType type) {
//        original.sendMessage(identity, message, type);
//        doSend(message);
//    }

    @Override
    public void sendMessage(@NotNull String s) {
        original.sendMessage(s);
        doSend(s);
    }

    @Override
    public void sendMessage(@NotNull String[] strings) {
        original.sendMessage(strings);
        for (String string : strings) {
            doSend(string);
        }
    }

    @Override
    public void sendMessage(@Nullable UUID uuid, @NotNull String s) {
        original.sendMessage(s);
        doSend(s);
    }

    @Override
    public void sendMessage(@Nullable UUID uuid, @NotNull String[] strings) {
        original.sendMessage(strings);
        for (String string : strings) {
            doSend(string);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void sendMessage(@NotNull BaseComponent... components) {
        original.sendMessage(components);
        doSend(BungeeComponentSerializer.get().deserialize(components));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void sendMessage(@NotNull BaseComponent component) {
        original.sendMessage(component);
        doSend(BungeeComponentSerializer.get().deserialize(new BaseComponent[] {component}));
    }

}
