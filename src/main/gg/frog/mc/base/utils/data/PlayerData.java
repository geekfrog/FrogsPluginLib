package gg.frog.mc.base.utils.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import gg.frog.mc.base.utils.PluginBase;

public class PlayerData {

	private static PluginBase pb = null;

	private static Map<String, String> PLAYER_UUID_MAP = new ConcurrentHashMap<>();
	private static Map<String, OfflinePlayer> OFFLINE_PLAYER_MAP = new ConcurrentHashMap<>();

	private PlayerData() {
	}

	public static void setPluginMain(PluginBase plugin) {
		pb = plugin;
	}

	public static Map<String, OfflinePlayer> getOFFLINE_PLAYER_MAP() {
		return OFFLINE_PLAYER_MAP;
	}

	public static OfflinePlayer getOfflinePlayer(String name) {
		if (name != null) {
			OfflinePlayer p = OFFLINE_PLAYER_MAP.get(name);
			if (p != null)
				return p;
			if (pb != null)
				for (OfflinePlayer p2 : pb.getServer().getOfflinePlayers()) {
					if (p2 == null)
						continue;
					if (p2.getName().equalsIgnoreCase(name)) {
						OFFLINE_PLAYER_MAP.put(name, p2);
						return p2;
					}
				}
		}
		return null;
	}

	public static String getPlayerUUIDByName(Player p) {
		String uuid = getPlayerUUIDByName(p.getName());
		if (uuid == null) {
			return p.getUniqueId().toString();
		}
		return uuid;
	}

	public static String getPlayerUUIDByName(String name) {
		if (name != null) {
			String uuid = PLAYER_UUID_MAP.get(name);
			if (uuid != null) {
				return uuid;
			} else {
				if (pb != null)
					for (OfflinePlayer p : pb.getServer().getOfflinePlayers()) {
						if (p == null)
							continue;
						if (p.getName().equalsIgnoreCase(name)) {
							uuid = p.getUniqueId().toString();
							PLAYER_UUID_MAP.put(name, uuid);
							return uuid;
						}
					}
			}
		}
		return null;
	}
}
