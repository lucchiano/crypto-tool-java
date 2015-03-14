package com.crypto.tool;

import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import org.reflections.Reflections;

public class CommandControl {
	private static final String COMMAND_PREFIX = "C_";
	private String PACKAGE_PREFIX = "com.crypto.tool.command";
	private static CommandControl instance = null;
	private Hashtable<String, Constructor<? extends ICommand>> commandList;

	private CommandControl() {
		commandList = new Hashtable<String, Constructor<? extends ICommand>>();
		Reflections reflections = new Reflections(PACKAGE_PREFIX);
		Set<Class<? extends ICommand>> commands = reflections.getSubTypesOf(ICommand.class);
		for (Class<? extends ICommand> commandClass : commands) {
			String commandId = commandClass.getSimpleName();
			if (commandId.startsWith(COMMAND_PREFIX)) {
				commandId = commandId.replaceFirst(COMMAND_PREFIX, "");
				try {
					Constructor<? extends ICommand> constructor = commandClass.getConstructor();
					commandList.put(commandId.toLowerCase(), constructor);
				} catch (Exception e) {
					System.err.println("Failed to load command class " + commandClass.getCanonicalName() + ": " + e.getMessage());
				}

			}
		}
	}

	public Enumeration<String> getCommandList() {
		return commandList.keys();
	}

	public static synchronized CommandControl getInstance() {
		if (instance == null)
			instance = new CommandControl();
		return instance;
	}

	public ICommand getCommand(String command) throws Exception {
		if (command == null)
			return null;

		Constructor<? extends ICommand> constructor = commandList.get(command);
		if (constructor == null)
			return null;
		ICommand commandInstance = constructor.newInstance();
		return commandInstance;
	}

}
