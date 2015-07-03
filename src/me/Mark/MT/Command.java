package me.Mark.MT;

public class Command {

	private String[] args;
	private String label;

	public Command(String commandS) {
		String[] argss = commandS.split(" ");
		String label = argss[0];
		String[] args = new String[argss.length - 1];
		for (int i = 0; i < argss.length; i++)
			if (i > 0)
				args[i - 1] = argss[i];
		this.label = label;
		this.args = args;
	}

	public String getLabel() {
		return label;
	}

	public String[] getArgs() {
		return args;
	}
}
