package com.github.yeetmanlord.reflection_api.packets.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;
import com.github.yeetmanlord.reflection_api.packets.in.NMSEntityUsePacketReflection;
import com.github.yeetmanlord.reflection_api.scoreboard.NMSScoreboardTeamReflection;

public class NMSScoreboardTeamPacketReflection extends NMSPacketReflection {

    /**
     * @param team   The team to send the packet for. On creation or updating all settings of this team will be sent.
     * @param action This packet should be used to create, update or remove a team.
     */
    public NMSScoreboardTeamPacketReflection(NMSScoreboardTeamReflection team, TeamPacketAction action) {

        super("PacketPlayOutScoreboardTeam", team.getTeam(), action.getAction());

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
     * @param action      Join or leave the team
     */
    @SuppressWarnings("rawtypes")
    public NMSScoreboardTeamPacketReflection(NMSScoreboardTeamReflection team, ArrayList playerNames, TeamPacketAction action) {

        super("PacketPlayOutScoreboardTeam", classes, team.getTeam(), playerNames, action.getAction());

    }

    /**
     * Creates a blank team and sends it to the client
     */
    public NMSScoreboardTeamPacketReflection() {

        super("PacketPlayOutScoreboardTeam", new Object[]{null});

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

    /**
     * Wrapper enum for the action field of the packet
     */
    public enum TeamPacketAction {
        CREATE(0),
        REMOVE(1),
        UPDATE(2),
        ADD_PLAYERS(3),
        REMOVE_PLAYERS(4);

        private int action;

        TeamPacketAction(int action) {
            this.action = action;
        }

        public int getAction() {
            return action;
        }
    }

}
