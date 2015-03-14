package com.crypto.tool.command;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.naming.directory.InvalidAttributesException;

import com.crypto.tool.ICommand;

public class Providers implements ICommand {

	private static final String ARG_PROVIDER = "-provider";

	private String providerName = null;

	public void init(ArrayList<String> arguments) throws InvalidAttributesException {
		providerName = null;

		for (Iterator iterator = arguments.iterator(); iterator.hasNext();) {
			String argument = (String) iterator.next();
			if (argument.startsWith(ARG_PROVIDER)) {
				providerName = argument.replaceFirst(ARG_PROVIDER + "=", "");
			}

		}
	}

	public void execute() throws Exception {
		if (providerName == null) {
			System.out.println("Current enabled providers:");
			Provider[] providers = Security.getProviders();
			for (int i = 0; i < providers.length; i++) {
				Provider provider = providers[i];
				System.out.println(" - " + provider.getName());
			}
		} else {
			Provider provider = Security.getProvider(providerName);
			System.out.println("Name    : " + provider.getName());
			System.out.println("Info    : " + provider.getInfo());
			System.out.println("Version : " + provider.getVersion());
			Set<Service> services = provider.getServices();
			System.out.println("Services: Found " + services.size() + " serivces");
			for (Iterator<Service> iterator = services.iterator(); iterator.hasNext();) {
				Service service = iterator.next();
				System.out.println("          " + service.getClassName());
				// System.out.println("          - Class    : " +
				// service.getClassName());
				System.out.println("          - Algorithm: " + service.getAlgorithm());
				System.out.println("          - Type     : " + service.getType());
			}

		}
	}

	public void help() {
		System.out.println("No arguments: List current loaded providers");
		System.out.println(ARG_PROVIDER + ": Specify provider name to list info. Sample: -provider=providerName");
	}
}
