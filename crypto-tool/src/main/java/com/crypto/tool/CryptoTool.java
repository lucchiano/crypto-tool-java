package com.crypto.tool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CryptoTool {
	private static String prompt = "#:";
	private static final String ARG_HELP = "-help";
	private static final String ARG_DEBUG = "-debug";

	public static void main(String[] args) {
		CommandControl.getInstance();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				System.out.print(prompt + " ");
				String line = br.readLine();
				ArrayList<String> arguments = new ArrayList<String>();
				String command = parseCommandLine(line, arguments);
				Hashtable<String, String> argumentTable = parseArguments(arguments);
				if (argumentTable.containsKey(ARG_DEBUG))
					printDebugCommandLineInterpretation(command, argumentTable);
				executeCommand(command, argumentTable);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

	private static void printDebugCommandLineInterpretation(String command, Hashtable<String, String> argumentTable) {
		System.out.println("Command: " + command);
		Enumeration<String> keys = argumentTable.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String val = argumentTable.get(key);
			System.out.println("   " + key + (val.equals("") ? "" : " = " + val));
		}
	}

	private static void executeCommand(String command, Hashtable<String, String> argumentTable) throws Exception {
		if (command != null && command.trim().length() != 0) {
			ICommand cmd = CommandControl.getInstance().getCommand(command);
			if (cmd == null)
				System.err.println("Unrecognized command " + command + ". Use 'help' option to list available options");
			else {
				try {
					if (argumentTable.containsKey(ARG_HELP))
						cmd.help();
					else
						cmd.execute(argumentTable);
				} catch (Exception e) {
					if (e instanceof IndexOutOfBoundsException) {
						System.out.println("ERROR: Illegal argument");
					}
					else {
						System.out.println(e.getLocalizedMessage());
					}
				}
			}
		}
	}

	private static Hashtable<String, String> parseArguments(ArrayList<String> arguments) {

		Hashtable<String, String> args = new Hashtable<String, String>();
		String key = null;
		for (String arg : arguments) {
			if (key == null && arg.startsWith("-")) {
				key = arg;
			} else if (key != null && arg.startsWith("-")) {
				args.put(key, "");
				key = arg;
			} else if (key != null && !arg.startsWith("-")) {
				args.put(key, arg);
				key = null;
			}
		}
		if (key != null)
			args.put(key, "");

		return args;
	}

	private static String parseCommandLine(String line, ArrayList<String> arguments) {
		Scanner in = new Scanner(line);
		boolean shortParam = true;
		StringBuffer longParam = null;
		String command = null;
		while (in.hasNext()) {
			command = in.next().trim();
			try {
				while (true) {
					String arg = in.next();
					if (!shortParam) {
						if (arg.endsWith("\"")) {
							longParam.append(" " + arg.substring(0, arg.length() - 1));
							shortParam = true;
							arg = longParam.toString();
						} else {
							longParam.append(" " + arg);

						}
					}
					if (arg.startsWith("\"") && arg.endsWith("\"")) {
						arg = arg.substring(1, arg.length() - 1);
					}
					if (arg.startsWith("\"") && shortParam) {
						shortParam = false;
						longParam = new StringBuffer(arg.substring(1));
					}

					if (shortParam)
						arguments.add(arg);
				}
			} catch (NoSuchElementException e) {
			}
		}
		in.close();
		return command;
	}

	public static void setPrompt(String prompt) {
		CryptoTool.prompt = prompt;
	}

	public static String getPrompt() {
		return prompt;
	}
}
