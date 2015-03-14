package com.crypto.tool.command;

import java.util.Hashtable;

import com.crypto.tool.CryptoTool;
import com.crypto.tool.ICommand;

public class C_Prompt implements ICommand {

	private String newPrompt = null;

	public void execute(Hashtable<String, String> arguments) throws Exception {
		newPrompt = null;
		if (arguments.size() == 1)
			newPrompt = arguments.get(0);
		if (newPrompt != null)
			CryptoTool.setPrompt(newPrompt);
		else
			System.out.println(CryptoTool.getPrompt());
	}

	public void help() {

	}
}
