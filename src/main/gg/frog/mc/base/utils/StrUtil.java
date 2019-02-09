package gg.frog.mc.base.utils;

import java.text.MessageFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.bukkit.entity.Player;

import gg.frog.mc.base.PluginMain;
import me.clip.placeholderapi.PlaceholderAPI;

public class StrUtil {

	private static final String dfs = "yyyy/MM/dd HH:mm:ss";

	public static String messageFormat(String src, Object... args) {
		return MessageFormat.format(src, args).replaceAll("&", "§").replaceAll("\\\\n", "\n");
	}

	public static String messageFormat(Player player, String src, Object... args) {
		String message = MessageFormat.format(src, args).replaceAll("&", "§").replaceAll("\\\\n", "\n").replaceAll("%player%", player.getPlayer().getDisplayName()).replaceAll("%player_name%", player.getPlayer().getName());
		if (PluginMain.enabledPlaceholder) {
			message = PlaceholderAPI.setPlaceholders(player, message, PlaceholderAPI.getPlaceholderPattern());
		}
		return message;
	}

	public static String timestampToString(long time) {
		return DateFormatUtils.format(new Date(time), dfs);
	}

	public static String dateToString(Date d) {
		return DateFormatUtils.format(d, dfs);
	}

	public static String nowTimeString() {
		return DateFormatUtils.format(new Date(), dfs);
	}

}
