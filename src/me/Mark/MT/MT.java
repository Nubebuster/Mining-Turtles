package me.Mark.MT;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Mark.MT.Commands.TurtleCMD;
import me.Mark.MT.Listeners.PlayerListener;

public class MT extends JavaPlugin {

	/**
	 * TODO check block data in PLACE
	 * TODO play break particles
	 * TODO /reloadscripts Realoading
	 */
	public static MT inst;
	FileConfiguration config;

	@Override
	public void onEnable() {
		inst = this;
		configs();
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerListener(), this);

		getCommand("turtle").setExecutor(new TurtleCMD());

		YamlConfiguration c = new YamlConfiguration();
		try {
			File f = new File(getDataFolder() + File.separator + "turtles.yml");
			if (!f.exists())
				f.createNewFile();
			c.load(f);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		for (String s : c.getKeys(false)) {
			@SuppressWarnings("deprecation")
			Turtle t = new Turtle(s, Material.getMaterial(c.getInt(s + ".material")),
					new Location(Bukkit.getWorld(c.getString(s + ".location.world")), c.getInt(s + ".location.x"),
							c.getInt(s + ".location.y"), c.getInt(s + ".location.z")),
					c.getString(s + ".owner"));
			@SuppressWarnings("unchecked")
			ItemStack[] content = ((List<ItemStack>) c.get(s + ".inv")).toArray(new ItemStack[0]);
			t.getInventory().setContents(content);
			Turtle.turtles.add(t);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player!");
			return false;
		}
		if (label.equalsIgnoreCase("turtlerod")) {
			ItemStack rod = new ItemStack(Material.BLAZE_ROD);
			ItemMeta im = rod.getItemMeta();
			im.setDisplayName("§aCreate a Turtle");
			rod.setItemMeta(im);
			((Player) sender).getInventory().addItem(rod);
			((Player) sender).getInventory().addItem(new ItemStack(Material.SPONGE));
			sender.sendMessage(ChatColor.GREEN + "Click with this rod on a sponge to create a turtle.");
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		YamlConfiguration c = new YamlConfiguration();
		for (Turtle t : Turtle.turtles) {
			if (t.isRunning()) {
				t.stop();
				if (t.getOwner() != null)
					t.getOwner().sendMessage(ChatColor.RED + "Server reloading, stopping turtle.");
			}
			String path = t.getName();
			c.set(path + ".owner", t.getOwnerName());
			c.set(path + ".location.x", t.getLocation().getBlockX());
			c.set(path + ".location.y", t.getLocation().getBlockY());
			c.set(path + ".location.z", t.getLocation().getBlockZ());
			c.set(path + ".location.world", t.getLocation().getWorld().getName());
			c.set(path + ".material", t.getMaterial().getId());
			c.set(path + ".inv", t.getInventory().getContents());
		}
		try {
			c.save(new File(getDataFolder() + File.separator + "turtles.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void configs() {
		if (!getDataFolder().exists())
			getDataFolder().mkdir();
		File file = new File(getDataFolder() + File.separator + "config.yml");
		config = getConfig();
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			config.options().copyDefaults(true);
			saveConfig();
		}
		if (config.getBoolean("rewriteconfig"))
			try {
				file.delete();
				file.createNewFile();
				config.options().copyDefaults(true);
				saveConfig();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}