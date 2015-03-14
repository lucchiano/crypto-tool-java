package com.crypto.tool.command;

import java.util.ArrayList;

import javax.naming.directory.InvalidAttributesException;

import com.crypto.tool.ICommand;

public class Exit implements ICommand {

	public void init(ArrayList<String> arguments) throws InvalidAttributesException {

	}

	public void execute() throws Exception {
		System.out.println("Bye!");
		System.exit(0);
	}

	public void help() {

	}

}
