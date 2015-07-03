package me.Mark.MT;

import java.util.List;
import java.util.Set;

public class Script {

	private int currentCommand = 0;
	private Command[] commands;

	public Script(String[] commandS) {
		commands = new Command[commandS.length];
		for (int i = 0; i < commandS.length; i++)
			commands[i] = new Command(commandS[i]);
	}

	public Script(Command[] commands) {
		this.commands = commands;
	}

	public Command getNextCommand() {
		int cur = currentCommand;
		currentCommand++;
		if (currentCommand > commands.length - 1)
			currentCommand = 0;
		return commands[cur];
	}

	public int getLength() {
		return commands.length;
	}

	/*
	 * public Script(List<String> commands) { String[] cmds = new
	 * String[commands.size() - 1]; for (int i = 0; i < commands.size(); i++)
	 * cmds[i] = commands.get(i); }
	 */

	public static Script getFromConfig(String name) {
		List<String> cmdsS = MT.inst.config.getStringList("script." + name);
		Command[] cmds = new Command[cmdsS.size()];
		for (int i = 0; i < cmdsS.size(); i++)
			cmds[i] = new Command(cmdsS.get(i));
		return new Script(cmds);
	}

	public static Set<String> getScripts() {
		return  MT.inst.config.getConfigurationSection("script").getKeys(false);
	}
}
