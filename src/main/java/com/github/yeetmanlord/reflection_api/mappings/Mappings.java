package com.github.yeetmanlord.reflection_api.mappings;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.Version;
import com.github.yeetmanlord.reflection_api.chat_components.NMSChatComponentTextReflection;
import com.github.yeetmanlord.reflection_api.entity.NMSDataWatcherReflection;
import com.github.yeetmanlord.reflection_api.entity.NMSEntityReflection;
import com.github.yeetmanlord.reflection_api.entity.players.NMSPlayerReflection;
import com.github.yeetmanlord.reflection_api.entity.players.player_connection.NMSPlayerConnectionReflection;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.inventory.NMSItemStackReflection;
import com.github.yeetmanlord.reflection_api.mappings.types.*;
import com.github.yeetmanlord.reflection_api.nbt.NMSNBTTagCompoundReflection;
import com.github.yeetmanlord.reflection_api.packets.in.NMSEntityUsePacketReflection;
import com.github.yeetmanlord.reflection_api.packets.network.NMSNetworkManagerReflection;
import com.github.yeetmanlord.reflection_api.scoreboard.NMSScoreboardTeamReflection;
import com.github.yeetmanlord.reflection_api.world.NMSWorldServerReflection;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Mappings are for getting changing nms class names, method names, and field
 * names over different versions. It is very important that you use this plugin
 * within the supported version range because if not mappings will not function
 * properly.
 *
 * @author YeetManLord
 * @apiNote Examples: <br>
 * To get a class name use
 * {@link ClassNameMapping#getNMSClassMapping()}<br>
 * To get a field name use
 * {@link FieldMapping#getField(com.github.yeetmanlord.reflection_api.NMSObjectReflection)}<br>
 * To invoke a method without arguements use
 * {@link ArgumentlessMethodMapping#runMethod(com.github.yeetmanlord.reflection_api.NMSObjectReflection)}<br>
 * To invoke a method with arguements use
 * {@link ArgumentMethodMapping#runMethod(com.github.yeetmanlord.reflection_api.NMSObjectReflection, Object...)}
 */
public class Mappings {

    public static final List<IMapping> mappings = Lists.newArrayList();

    /**
     * The package mapping for the `net.minecraft.server.<span>&lt</span>version&gt` package. In versions 1.17+, it is `net.minecraft`
     */
    public static final PackageMapping BASE_PACKAGE = new PackageMapping("NMS Base Package (net.minecraft.server)", Maps.newHashMap());

    /**
     * The package for chat related items, in 1.17+ it is `net.minecraft.network.chat`
     */
    public static final PackageMapping CHAT_PACKAGE_MAPPING = new PackageMapping("Chat Package", Maps.newHashMap());

    /**
     * The package for PlayOut and PlayIn packets, in 1.17+ it is `net.minecraft.network.protocol.game`
     */
    public static final PackageMapping PACKET_PLAY_PACKAGE_MAPPING = new PackageMapping("Packet Package", Maps.newHashMap());

    /**
     * The class mapping for the chat serializer class. In 1.8, it is `ChatSerializer`, afterwards it is `IChatBaseComponent$ChatSerializer`
     */
    public static final ClassNameMapping CHAT_SERIALIZER_CLASS_MAPPING = new ClassNameMapping("ChatSerializer", CHAT_PACKAGE_MAPPING, Maps.newHashMap());

    /**
     * The field mapping for the channel field in the NetworkManager class. In 1.8, it is `i`, afterwards it is `channel`
     */
    public static final FieldMapping<Channel, NMSNetworkManagerReflection> NETWORK_MANAGER_CHANNEL_MAPPING = new FieldMapping<>("NetworkManager Channel", NMSNetworkManagerReflection.class, Maps.newHashMap());

    /**
     * The class mapping for the title packet class. In 1.8-1.16.5 it is `PacketPlayOutTitle`, in 1.17+ it is `ClientboundSetTitleTextPacket`
     * Use {@link com.github.yeetmanlord.reflection_api.packets.player.NMSTitlePacketReflection} to easily wrap these packets.
     * Using this class you won't have to deal with mappings or the version.
     */

    public static final ClassNameMapping TITLE_PACKET_CLASS_MAPPING = new ClassNameMapping("Title Packet", PACKET_PLAY_PACKAGE_MAPPING, Maps.newHashMap());

    /**
     * The class mapping for the subtitle packet class. In 1.8-1.16.5 it is `PacketPlayOutTitle`, in 1.17+ it is `ClientboundSetSubtitleTextPacket`
     * Use {@link com.github.yeetmanlord.reflection_api.packets.player.NMSTitlePacketReflection} to easily wrap these packets.
     * Using this class you won't have to deal with mappings or the version.
     */
    public static final ClassNameMapping SUBTITLE_PACKET_CLASS_MAPPING = new ClassNameMapping("Subtitle Packet", PACKET_PLAY_PACKAGE_MAPPING, Maps.newHashMap());

    /**
     * The class mapping for the clear packet class. In 1.8-1.16.5 it is `PacketPlayOutTitle`, in 1.17+ it is `ClientboundClearTitlesPacket`
     * Use {@link com.github.yeetmanlord.reflection_api.packets.player.NMSTitlePacketReflection} to easily wrap these packets.
     * Using this class you won't have to deal with mappings or the version.
     */
    public static final ClassNameMapping TITLE_CLEAR_PACKET_CLASS_MAPPING = new ClassNameMapping("Title Clear Packet", PACKET_PLAY_PACKAGE_MAPPING, Maps.newHashMap());

    /**
     * The class mapping for the title timings packet class. In 1.8-1.16.5 it is `PacketPlayOutTitle`, in 1.17+ it is `ClientboundSetTitlesAnimationPacket`.
     * Use {@link com.github.yeetmanlord.reflection_api.packets.player.NMSTitlePacketReflection} to easily wrap these packets.
     * Using this class you won't have to deal with mappings or the version.
     */
    public static final ClassNameMapping TITLE_TIMINGS_PACKET_CLASS_MAPPING = new ClassNameMapping("Title Timings Packet", PACKET_PLAY_PACKAGE_MAPPING, Maps.newHashMap());

    /**
     * The class mapping for the action type of the PacketPlayOutPlayerInfo packet. In 1.8, it is `EnumPlayerAction`,
     * from 1.8.1 to 1.16.5, it is `PacketPlayOutPlayerInfo$EnumPlayerAction`, from 1.17+ it is `PacketPlayOutPlayerInfo$EnumPlayerInfoAction`
     */
    public static final ClassNameMapping ENUM_PLAYER_ACTION_CLASS_MAPPING = new ClassNameMapping("EnumPlayerAction", PACKET_PLAY_PACKAGE_MAPPING, Maps.newHashMap());

    /**
     * The method name mapping for the getNametagVisibility method in ScoreboardTeam. In 1.8, it is `i`, afterwards it is `getNameTagVisibility`
     */
    public static final ArgumentlessMethodMapping<Object, NMSScoreboardTeamReflection> GET_NAMETAG_VISIBILITY_MAPPING = new ArgumentlessMethodMapping<>("ScoreboardTeam getNametagVisibility", NMSScoreboardTeamReflection.class, Maps.newHashMap());

    /**
     * The method name mapping for the getColor method in ScoreboardTeam. From 1.8 - 1.16.5, it is `l`, from 1.17 to 1.18 it is `getColor`, afterwards it is `n`
     */
    public static final ArgumentlessMethodMapping<Object, NMSScoreboardTeamReflection> GET_TEAM_COLOR_MAPPING = new ArgumentlessMethodMapping<>("ScoreboardTeam getColor", NMSScoreboardTeamReflection.class, Maps.newHashMap());

    /**
     * The class name mapping for the PacketPlayOutTitle's EnumTitleAction. In 1.8, it is `EnumTitleAction`, afterwards it is `PacketPlayOutTitle$EnumTitleAction`
     */
    public static final ClassNameMapping ENUM_TITLE_ACTION_CLASS_MAPPING = new ClassNameMapping("EnumTitleAction", PACKET_PLAY_PACKAGE_MAPPING, Maps.newHashMap());

    /**
     * The package mapping for the scoreboard package. In 1.17+, it is `net.minecraft.world.scores`
     */
    public static final PackageMapping SCOREBOARD_PACKAGE_MAPPING = new PackageMapping("Scoreboard Package", Maps.newHashMap());

    /**
     * The class name mapping for NametagVisibility. In 1.8, it is `EnumNameTagVisibility`, afterwards it is `ScoreboardTeamBase$EnumNameTagVisibility`
     */
    public static final ClassNameMapping NAMETAG_VISIBILTY_CLASS_MAPPING = new ClassNameMapping("EnumNametagVisibility", SCOREBOARD_PACKAGE_MAPPING, Maps.newHashMap());

    /**
     * The argument name mapping for ScoreboardTeam's setNametagVisibility method. In 1.8, it is `a`, afterwards it is `setNametagVisibility`
     */
    public static final ArgumentMethodMapping<Void, NMSScoreboardTeamReflection> SET_NAMETAG_VISIBILITY_MAPPING = new ArgumentMethodMapping<>("ScoreboardTeam setNametagVisibility", NMSScoreboardTeamReflection.class, Maps.newHashMap());

    /**
     * The class name mapping for the entity look packet. In 1.8, it is `PacketPlayOutEntityLook`, afterwards it is `PacketPlayOutEntity$PacketPlayOutEntityLook`
     */
    public static final ClassNameMapping ENTITY_LOOK_PACKET_CLASS_MAPPING = new ClassNameMapping("PacketPlayOutEntityLook", PACKET_PLAY_PACKAGE_MAPPING, Maps.newHashMap());

    /**
     * The class name mapping for the entity look and relative move packet. In 1.8, it is `PacketPlayOutRelEntityMoveLook`, afterwards it is `PacketPlayOutEntity$PacketPlayOutRelEntityMoveLook`
     */
    public static final ClassNameMapping REL_ENTITY_MOVE_LOOK_PACKET_CLASS_MAPPING = new ClassNameMapping("PacketPlayOutRelEntityMoveLook", PACKET_PLAY_PACKAGE_MAPPING, Maps.newHashMap());

    /**
     * The class mapping for the action type of the PacketPlayInUseEntity packet. In 1.8, it is `EnumEntityUseAction`, afterwards it is `PacketPlayInUseEntity$EnumEntityUseAction`
     */
    public static final ClassNameMapping ENUM_ENTITY_USE_ACTION_CLASS_MAPPING = new ClassNameMapping("EnumEntityUseAction", PACKET_PLAY_PACKAGE_MAPPING, Maps.newHashMap());

    /**
     * Value mapping for which datawatcher index handles a player's skin layers.<br>
     * 1.8 - 1.9, it is 10,<br>
     * 1.9 - 1.10, it is 12,<br>
     * 1.10 - 1.15 it is 13,<br>
     * 1.15 - 1.16.5 it is 16<br>
     * 1.17+ it is 17<br>
     */
    public static final ValueMapping<Integer> DATA_WATCHER_PLAYER_SKIN_LAYER_INDEX = new ValueMapping<>("DataWatcherPlayerSkinLayerIndex", Maps.newHashMap());

    /**
     * The method name mapping for PacketPlayInUseEntity's getAction method. From 1.8 to 1.13, it is `a`, afterwards it is `b`
     */
    public static final ArgumentlessMethodMapping<Object, NMSEntityUsePacketReflection> PACKET_ENTITY_USE_GET_ACTION_MAPPING = new ArgumentlessMethodMapping<>("PacketEntityUseAction getAction", NMSEntityUsePacketReflection.class, Maps.newHashMap());

    /**
     * The method name mapping for PacketPlayInUseEntity's getHand method. From 1.9 to 1.13, it is `b`, afterwards it is `c`
     */
    public static final ArgumentlessMethodMapping<Object, NMSEntityUsePacketReflection> PACKET_ENTITY_USE_GET_HAND_MAPPING = new ArgumentlessMethodMapping<>("PacketEntityUseAction getHand", NMSEntityUsePacketReflection.class, Maps.newHashMap());

    /**
     * The field name mapping for WorldServer's entity list. From 1.8 - 1.14, it is `entityList`, from 1.14 - 1.16 it is `globalEntitiesList`, from 1.16 - 1.17 it is `entitiesByUUID`, version afterwards do not have this field
     *
     * @apiNote From 1.16-1.17 This field is not actually a list but a map of UUIDs to entities, whereas previously it was simply a list of entities.
     */
    public static final FieldMapping<Object, NMSWorldServerReflection> WORLD_SERVER_ENTITY_LIST_MAPPING = new FieldMapping<>("WorldServer EntityList", NMSWorldServerReflection.class, Maps.newHashMap());

    /**
     * The method mapping for WorldServer's entity iterable. From 1.17 - 1.19, it is `C`, afterwards it is `z`
     */
    public static final ArgumentlessMethodMapping<Iterable<?>, NMSWorldServerReflection> WORLD_SERVER_ENTITY_ITERABLE_MAPPING = new ArgumentlessMethodMapping<>("WorldServer EntityIterable", NMSWorldServerReflection.class, Maps.newHashMap());

    /**
     * The field name mapping to get a datawatcher's list of items. From 1.8 - 1.10, it is `b`, from 1.10 - 1.14 it is `c`, 1.17+ it is `entries`
     */
    public static final FieldMapping<Map<Integer, Object>, NMSDataWatcherReflection> DATAWATCHER_ITEMS_MAPPING = new FieldMapping<>("DataWatcher Objects", NMSDataWatcherReflection.class, Maps.newHashMap());

    /**
     * The package mapping for the entity package. In 1.17+, it is `net.minecraft.world.entity`
     */
    public static final PackageMapping ENTITY_PACKAGE_MAPPING = new PackageMapping("Entity Package", Maps.newHashMap());

    /**
     * The package mapping for the handshake packet package. In 1.17+, it is `net.minecraft.network.protocol.handshake`
     */
    public static final PackageMapping PACKET_PLAY_HANDSHAKE_PACKAGE_MAPPING = new PackageMapping("Packet Handshake Package", Maps.newHashMap());

    /**
     * The package mapping for the login packet package. In 1.17+, it is `net.minecraft.network.protocol.login`
     */
    public static final PackageMapping PACKET_LOGIN_PACKAGE_MAPPING = new PackageMapping("Packet Login Package", Maps.newHashMap());

    /**
     * The package mapping for the status packet package. In 1.17+, it is `net.minecraft.network.protocol.status`
     */
    public static final PackageMapping PACKET_STATUS_PACKAGE_MAPPING = new PackageMapping("Packet Status Package", Maps.newHashMap());

    /**
     * The package mapping for the protocol package. In 1.17+, it is `net.minecraft.network.protocol`
     */
    public static final PackageMapping PROTOCOL_PACKAGE_MAPPING = new PackageMapping("Protocol Package", Maps.newHashMap());

    /**
     * The package mapping for the block package. In 1.17+, it is `net.minecraft.world.level.block`
     */
    public static final PackageMapping BLOCK_PACKAGE_MAPPING = new PackageMapping("Block Package", Maps.newHashMap());

    /**
     * The package mapping for the nbt package. In 1.17+, it is `net.minecraft.nbt`
     */
    public static final PackageMapping NBT_PACKAGE_MAPPING = new PackageMapping("NBT Package", Maps.newHashMap());

    /**
     * The package mapping for the item package. In 1.17+, it is `net.minecraft.world.item`
     */
    public static final PackageMapping ITEM_PACKAGE_MAPPING = new PackageMapping("Item Package", Maps.newHashMap());

    /**
     * The package mapping for the server package. In 1.17+, it is `net.minecraft.server`
     */
    public static final PackageMapping SERVER_PACKAGE_MAPPING = new PackageMapping("Server Package", Maps.newHashMap());

    /**
     * The package mapping for the world physics package. In 1.17+, it is `net.minecraft.world.phys`
     */
    public static final PackageMapping WORLD_PHYSICS_PACKAGE_MAPPING = new PackageMapping("World Physics Package", Maps.newHashMap());

    /**
     * The package mapping for the server level package. In 1.17+, it is `net.minecraft.server.level`
     */
    public static final PackageMapping SERVER_LEVEL_PACKAGE_MAPPING = new PackageMapping("Server Level Package", Maps.newHashMap());

    /**
     * The package mapping for the server network package. In 1.17+, it is `net.minecraft.server.network`
     */
    public static final PackageMapping SERVER_NETWORK_PACKAGE_MAPPING = new PackageMapping("Network Package", Maps.newHashMap());

    /**
     * The package mapping for the core package. In 1.17+, it is `net.minecraft.core`
     */
    public static final PackageMapping CORE_PACKAGE_MAPPING = new PackageMapping("Core Package", Maps.newHashMap());

    /**
     * The package mapping for the datawatcher package (Data syncer I guess? Since it's in the syncer package). In 1.17+, it is `net.minecraft.network.syncher`
     */
    public static final PackageMapping DATAWATCHER_PACKAGE_MAPPING = new PackageMapping("DataWatcher Package", Maps.newHashMap());

    /**
     * The package mapping for the network package. In 1.17+, it is `net.minecraft.network`
     */
    public static final PackageMapping NETWORK_PACKAGE_MAPPING = new PackageMapping("Network Package", Maps.newHashMap());

    /**
     * The field mapping for a player's connection. In 1.8 - 1.14, it is `playerConnection`, in 1.17+ it is `b`
     */
    public static final FieldMapping<Object, NMSPlayerReflection> ENTITY_PLAYER_CONNECTION_MAPPING = new FieldMapping<>("EntityPlayer Connection", NMSPlayerReflection.class, Maps.newHashMap());

    /**
     * The package mapping for a the player package. In 1.17+, it is `net.minecraft.world.entity.player`
     */
    public static final PackageMapping WORLD_PLAYER_PACKAGE_MAPPING = new PackageMapping("World Player Package", Maps.newHashMap());

    /**
     * The field mapping for a world server's player list
     */
    public static final FieldMapping<List, NMSWorldServerReflection> WORLD_SERVER_PLAYER_LIST_MAPPING = new FieldMapping<>("WorldServer PlayerList", NMSWorldServerReflection.class, Maps.newHashMap());

    /**
     * The field mapping for a player connections network manager
     */
    public static final FieldMapping<Object, NMSPlayerConnectionReflection> PLAYER_CONNECTION_NETWORK_MANAGER_MAPPING = new FieldMapping<>("PlayerConnection NetworkManager", NMSPlayerConnectionReflection.class, Maps.newHashMap());

    /**
     * The package mapping for the world-level package. In 1.17+, it is `net.minecraft.world.level`
     */
    public static final PackageMapping WORLD_LEVEL_PACKAGE_MAPPING = new PackageMapping("World Level Package", Maps.newHashMap());

    /**
     * The method mapping to get the server from a world server. In 1.8 - 1.18, it is `getMinecraftServer`, in 1.18+ it is `n`
     */
    public static final ArgumentlessMethodMapping<Object, NMSWorldServerReflection> WORLD_SERVER_GET_SERVER_MAPPING = new ArgumentlessMethodMapping<>("WorldServer getServer", NMSWorldServerReflection.class, Maps.newHashMap());

    /**
     * The method mapping to get an entity's axis aligned bounding box. In 1.8 - 1.18, it is `getBoundingBox`, in 1.18+ it is `cw`
     */
    public static final ArgumentlessMethodMapping<Object, NMSEntityReflection> ENTITY_GET_BOUNDING_BOX_MAPPING = new ArgumentlessMethodMapping<>("Entity getBoundingBox", NMSEntityReflection.class, Maps.newHashMap());

    /**
     * The method mapping to get an entity's data watcher. In 1.8 - 1.18, it is `getDataWatcher`, in 1.18+ it is `ai`
     */
    public static final ArgumentlessMethodMapping<Object, NMSEntityReflection> ENTITY_GET_DATA_WATCHER_MAPPING = new ArgumentlessMethodMapping<>("Entity getDataWatcher", NMSEntityReflection.class, Maps.newHashMap());

    /**
     * The method mapping to set an entry in an entity's data watcher. In 1.9 - 1.18, it is `set`, in 1.18+ it is `b` This method does not exist prior to 1.9
     */
    @Nullable
    public static ArgumentMethodMapping<Void, NMSDataWatcherReflection> ENTITY_DATA_WATCHER_SET_ENTRY_MAPPING;

    /**
     * The method mapping to send a packet to a player. In 1.8 - 1.18, it is `sendPacket`, in 1.18+ it is `a`
     */
    public static final ArgumentMethodMapping<Object, NMSPlayerConnectionReflection> PLAYER_CONNECTION_SEND_PACKET_MAPPING = new ArgumentMethodMapping<>("PlayerConnection sendPacket", NMSPlayerConnectionReflection.class, Maps.newHashMap());

    /**
     * The method mapping to add an entity to a world server. In 1.8 - 1.18, it is `addEntity`, in 1.18+ it is `c`
     */
    public static final ArgumentMethodMapping<Object, NMSWorldServerReflection> WORLD_SERVER_ADD_ENTITY_MAPPING = new ArgumentMethodMapping<>("WorldServer addEntity", NMSWorldServerReflection.class, Maps.newHashMap());

    /**
     * The method mapping to get a team's player list. In 1.8 - 1.18, it is `getPlayerNameSet`, in 1.18+ it is `b`
     */
    public static final ArgumentlessMethodMapping<Collection<String>, NMSScoreboardTeamReflection> TEAM_GET_PLAYER_LIST_MAPPING = new ArgumentlessMethodMapping<>("ScoreboardTeam getPlayerNameSet", NMSScoreboardTeamReflection.class, Maps.newHashMap());

    /**
     * The method mapping to set an entity's location. In 1.8 - 1.18, it is `setLocation`, in 1.18+ it is `b`
     */
    public static final ArgumentMethodMapping<Void, NMSEntityReflection> ENTITY_SET_LOCATION_MAPPING = new ArgumentMethodMapping<>("Entity setLocation", NMSEntityReflection.class, Maps.newHashMap());

    /**
     * The class name mapping for the chat packet. In 1.8 - 1.19, it is `PacketPlayOutChat`, in 1.19+ it is `ClientboundSystemChatPacket`
     */
    public static final ClassNameMapping CHAT_PACKET_CLASS_MAPPING = new ClassNameMapping("PacketPlayOutChat", PACKET_PLAY_PACKAGE_MAPPING, Maps.newHashMap());

    /**
     * The method mapping to get an itemstack's nbt tag. In 1.8 - 1.18, it is `getTag`, from 1.18 - 1.19, it is `t`, afterwards it is `u`
     */
    public static final ArgumentlessMethodMapping<Object, NMSItemStackReflection> ITEM_STACK_GET_NBT_TAG_MAPPING = new ArgumentlessMethodMapping<>("ItemStack getTag", NMSItemStackReflection.class, Maps.newHashMap());

    /**
     * The method mapping to set an itemstack's nbt tag. In 1.8 - 1.18, it is `setTag`, in 1.18+, it is `c`
     */
    public static final ArgumentMethodMapping<Void, NMSItemStackReflection> ITEM_STACK_SET_NBT_TAG_MAPPING = new ArgumentMethodMapping<>("ItemStack setTag", NMSItemStackReflection.class, Maps.newHashMap());

    public static final ArgumentMethodMapping<Void, NMSScoreboardTeamReflection> TEAM_SET_PREFIX_MAPPING = new ArgumentMethodMapping<>("ScoreboardTeam setPrefix", NMSScoreboardTeamReflection.class, Maps.newHashMap());

    /**
     * Loads all mappings once the server version has been found. This is called in the {@link ReflectionApi#init(JavaPlugin)} method.
     *
     * @throws MappingsException Throws if the mappings failed to load.
     * @see ReflectionApi#init(JavaPlugin)
     */
    public static void loadMappings() throws MappingsException {

        BASE_PACKAGE.addMapping(VersionRange.ANY, "");

        ENTITY_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        ENTITY_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "world.entity");

        PACKET_PLAY_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        PACKET_PLAY_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "network.protocol.game");

        PACKET_PLAY_HANDSHAKE_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        PACKET_PLAY_HANDSHAKE_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "network.protocol.handshake");

        PACKET_LOGIN_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        PACKET_LOGIN_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "network.protocol.login");

        PACKET_STATUS_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        PACKET_STATUS_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "network.protocol.status");

        PROTOCOL_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        PROTOCOL_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "network.protocol");

        CHAT_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        CHAT_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "network.chat");

        BLOCK_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        BLOCK_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "world.level.block");

        ITEM_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        ITEM_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "world.item");

        NBT_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        NBT_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "nbt");

        SCOREBOARD_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        SCOREBOARD_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "world.scores");

        SERVER_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        SERVER_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "server");

        WORLD_PHYSICS_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        WORLD_PHYSICS_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "world.phys");

        SERVER_LEVEL_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        SERVER_LEVEL_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "server.level");

        SERVER_NETWORK_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        SERVER_NETWORK_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "server.network");

        CORE_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        CORE_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "core");

        DATAWATCHER_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        DATAWATCHER_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "network.syncher");

        NETWORK_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        NETWORK_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "network");

        WORLD_PLAYER_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        WORLD_PLAYER_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "world.entity.player");

        CHAT_SERIALIZER_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_8_1), "ChatSerializer");
        CHAT_SERIALIZER_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8_1, Version.MAX), "IChatBaseComponent$ChatSerializer");

        NETWORK_MANAGER_CHANNEL_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_8_1), "i");
        NETWORK_MANAGER_CHANNEL_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8_1, ReflectionApi.v1_17), "channel");
        NETWORK_MANAGER_CHANNEL_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, ReflectionApi.v1_19), "k");
        NETWORK_MANAGER_CHANNEL_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_19, Version.MAX), "m");

        ENUM_PLAYER_ACTION_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_8_1), "EnumPlayerInfoAction");
        ENUM_PLAYER_ACTION_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8_1, ReflectionApi.v1_17), "PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
        ENUM_PLAYER_ACTION_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "PacketPlayOutPlayerInfo$EnumPlayerInfoAction");

        GET_NAMETAG_VISIBILITY_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_8_1), "i");
        GET_NAMETAG_VISIBILITY_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8_1, ReflectionApi.v1_18), "getNameTagVisibility");
        GET_NAMETAG_VISIBILITY_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, Version.MAX), "j");

        TITLE_PACKET_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "PacketPlayOutTitle");
        TITLE_PACKET_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "ClientboundSetTitleTextPacket");

        SUBTITLE_PACKET_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "PacketPlayOutTitle");
        SUBTITLE_PACKET_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "ClientboundSetSubtitleTextPacket");

        TITLE_CLEAR_PACKET_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "PacketPlayOutTitle");
        TITLE_CLEAR_PACKET_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "ClientboundClearTitlesPacket");

        TITLE_TIMINGS_PACKET_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "PacketPlayOutTitle");
        TITLE_TIMINGS_PACKET_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "ClientboundSetTitlesAnimationPacket");

        ENUM_TITLE_ACTION_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_8_1), "EnumTitleAction");
        ENUM_TITLE_ACTION_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8_1, ReflectionApi.v1_17), "PacketPlayOutTitle$EnumTitleAction");

        NAMETAG_VISIBILTY_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_8_1), "EnumNameTagVisibility");
        NAMETAG_VISIBILTY_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8_1, Version.MAX), "ScoreboardTeamBase$EnumNameTagVisibility");

        SET_NAMETAG_VISIBILITY_MAPPING.setArgType(NAMETAG_VISIBILTY_CLASS_MAPPING.getNMSClassMapping());
        SET_NAMETAG_VISIBILITY_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_8_1), "a");
        SET_NAMETAG_VISIBILITY_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8_1, ReflectionApi.v1_18), "setNameTagVisibility");
        SET_NAMETAG_VISIBILITY_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, Version.MAX), "a");

        ENTITY_LOOK_PACKET_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_8_1), "PacketPlayOutEntityLook");
        ENTITY_LOOK_PACKET_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8_1, Version.MAX), "PacketPlayOutEntity$PacketPlayOutEntityLook");

        REL_ENTITY_MOVE_LOOK_PACKET_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_8_1), "PacketPlayOutRelEntityMoveLook");
        REL_ENTITY_MOVE_LOOK_PACKET_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8_1, Version.MAX), "PacketPlayOutEntity$PacketPlayOutRelEntityMoveLook");

        ENUM_ENTITY_USE_ACTION_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_8_1), "EnumEntityUseAction");
        ENUM_ENTITY_USE_ACTION_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8_1, Version.MAX), "PacketPlayInUseEntity$EnumEntityUseAction");

        DATA_WATCHER_PLAYER_SKIN_LAYER_INDEX.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_9), 10);
        DATA_WATCHER_PLAYER_SKIN_LAYER_INDEX.addMapping(new VersionRange(ReflectionApi.v1_9, ReflectionApi.v1_10), 12);
        DATA_WATCHER_PLAYER_SKIN_LAYER_INDEX.addMapping(new VersionRange(ReflectionApi.v1_10, ReflectionApi.v1_14), 13);
        DATA_WATCHER_PLAYER_SKIN_LAYER_INDEX.addMapping(new VersionRange(ReflectionApi.v1_14, ReflectionApi.v1_15), 15);
        DATA_WATCHER_PLAYER_SKIN_LAYER_INDEX.addMapping(new VersionRange(ReflectionApi.v1_15, ReflectionApi.v1_17), 16);
        DATA_WATCHER_PLAYER_SKIN_LAYER_INDEX.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), 17);

        PACKET_ENTITY_USE_GET_ACTION_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_13), "a");
        PACKET_ENTITY_USE_GET_ACTION_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_13, ReflectionApi.v1_17), "b");

        PACKET_ENTITY_USE_GET_HAND_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_9, ReflectionApi.v1_13), "b");
        PACKET_ENTITY_USE_GET_HAND_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_13, ReflectionApi.v1_17), "c");

        WORLD_SERVER_ENTITY_LIST_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_14), "entityList");
        WORLD_SERVER_ENTITY_LIST_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_14, ReflectionApi.v1_16), "globalEntityList");
        WORLD_SERVER_ENTITY_LIST_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_16, ReflectionApi.v1_17), "entitiesByUUID");

        WORLD_SERVER_ENTITY_ITERABLE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, ReflectionApi.v1_19), "C");
        WORLD_SERVER_ENTITY_ITERABLE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_19, Version.MAX), "z");

        // Unsure if I need these. Must go through versions again and re-test everything

