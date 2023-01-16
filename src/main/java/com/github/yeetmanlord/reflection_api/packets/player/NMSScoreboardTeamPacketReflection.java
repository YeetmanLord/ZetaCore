package com.github.yeetmanlord.reflection_api.packets.player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;
import com.github.yeetmanlord.reflection_api.scoreboard.NMSScoreboardTeamReflection;
import com.google.common.collect.ImmutableList;

public class NMSScoreboardTeamPacketReflection extends NMSPacketReflection {

    /**
     * @param team   The team to send the packet for. On creation or updating all settings of this team will be sent.
     * @param action This packet should be used to create, update or remove a team.
     */
    public NMSScoreboardTeamPacketReflection(NMSScoreboardTeamReflection team, TeamPacketAction action) {

        super(init(team, action));

    }

    /**
     * To add players or fake players to a team
     *
     * @param team        The team object to join
     * @param playerNames The players by name to add to the team
     * @param action      Join or leave the team
     */
    @SuppressWarnings("rawtypes")
    public NMSScoreboardTeamPacketReflection(NMSScoreboardTeamReflection team, String playerName, TeamPacketAction action) {

        super(init(team, playerName, action));

    }

    /**
     * Creates a blank team and sends it to the client
     *
     * @deprecated Not supported in 1.17+. Use {@link NMSScoreboardTeamPacketReflection#NMSScoreboardTeamPacketReflection(NMSScoreboardTeamReflection, TeamPacketAction)} instead.
     */
    public NMSScoreboardTeamPacketReflection() {

        super(Mappings.PACKET_PLAY_PACKAGE_MAPPING, "PacketPlayOutScoreboardTeam", new Object[]{null});

    }

    private static Object init(NMSScoreboardTeamReflection team, TeamPacketAction action) {

        if (ReflectionApi.version.isNewer(ReflectionApi.v1_17)) {
            try {
                switch (action) {
                    case CREATE:
                        Method m = staticClass.getMethod("a", NMSScoreboardTeamReflection.staticClass, boolean.class);
                        return m.invoke(null, team.getTeam(), false);
                    case UPDATE:
                    case CREATE_AND_ADD_PLAYERS:
                        Method m1 = staticClass.getMethod("a", NMSScoreboardTeamReflection.staticClass, boolean.class);
                        return m1.invoke(null, team.getTeam(), true);
                    case REMOVE:
                        Method m2 = staticClass.getMethod("a", NMSScoreboardTeamReflection.staticClass);
                        return m2.invoke(null, team.getTeam());
                    default:
                        throw new IllegalArgumentException("Invalid team packet action for current parameters");
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Constructor<?> constructor = staticClass.getConstructor(NMSScoreboardTeamReflection.staticClass, int.class);
                return constructor.newInstance(team.getTeam(), action.getAction());
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalStateException("Failed to create packet");
    }

    private static Object init(NMSScoreboardTeamReflection team, String playerName, TeamPacketAction action) {

        if (ReflectionApi.version.isNewer(ReflectionApi.v1_17)) {
            try {
                Class<?> actionClass = ReflectionApi.getNMSInnerClass(Mappings.PACKET_PLAY_PACKAGE_MAPPING, "a", "PacketPlayOutScoreboardTeam");
                Method m = staticClass.getMethod("a", NMSScoreboardTeamReflection.staticClass, String.class,
                        actionClass);
                switch (action) {
                    case ADD_PLAYERS:
                        return m.invoke(null, team.getTeam(), playerName, actionClass.getEnumConstants()[0]);
                    case REMOVE_PLAYERS:
                        return m.invoke(null, team.getTeam(), playerName, actionClass.getEnumConstants()[1]);
                    default:
                        throw new IllegalArgumentException("Invalid team packet action for current parameters");
                }
            } catch (NoSuchMethodException | InvocationTargetException | SecurityException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Constructor<?> constructor = staticClass.getConstructor(NMSScoreboardTeamReflection.staticClass, Collection.class, int.class);
                Collection<String> collection = new ArrayList<>();
                collection.add(playerName);
                return constructor.newInstance(team.getTeam(), collection, action.getAction());
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalStateException("Failed to create packet");

    }

    public NMSScoreboardTeamPacketReflection(Object nmsObject) {

        super(nmsObject);

    }

    public static final Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.PACKET_PLAY_PACKAGE_MAPPING, "PacketPlayOutScoreboardTeam");

    public static NMSScoreboardTeamPacketReflection cast(NMSObjectReflection refl) {

        if (staticClass.isInstance(refl.getNMSObject())) {
            return new NMSScoreboardTeamPacketReflection(refl.getNMSObject());
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
        REMOVE_PLAYERS(4),
        CREATE_AND_ADD_PLAYERS(0);

        private int action;

        TeamPacketAction(int action) {
            this.action = action;
        }

        public int getAction() {
            return action;
        }
    }

}
