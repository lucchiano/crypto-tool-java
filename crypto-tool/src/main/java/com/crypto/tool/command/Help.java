package com.crypto.tool.command;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.naming.directory.InvalidAttributesException;

import com.crypto.tool.CryptoTool;
import com.crypto.tool.ICommand;

public class Help implements ICommand {

	public void init(ArrayList<String> arguments) throws InvalidAttributesException {

	}

	public void execute() throws Exception {
		Enumeration<String> commands = CryptoTool.getInstance().getCommandList();
		System.out.println("Available commands:");
		while (commands.hasMoreElements()) {
			String command = commands.nextElement();
			System.out.println(" - " + command);
		}
	}

	public void help() {

	}
}