//        DATAWATCHER_ITEMS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_10), "c");
//        DATAWATCHER_ITEMS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_10, ReflectionApi.v1_14), "d");
//        DATAWATCHER_ITEMS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_14, Version.MAX), "entries");

        GET_TEAM_COLOR_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_9), "l");
        GET_TEAM_COLOR_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_9, new Version(12, 0)), "m");
        GET_TEAM_COLOR_MAPPING.addMapping(new VersionRange(new Version(12, 0), ReflectionApi.v1_18), "getColor");
        GET_TEAM_COLOR_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, Version.MAX), "n");

        ENTITY_PLAYER_CONNECTION_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "playerConnection");
        ENTITY_PLAYER_CONNECTION_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "b");

        WORLD_SERVER_PLAYER_LIST_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "players");
        WORLD_SERVER_PLAYER_LIST_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, ReflectionApi.v1_18), "B");
        WORLD_SERVER_PLAYER_LIST_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, Version.MAX), "K");

        PLAYER_CONNECTION_NETWORK_MANAGER_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "networkManager");
        PLAYER_CONNECTION_NETWORK_MANAGER_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, ReflectionApi.v1_19), "a");
        PLAYER_CONNECTION_NETWORK_MANAGER_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_19, Version.MAX), "b");

        WORLD_LEVEL_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_17), "");
        WORLD_LEVEL_PACKAGE_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_17, Version.MAX), "world.level");

        WORLD_SERVER_GET_SERVER_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_18), "getMinecraftServer");
        WORLD_SERVER_GET_SERVER_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, Version.MAX), "n");

        ENTITY_GET_BOUNDING_BOX_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_18), "getBoundingBox");
        ENTITY_GET_BOUNDING_BOX_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, ReflectionApi.v1_19), "cw");
        ENTITY_GET_BOUNDING_BOX_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_19, Version.MAX), "cz");

        ENTITY_GET_DATA_WATCHER_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_18), "getDataWatcher");
        ENTITY_GET_DATA_WATCHER_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, Version.MAX), "ai");

        if (ReflectionApi.version.isOlder(ReflectionApi.v1_9)) {
            ENTITY_DATA_WATCHER_SET_ENTRY_MAPPING = null;
        } else {
            ENTITY_DATA_WATCHER_SET_ENTRY_MAPPING = new ArgumentMethodMapping<>("Entity DataWatcher setEntry", NMSDataWatcherReflection.class, Maps.newHashMap(), new Class[]{ReflectionApi.getNMSClass(Mappings.DATAWATCHER_PACKAGE_MAPPING, "DataWatcherObject"), Object.class});
            ENTITY_DATA_WATCHER_SET_ENTRY_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_9, ReflectionApi.v1_18), "set");
            ENTITY_DATA_WATCHER_SET_ENTRY_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, Version.MAX), "b");
        }

        PLAYER_CONNECTION_SEND_PACKET_MAPPING.setArgType(ReflectionApi.getNMSClass(Mappings.PROTOCOL_PACKAGE_MAPPING, "Packet"));
        PLAYER_CONNECTION_SEND_PACKET_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_18), "sendPacket");
        PLAYER_CONNECTION_SEND_PACKET_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, Version.MAX), "a");

        WORLD_SERVER_ADD_ENTITY_MAPPING.setArgType(NMSEntityReflection.staticClass);
        WORLD_SERVER_ADD_ENTITY_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_18), "addEntity");
        WORLD_SERVER_ADD_ENTITY_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, Version.MAX), "c");

        TEAM_GET_PLAYER_LIST_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_18), "getPlayerNameSet");
        TEAM_GET_PLAYER_LIST_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, Version.MAX), "g");

        ENTITY_SET_LOCATION_MAPPING.setArgType(double.class, double.class, double.class, float.class, float.class);
        ENTITY_SET_LOCATION_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_18), "setLocation");
        ENTITY_SET_LOCATION_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, Version.MAX), "b");

        CHAT_PACKET_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_19), "PacketPlayOutChat");
        CHAT_PACKET_CLASS_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_19, Version.MAX), "ClientboundSystemChatPacket");

        ITEM_STACK_GET_NBT_TAG_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_18), "getTag");
        ITEM_STACK_GET_NBT_TAG_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, ReflectionApi.v1_19), "t");
        ITEM_STACK_GET_NBT_TAG_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_19, Version.MAX), "u");

        ITEM_STACK_SET_NBT_TAG_MAPPING.setArgType(NMSNBTTagCompoundReflection.staticClass);
        ITEM_STACK_SET_NBT_TAG_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_18), "setTag");
        ITEM_STACK_SET_NBT_TAG_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, Version.MAX), "c");

        if (ReflectionApi.version.isOlder(ReflectionApi.v1_13)) {
            TEAM_SET_PREFIX_MAPPING.setArgType(String.class);
        } else {
            TEAM_SET_PREFIX_MAPPING.setArgType(ReflectionApi.getNMSClass(Mappings.CHAT_PACKAGE_MAPPING, "IChatBaseComponent"));
        }
        TEAM_SET_PREFIX_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_8, ReflectionApi.v1_18), "setPrefix");
        TEAM_SET_PREFIX_MAPPING.addMapping(new VersionRange(ReflectionApi.v1_18, Version.MAX), "b");

    }

}
