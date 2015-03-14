package com.crypto.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
	private static String prompt = "#:";
	private static final String ARG_HELP = "-help";

	public static void main(String[] args) {
		CryptoTool.getInstance();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner in = null;
		while (true) {
			try {
				System.out.print(prompt + " ");
				String line = br.readLine();
				in = new Scanner(line);
				String command = null;
				ArrayList<String> arguments = new ArrayList<String>();
				boolean printHelp = false;
				while (in.hasNext()) {
					command = in.next().trim();
					try {
						while (true) {
							String arg = in.next();
							if (ARG_HELP.equalsIgnoreCase(arg))
								printHelp = true;
							arguments.add(arg);
						}
					} catch (NoSuchElementException e) {
					}
				}
				if (command != null && command.trim().length() != 0) {
					ICommand cmd = CryptoTool.getInstance().getCommand(command);
					if (cmd == null)
						System.err.println("Unrecognized command " + command + ". Use 'help' option to list available options or 'command' "
								+ ARG_HELP + " to command help");
					else {
						try {
							if (printHelp)
								cmd.help();
							cmd.init(arguments);
							cmd.execute();
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
					}
				}
				in.close();
			} catch (IOException e1) {
				in.close();
			}
		}

	}
}
