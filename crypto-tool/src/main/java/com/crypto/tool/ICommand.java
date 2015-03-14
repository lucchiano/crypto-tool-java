package com.crypto.tool;

import java.util.ArrayList;

import javax.naming.directory.InvalidAttributesException;

public interface ICommand {
	public void init(ArrayList<String> arguments) throws InvalidAttributesException;

	public void execute() throws Exception;

	public void help();
}
