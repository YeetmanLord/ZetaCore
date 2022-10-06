package com.github.yeetmanlord.reflection_api.packets.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;
import com.github.yeetmanlord.reflection_api.scoreboard.NMSScoreboardTeamReflection;

public class NMSScoreboardTeamPacketReflection extends NMSPacketReflection {

	/**
	 * @param action To create a team client-side use 1 use 0 to send team settings
	 *               to the client
	 */
	public NMSScoreboardTeamPacketReflection(NMSScoreboardTeamReflection team, int action) {

		super("PacketPlayOutScoreboardTeam", team.getTeam(), action);

	}

	private static HashMap<Class<?>, Integer> classes = new HashMap<>();
	static {
		classes.put(ReflectionApi.getNMSClass("ScoreboardTeam"), 0);
		classes.put(Collection.class, 1);
		classes.put(int.class, 2);
	}

	/**
	 * To add players or fake players to a team
	 * 
	 * @param team        The team object to join
	 * @param playerNames The players by name to add to the team
	 * @param action      can have the values of 3 (Join team) or 4 (Leave team)
	 */
	@SuppressWarnings("rawtypes")
	public NMSScoreboardTeamPacketReflection(NMSScoreboardTeamReflection team, ArrayList playerNames, int action) {

		super("PacketPlayOutScoreboardTeam", classes, team.getTeam(), playerNames, action);

	}

	/**
	 * Creates a blank team and sends it to the client
	 */
	public NMSScoreboardTeamPacketReflection() {

		super("PacketPlayOutScoreboardTeam", new Object[] { null });

	}

	public NMSScoreboardTeamPacketReflection(Object nmsObject) {

		super(nmsObject);

	}

	public static final Class<?> staticClass = ReflectionApi.getNMSClass("PacketPlayOutScoreboardTeam");

	public static NMSScoreboardTeamPacketReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNmsObject())) {
			return new NMSScoreboardTeamPacketReflection(refl.getNmsObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSScoreboardTeamPacketReflection");

	}

}
