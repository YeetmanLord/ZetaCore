package com.github.yeetmanlord.reflection_api.scoreboard;

import java.lang.reflect.Constructor;
import java.util.HashMap;

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
			Constructor<?> constr = ReflectionApi.getNMSClass("ScoreboardTeam").getConstructor(ReflectionApi.getNMSClass("Scoreboard"), String.class);
			return constr.newInstance(nmsScoreboard, name);
		}
		catch (Exception e) {
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
		}
		catch (MappingsException e1) {
			e1.printStackTrace();
		}

		try {
			Object value = clazz.getField(visibility.name().toUpperCase()).get(null);
			Mappings.SET_NAMETAG_VISIBILITY_MAPPING.runMethod(this, value);
			invokeMethodForNmsObject("b", new Class<?>[] { clazz }, new Object[] { value });
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Takes in a color
	 * 
	 * @param color
	 */
	public void setChatFormat(String color) {

		Class<?> clazz = ReflectionApi.getNMSClass("EnumChatFormat");

		try {
			Object value = clazz.getField(color.toUpperCase()).get(null);
			invokeMethodForNmsObject("a", new Class<?>[] { clazz }, new Object[] { value });
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setSuffix(String suffix) {

		suffix = ChatColor.translateAlternateColorCodes('&', suffix);

		try {
			invokeMethodForNmsObject("setSuffix", new Class<?>[] { String.class }, new Object[] { suffix });
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

	}

	public void setPrefix(String prefix) {

		prefix = ChatColor.translateAlternateColorCodes('&', prefix);

		try {
			invokeMethodForNmsObject("setPrefix", new Class<?>[] { String.class }, new Object[] { prefix });
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

	}

	public void setDisplayName(String name) {

		name = ChatColor.translateAlternateColorCodes('&', name);

		try {
			invokeMethodForNmsObject("setDisplayName", new Class<?>[] { String.class }, new Object[] { name });
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

	}

	public void setAllowFriendlyFire(boolean allow) {

		try {
			invokeMethodForNmsObject("setAllowFriendlyFire", new Class<?>[] { boolean.class }, new Object[] { allow });
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

	}

	public String getSuffix() {

		try {
			return (String) invokeMethodForNmsObject("getSuffix");
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return null;

	}

	public String getPrefix() {

		try {
			return (String) invokeMethodForNmsObject("getPrefix");
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return null;

	}

	public String getDisplayName() {

		try {
			return (String) invokeMethodForNmsObject("getDisplayName");
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return null;

	}

	public boolean allowFriendlyFire() {

		try {
			return (boolean) invokeMethodForNmsObject("allowFriendlyFire");
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return true;

	}

	public NameTagVisibility getNametagVisibility() {

		try {
			return NameTagVisibility.valueOf(Mappings.GET_NAMETAG_VISIBILITY_MAPPING.runMethod(this).toString());
		}
		catch (MappingsException e) {
			e.printStackTrace();
		}

		return null;

	}

	public Object getChatFormat() {

		try {
			return invokeMethodForNmsObject("l");
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return null;

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
		return "ScoreboardTeamReflection" + values.toString();

	}

	public static final Class<?> staticClass = ReflectionApi.getNMSClass("ScoreboardTeam");

	public static NMSScoreboardTeamReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNmsObject())) {
			return new NMSScoreboardTeamReflection(refl.getNmsObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSScoreboardTeamReflection");

	}

}
