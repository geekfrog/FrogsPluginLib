package gg.frog.mc.base.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import gg.frog.mc.base.PluginMain;
import gg.frog.mc.base.utils.data.PlayerData;

public class BaseListener implements Listener {

	private PluginMain pm;

	public BaseListener(PluginMain pm) {
		this.pm = pm;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		PlayerData.getOFFLINE_PLAYER_MAP().remove(event.getPlayer().getName());
	}

	@EventHandler
	public void onKick(PlayerKickEvent event) {
		PlayerData.getOFFLINE_PLAYER_MAP().remove(event.getPlayer().getName());
	}
}
