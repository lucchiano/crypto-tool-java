package com.crypto.tool.command;

import java.util.Hashtable;

import com.crypto.tool.ICommand;

public class C_Exit implements ICommand {

	public void execute(Hashtable<String, String> arguments) throws Exception {
		System.out.println("Bye!");
		System.exit(0);
	}

	public void help() {

	}

}
