package me.Mark.MT.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.Mark.MT.MT;

public class ReloadCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("turtles.reload")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission.");
			return false;
		}
		MT.inst.reloadConfig();
		MT.inst.config = MT.inst.getConfig();
		sender.sendMessage(ChatColor.GREEN + "Reloaded the scripts!");
		return false;
	}
}
