package gg.frog.mc.base.listener;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import gg.frog.mc.base.utils.data.PlayerData;
import gg.frog.mc.base.PluginMain;

public class BaseListener implements Listener {

	private PluginMain pm;

	public BaseListener(PluginMain pm) {
		this.pm = pm;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerLoginEvent event) {
		String uuid = event.getPlayer().getUniqueId().toString();
		OfflinePlayer p = (OfflinePlayer) event.getPlayer();
		PlayerData.getPLAYER_UUID_MAP().put(event.getPlayer().getName(), uuid);
		PlayerData.getOFFLINE_PLAYER_MAP().put(event.getPlayer().getName(), p);
		pm.getServer().getConsoleSender().sendMessage(PluginMain.PLUGIN_PREFIX + "UUID of player " + event.getPlayer().getName() + " is " + uuid);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onKick(PlayerKickEvent event) {
		PlayerData.getPLAYER_UUID_MAP().remove(event.getPlayer().getName());
		PlayerData.getOFFLINE_PLAYER_MAP().remove(event.getPlayer().getName());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent event) {
		PlayerData.getPLAYER_UUID_MAP().remove(event.getPlayer().getName());
		PlayerData.getOFFLINE_PLAYER_MAP().remove(event.getPlayer().getName());
	}
}
