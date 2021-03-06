package gg.frog.mc.base.utils;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.base.Charsets;

public class UpdateCheck implements Runnable {

	private PluginBase pb;
	private String pluginInfoUrl;

	public UpdateCheck(PluginBase pb, String pluginInfoUrl) {
		this.pb = pb;
		this.pluginInfoUrl = pluginInfoUrl;
	}

	@Override
	public void run() {
		try {
			URL url = new URL(pluginInfoUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			connection.setRequestMethod("GET");
			YamlConfiguration tempConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8));
			String version = tempConfig.getString("version", "Unknown version");
			if (!pb.PLUGIN_VERSION.equals(version)) {
				pb.getServer().getConsoleSender().sendMessage(StrUtil.messageFormat("§b[§4" + pb.PLUGIN_NAME + "§b] " + "§eFound a new version ''{0}''. (Yours: ''{1}'')", version, pb.PLUGIN_VERSION));
			} else {
				pb.getServer().getConsoleSender().sendMessage(StrUtil.messageFormat("§b[§4" + pb.PLUGIN_NAME + "§b] " + "§2No new version available."));
			}
		} catch (Exception e) {
			pb.getServer().getConsoleSender().sendMessage(StrUtil.messageFormat("§b[§4" + pb.PLUGIN_NAME + "§b] " + "§4Can't check new version."));
		}
	}
}
