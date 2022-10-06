package com.github.yeetmanlord.reflection_api.mappings;

import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.types.ArguementMethodMapping;
import com.github.yeetmanlord.reflection_api.mappings.types.ArguementlessMethodMapping;
import com.github.yeetmanlord.reflection_api.mappings.types.ClassNameMapping;
import com.github.yeetmanlord.reflection_api.mappings.types.FieldMapping;
import com.github.yeetmanlord.reflection_api.mappings.types.ValueMapping;
import com.github.yeetmanlord.reflection_api.packets.network.NMSNetworkManagerReflection;
import com.github.yeetmanlord.reflection_api.scoreboard.NMSScoreboardTeamReflection;
import com.google.common.collect.Maps;

import io.netty.channel.Channel;

/**
 * 
 * Mappings are for getting changing nms class names, method names, and field
 * names over different versions. It is very important that you use this plugin
 * within the supported version range because if not mappings will not function
 * properly.
 * 
 * @apiNote Examples: <br>
 *          To get a class name use
 *          {@link ClassNameMapping#getNMSClassMapping()}<br>
 *          To get a field name use
 *          {@link FieldMapping#getField(com.github.yeetmanlord.reflection_api.NMSObjectReflection)}<br>
 *          To invoke a method without arguements use
 *          {@link ArguementlessMethodMapping#runMethod(com.github.yeetmanlord.reflection_api.NMSObjectReflection)}<br>
 *          To invoke a method with arguements use
 *          {@link ArguementMethodMapping#runMethod(com.github.yeetmanlord.reflection_api.NMSObjectReflection, Object...)}
 * 
 * @author YeetManLord
 *
 */
public class Mappings {

	public static final ClassNameMapping CHAT_SERIALIZER_CLASS_MAPPING = new ClassNameMapping("ChatSerializer", Maps.newHashMap());

	public static final FieldMapping<Channel> NETWORK_MANAGER_CHANNEL_MAPPING = new FieldMapping<>("NetworkManager Channel", NMSNetworkManagerReflection.class, Maps.newHashMap());

	public static final ClassNameMapping ENUM_PLAYER_ACTION_CLASS_MAPPING = new ClassNameMapping("EnumPlayerAction", Maps.newHashMap());

	public static final ArguementlessMethodMapping<Object> GET_NAMETAG_VISIBILITY_MAPPING = new ArguementlessMethodMapping<>("ScoreboardTeam getNametagVisibility", NMSScoreboardTeamReflection.class, Maps.newHashMap());

	public static final ClassNameMapping ENUM_TITLE_ACTION_CLASS_MAPPING = new ClassNameMapping("EnumTitleAction", Maps.newHashMap());

	public static final ClassNameMapping NAMETAG_VISIBILTY_CLASS_MAPPING = new ClassNameMapping("EnumNametagVisibility", Maps.newHashMap());

	public static final ArguementMethodMapping<Object> SET_NAMETAG_VISIBILITY_MAPPING = new ArguementMethodMapping<>("ScoreboardTeam setNametagVisibility", NMSScoreboardTeamReflection.class, Maps.newHashMap());

	public static final ClassNameMapping PACKET_PLAY_OUT_ENTITY_LOOK_CLASS_MAPPING = new ClassNameMapping("PacketPlayOutEntityLook", Maps.newHashMap());

	public static final ClassNameMapping PACKET_PLAY_OUT_REL_ENTITY_MOVE_LOOK_CLASS_MAPPING = new ClassNameMapping("PacketPlayOutRelEntityMoveLook", Maps.newHashMap());

	public static final ClassNameMapping ENUM_ENTITY_USE_ACTION_CLASS_MAPPING = new ClassNameMapping("EnumEntityUseAction", Maps.newHashMap());
	
	public static final ValueMapping<Integer> DATA_WATCHER_PLAYER_SKIN_LAYER_INDEX = new ValueMapping<>("DataWatcherPlayerSkinLayerIndex", Maps.newHashMap());

