package com.crypto.tool;

import java.util.Hashtable;

public interface ICommand {
	public void execute(Hashtable<String, String> arguments) throws Exception;

	public void help();
}
