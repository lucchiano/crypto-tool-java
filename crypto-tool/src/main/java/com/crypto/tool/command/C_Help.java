package com.crypto.tool.command;

import java.util.Enumeration;
import java.util.Hashtable;

import com.crypto.tool.CommandControl;
import com.crypto.tool.ICommand;

public class C_Help implements ICommand {

	public void execute(Hashtable<String, String> arguments) throws Exception {
		Enumeration<String> commands = CommandControl.getInstance().getCommandList();
		System.out.println("Available commands:");
		while (commands.hasMoreElements()) {
			String command = commands.nextElement();
			System.out.println(" - " + command);
		}
	}

	public void help() {

	}
}
