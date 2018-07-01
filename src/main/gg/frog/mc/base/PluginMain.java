package gg.frog.mc.base;

import java.io.File;
import java.io.InputStream;
import java.util.Locale;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;

import gg.frog.mc.base.listener.BaseListener;
import gg.frog.mc.base.utils.FileUtil;
import gg.frog.mc.base.utils.StrUtil;
import gg.frog.mc.base.utils.UpdateCheck;
import gg.frog.mc.base.utils.data.PlayerData;
import gg.frog.mc.base.utils.FileUtil.FindFilesDo;
import gg.frog.mc.base.utils.PluginBase;

public class PluginMain extends PluginBase {

	public static final String DEPEND_PLUGIN = "";
	public static String PLUGIN_PREFIX;

	private PluginMain pm = null;
	public static boolean enabledPlaceholder;

	public PluginMain() {
		PLUGIN_NAME = getDescription().getName();
		PLUGIN_VERSION = getDescription().getVersion();
		PLUGIN_NAME_LOWER_CASE = PLUGIN_NAME.toLowerCase(Locale.ENGLISH);
		PLUGIN_PREFIX = "§4[§b" + PLUGIN_NAME + "§4] " + "§r";
	}

	@Override
	public void onLoad() {
		pm = this;
		if (getServer().getPluginManager().getPlugin("SQLibrary") == null) {
			new File(getDataFolder().getAbsoluteFile() + "/lib/").mkdirs();
			loadLibFromJar("SQLibrary-7.1.jar");
		}
	}

	@Override
	public void onEnable() {
		getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PLUGIN_PREFIX + "==============================="));
		getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PLUGIN_PREFIX));
		getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PLUGIN_PREFIX + "    " + PLUGIN_NAME + " v" + PLUGIN_VERSION));
		getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PLUGIN_PREFIX + "    author：GeekFrog QQ：324747460"));
		getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PLUGIN_PREFIX + "    https://github.com/geekfrog/FrogsPluginLib/ "));
		getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PLUGIN_PREFIX));
		getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PLUGIN_PREFIX + "==============================="));
		if (!checkPluginDepends()) {
			getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PLUGIN_PREFIX + "§4Startup failure!"));
			getServer().getPluginManager().disablePlugin(pm);
		} else {
			PlayerData.setPluginMain(pm);
			registerListeners();
			registerCommands();
			getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PLUGIN_PREFIX + "§2Startup successful!"));
			getServer().getScheduler().runTask(pm, new Runnable() {
				public void run() {
					try {
						new Metrics(pm);
					} catch (Exception e) {
						e.printStackTrace();
					}
					getServer().getScheduler().runTaskAsynchronously(pm, new UpdateCheck(pm, "https://raw.githubusercontent.com/geekfrog/FrogsPluginLib/master/src/resources/plugin.yml"));
				}
			});
		}
	}

	@Override
	public void onDisable() {
		getServer().getServicesManager().unregisterAll(pm);
		Bukkit.getScheduler().cancelTasks(pm);
	}

	/**
	 * 注册监听器 <br/>
	 * 这里可以注册多个
	 */
	private void registerListeners() {
		pm.getServer().getPluginManager().registerEvents(new BaseListener(), pm);
	}

	/**
	 * 注册命令 <br/>
	 * 这里可以注册多个，一般注册一个就够用
	 */
	private void registerCommands() {

	}

	private boolean loadLibFromJar(String fileName) {
		try {
			File filePath = new File(getDataFolder().getAbsoluteFile() + "/lib/", fileName);
			if (!filePath.exists()) {
				FileUtil.findFilesFromJar(new FindFilesDo() {
					public void process(String fileName, InputStream is) {
						File f = new File(pm.getDataFolder(), fileName);
						FileUtil.writeFile(f, is);
					}

					public boolean isProcess(String tempFileName) {
						if (tempFileName.matches("lib/" + fileName)) {
							if (!filePath.exists()) {
								return true;
							}
						}
						return false;
					}
				}, this.getClass());
			}
			this.getServer().getPluginManager().loadPlugin(filePath);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean checkPluginDepends() {
		boolean needDepend = false;
		if (DEPEND_PLUGIN.length() > 0) {
			for (String name : DEPEND_PLUGIN.split(",")) {
				if (!getServer().getPluginManager().isPluginEnabled(name)) {
					getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PLUGIN_PREFIX + "§4Need depend plugins : " + name + "."));
					needDepend = true;
				}
			}

			if (needDepend) {
				return false;
			}
		}
		enabledPlaceholder = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
		if (!enabledPlaceholder) {
			getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PLUGIN_PREFIX + "§ePlaceholder is not installed or not enabled."));
			getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PLUGIN_PREFIX + "§eSome func will be disabled."));
		}
		return true;
	}
}
