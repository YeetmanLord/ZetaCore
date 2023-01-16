package com.github.yeetmanlord.reflection_api.scoreboard;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.chat_components.NMSChatSerializerReflection;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;

public class NMSScoreboardTeamReflection extends NMSObjectReflection {

    public NMSScoreboardTeamReflection(Scoreboard scoreboard, String name) {

        super(init(scoreboard, name));

    }

    private static Object init(Scoreboard scoreboard, String name) {

        Object nmsScoreboard = ReflectionApi.getHandle(scoreboard);

        try {
            Constructor<?> constr = ReflectionApi.getNMSClass(Mappings.SCOREBOARD_PACKAGE_MAPPING, "ScoreboardTeam").getConstructor(ReflectionApi.getNMSClass(Mappings.SCOREBOARD_PACKAGE_MAPPING,"Scoreboard"), String.class);
            return constr.newInstance(nmsScoreboard, name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public NMSScoreboardTeamReflection(Object nmsObject) {

        super(nmsObject);

    }

    public Object getTeam() {

        return nmsObject;

    }

    /**
     * @param visibility Allowed values are ALWAYS, NEVER, HIDE_FOR_OTHER_TEAMS,
     *                   HIDE_FOR_OWN_TEAM
     */
    public void setNametagVisibility(NameTagVisibility visibility) {

        Class<?> clazz = null;

        try {
            clazz = Mappings.NAMETAG_VISIBILTY_CLASS_MAPPING.getNMSClassMapping();
        } catch (MappingsException e1) {
            e1.printStackTrace();
        }

        try {
            Object value = clazz.getEnumConstants()[visibility.ordinal()];
            Mappings.SET_NAMETAG_VISIBILITY_MAPPING.runMethod(this, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Takes in a color
     *
     * @param color Color to set chat format
     */
    public void setChatFormat(ChatColor color) {
        Class<?> clazz = ReflectionApi.getNMSClass(Mappings.BASE_PACKAGE, "EnumChatFormat");

        try {
            Object value = clazz.getEnumConstants()[color.ordinal()];
            invokeMethodForNmsObject("a", new Class<?>[]{clazz}, new Object[]{value});
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addPlayerToTeam(String playerName) {
        try {
            Collection<String> names = Mappings.TEAM_GET_PLAYER_LIST_MAPPING.runMethod(this);
            names.add(playerName);

        } catch (MappingsException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSuffix(String suffix) {

        suffix = ChatColor.translateAlternateColorCodes('&', suffix);

        try {
            invokeMethodForNmsObject("setSuffix", new Class<?>[]{String.class}, new Object[]{suffix});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setPrefix(String prefix) {

        prefix = ChatColor.translateAlternateColorCodes('&', prefix);

        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_13)) {
                Mappings.TEAM_SET_PREFIX_MAPPING.runMethod(this, prefix);
            }
            else {
                Mappings.TEAM_SET_PREFIX_MAPPING.runMethod(this, NMSChatSerializerReflection.createChatComponentFromText(prefix));
            }
        } catch (MappingsException e) {
            e.printStackTrace();
        }

    }

    public void setDisplayName(String name) {

        name = ChatColor.translateAlternateColorCodes('&', name);

        try {
            invokeMethodForNmsObject("setDisplayName", new Class<?>[]{String.class}, new Object[]{name});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setAllowFriendlyFire(boolean allow) {

        try {
            invokeMethodForNmsObject("setAllowFriendlyFire", new Class<?>[]{boolean.class}, new Object[]{allow});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setCanSeeFriendlyInvisibles(boolean allow) {

        try {
            invokeMethodForNmsObject("setCanSeeFriendlyInvisibles", new Class<?>[]{boolean.class}, new Object[]{allow});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public String getSuffix() {

        try {
            return (String) invokeMethodForNmsObject("getSuffix");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;

    }

    public String getPrefix() {

        try {
            return (String) invokeMethodForNmsObject("getPrefix");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;

    }

    public String getDisplayName() {

        try {
            return (String) invokeMethodForNmsObject("getDisplayName");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;

    }

    public boolean allowFriendlyFire() {

        try {
            return (boolean) invokeMethodForNmsObject("allowFriendlyFire");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return true;

    }

    public NameTagVisibility getNametagVisibility() {

        try {
            return NameTagVisibility.valueOf(Mappings.GET_NAMETAG_VISIBILITY_MAPPING.runMethod(this).toString());
        } catch (MappingsException e) {
            e.printStackTrace();
        }

        return null;

    }

    public ChatColor getChatFormat() {

        try {
            Object chatFormat = Mappings.GET_TEAM_COLOR_MAPPING.runMethod(this);
            return ChatColor.values()[((Enum<?>) chatFormat).ordinal()];
        } catch (MappingsException e) {
            e.printStackTrace();
        }

        return null;

    }

    public boolean canSeeFriendlyInvisibles() {
        try {
            return (boolean) invokeMethodForNmsObject("canSeeFriendlyInvisibles");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {

        HashMap<String, Object> values = new HashMap<>();
        values.put("type", nmsObject.getClass());
        values.put("team", nmsObject);
        values.put("prefix", getPrefix());
        values.put("chatFormat", getChatFormat());
        values.put("displayName", getDisplayName());
        values.put("nametagVisibility", getNametagVisibility());
        values.put("suffix", getSuffix());
        values.put("allowsFriendlyFire", allowFriendlyFire());
        return "ScoreboardTeamReflection" + values;

    }

    public static final Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.SCOREBOARD_PACKAGE_MAPPING, "ScoreboardTeam");

    public static NMSScoreboardTeamReflection cast(NMSObjectReflection refl) {

        if (staticClass.isInstance(refl.getNMSObject())) {
            return new NMSScoreboardTeamReflection(refl.getNMSObject());
        }

        throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSScoreboardTeamReflection");

    }

}
