package com.github.yeetmanlord.zeta_core.api.util;

import com.github.yeetmanlord.reflection_api.chat_components.NMSChatSerializerReflection;
import com.github.yeetmanlord.reflection_api.entity.players.NMSPlayerReflection;
import com.github.yeetmanlord.reflection_api.entity.players.player_connection.NMSPlayerConnectionReflection;
import com.github.yeetmanlord.reflection_api.packets.chat.NMSChatPacketReflection;
import com.github.yeetmanlord.reflection_api.packets.player.NMSTitlePacketReflection;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PluginUtilities {

    public static String getBooleanColor(boolean bool) {
        return "Current: " + (bool ? ChatColor.GREEN.toString() + true : ChatColor.RED.toString() + false);
    }

    public static String[] fromListString(List<String> list) {

        String[] array = new String[list.size()];

        for (int x = 0; x < list.size(); x++) {
            array[x] = list.get(x);
        }

        return array;

    }

    public static <T> T[] fromGenericList(T[] arrayA, List<T> list) {

        T[] array = Arrays.copyOf(arrayA, list.size());

        for (int x = 0; x < list.size(); x++) {
            array[x] = list.get(x);
        }

        return array;

    }

    public static int[] fromListInt(List<Integer> list) {

        int[] array = new int[list.size()];

        for (int x = 0; x < list.size(); x++) {
            array[x] = list.get(x);
        }

        return array;

    }

    public static List<String> getLore(ItemMeta meta) {

        List<String> empty = new ArrayList<>();

        if (meta == null) {
            return empty;
        }

        if (meta.getLore() != null) {
            return meta.getLore();
        }

        return empty;

    }

    public static void sendTitlePackets(Player player, String title, @Nullable String subtitle, @Nullable String actionBar) {
        sendTitlePackets(player, title, subtitle, actionBar, 5, 400, 40);
    }

    public static void sendTitlePackets(Player bPlayer, String title, String subtitle, @Nullable String actionBar, int fadeIn, int stay, int fadeOut) {

        NMSPlayerReflection player = new NMSPlayerReflection(bPlayer);
        NMSPlayerConnectionReflection connection = player.getPlayerConnection();
        NMSTitlePacketReflection titlePacket = new NMSTitlePacketReflection(NMSTitlePacketReflection.NMSEnumTitleAction.TITLE, NMSChatSerializerReflection.createChatComponentFromText(title));
        NMSTitlePacketReflection subtitlePacket = new NMSTitlePacketReflection(NMSTitlePacketReflection.NMSEnumTitleAction.SUBTITLE, NMSChatSerializerReflection.createChatComponentFromText(subtitle));
        NMSChatPacketReflection actionBarPacket = null;

        if (actionBar != null) {
            actionBarPacket = new NMSChatPacketReflection(actionBar, NMSChatPacketReflection.EnumChatPosition.GAME_INFO);
        }

        NMSTitlePacketReflection timesPacket = new NMSTitlePacketReflection(fadeIn, stay, fadeOut);

        if (actionBarPacket != null) {
            connection.sendPacket(actionBarPacket);
        }

        connection.sendPacket(timesPacket);
        connection.sendPacket(titlePacket);
        connection.sendPacket(subtitlePacket);

    }

    public static String titleCase(String initial) {

        StringBuilder builder = new StringBuilder();
        initial = initial.replaceAll("_", " ");

        for (String s : initial.split(" ")) {

            for (int x = 0; x < s.length(); x++) {
                char c = s.charAt(x);

                if (x == 0) {
                    builder.append(String.valueOf(c).toUpperCase());
                } else {
                    builder.append(String.valueOf(c).toLowerCase());
                }

            }

            builder.append(" ");

        }

        return builder.toString().trim();

    }

}