	public static void loadMappings() throws MappingsException {

		CHAT_SERIALIZER_CLASS_MAPPING.addMapping(new VersionRange("1.8", "1.8.1"), "ChatSerializer");
		CHAT_SERIALIZER_CLASS_MAPPING.addMapping(new VersionRange("1.8.1", "1.19"), "IChatBaseComponent$ChatSerializer");

		NETWORK_MANAGER_CHANNEL_MAPPING.addMapping(new VersionRange("1.8", "1.8.1"), "i");
		NETWORK_MANAGER_CHANNEL_MAPPING.addMapping(new VersionRange("1.8.1", "1.19"), "channel");

		ENUM_PLAYER_ACTION_CLASS_MAPPING.addMapping(new VersionRange("1.8", "1.8.1"), "EnumPlayerInfoAction");
		ENUM_PLAYER_ACTION_CLASS_MAPPING.addMapping(new VersionRange("1.8.1", "1.19"), "PacketPlayOutPlayerInfo$EnumPlayerInfoAction");

		GET_NAMETAG_VISIBILITY_MAPPING.addMapping(new VersionRange("1.8", "1.8.1"), "i");
		GET_NAMETAG_VISIBILITY_MAPPING.addMapping(new VersionRange("1.8.1", "1.19"), "getNameTagVisibility");

		ENUM_TITLE_ACTION_CLASS_MAPPING.addMapping(new VersionRange("1.8", "1.8.1"), "EnumTitleAction");
		ENUM_TITLE_ACTION_CLASS_MAPPING.addMapping(new VersionRange("1.8.1", "1.19"), "PacketPlayOutTitle$EnumTitleAction");

		NAMETAG_VISIBILTY_CLASS_MAPPING.addMapping(new VersionRange("1.8", "1.8.1"), "EnumNameTagVisibility");
		NAMETAG_VISIBILTY_CLASS_MAPPING.addMapping(new VersionRange("1.8.1", "1.19"), "ScoreboardTeamBase$EnumNameTagVisibility");

		SET_NAMETAG_VISIBILITY_MAPPING.setArgType(NAMETAG_VISIBILTY_CLASS_MAPPING.getNMSClassMapping());
		SET_NAMETAG_VISIBILITY_MAPPING.addMapping(new VersionRange("1.8", "1.8.1"), "a");
		SET_NAMETAG_VISIBILITY_MAPPING.addMapping(new VersionRange("1.8.1", "1.19"), "setNameTagVisibility");

		PACKET_PLAY_OUT_ENTITY_LOOK_CLASS_MAPPING.addMapping(new VersionRange("1.8", "1.8.1"), "PacketPlayOutEntityLook");
		PACKET_PLAY_OUT_ENTITY_LOOK_CLASS_MAPPING.addMapping(new VersionRange("1.8.1", "1.19"), "PacketPlayOutEntity$PacketPlayOutEntityLook");

		PACKET_PLAY_OUT_REL_ENTITY_MOVE_LOOK_CLASS_MAPPING.addMapping(new VersionRange("1.8", "1.8.1"), "PacketPlayOutRelEntityMoveLook");
		PACKET_PLAY_OUT_REL_ENTITY_MOVE_LOOK_CLASS_MAPPING.addMapping(new VersionRange("1.8.1", "1.19"), "PacketPlayOutEntity$PacketPlayOutRelEntityMoveLook");

		ENUM_ENTITY_USE_ACTION_CLASS_MAPPING.addMapping(new VersionRange("1.8", "1.8.1"), "EnumEntityUseAction");
		ENUM_ENTITY_USE_ACTION_CLASS_MAPPING.addMapping(new VersionRange("1.8.1", "1.19"), "PacketPlayInUseEntity$EnumEntityUseAction");

		DATA_WATCHER_PLAYER_SKIN_LAYER_INDEX.addMapping(new VersionRange("1.8", "1.9"), 10);
		DATA_WATCHER_PLAYER_SKIN_LAYER_INDEX.addMapping(new VersionRange("1.9", "1.17"), 12);
		DATA_WATCHER_PLAYER_SKIN_LAYER_INDEX.addMapping(new VersionRange("1.17", "1.19"), 17);

	}

}
