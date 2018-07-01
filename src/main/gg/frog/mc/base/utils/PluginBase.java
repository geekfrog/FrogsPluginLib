package gg.frog.mc.base.utils;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class PluginBase extends JavaPlugin {
	public static final Logger LOG = Logger.getLogger("Minecraft");
	public String PLUGIN_NAME;
	public String PLUGIN_VERSION;
	public String PLUGIN_NAME_LOWER_CASE;
}
