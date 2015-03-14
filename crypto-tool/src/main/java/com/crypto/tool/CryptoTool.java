package com.crypto.tool;

import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import org.reflections.Reflections;

public class CryptoTool {
	private String PACKAGE_PREFIX = "com.crypto.tool.command";
	private static CryptoTool instance = null;
	private Hashtable<String, ICommand> commandList;

	private CryptoTool() {
		commandList = new Hashtable<String, ICommand>();
		Reflections reflections = new Reflections(PACKAGE_PREFIX);
		Set<Class<? extends ICommand>> commands = reflections.getSubTypesOf(ICommand.class);
		for (Class<? extends ICommand> commandClass : commands) {
			String commandId = commandClass.getSimpleName().toLowerCase();
			try {
				Constructor<? extends ICommand> constructor = commandClass.getConstructor();
				ICommand commandInstance = constructor.newInstance();
				commandList.put(commandId, commandInstance);
			} catch (Exception e) {
				System.err.println("Failed to load command class " + commandClass.getCanonicalName() + ": " + e.getMessage());
			}
		}
	}

	public Enumeration<String> getCommandList() {
		return commandList.keys();
	}

	public static synchronized CryptoTool getInstance() {
		if (instance == null)
			instance = new CryptoTool();
		return instance;
	}

	public ICommand getCommand(String command) {
		if (command == null)
			command = "";
		return commandList.get(command);
	}

}
