package com.crypto.tool.command;

import java.io.InvalidObjectException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.Hashtable;
import java.util.Set;

import com.crypto.tool.ICommand;

public class C_Provider implements ICommand {

	private static final String ARG_NAME = "-name";
	private static final String ARG_EXT = "-ext";
	private static final String ARG_LIST = "-list";
	private static final String ARG_PROP = "-prop";

	private boolean printExt = false;
	private String providerName = null;
	private boolean listService = false;
	private boolean listProperties = false;

	public void execute(Hashtable<String, String> arguments) throws Exception {
		providerName = arguments.get(ARG_NAME);
		printExt = arguments.containsKey(ARG_EXT);
		listService = arguments.containsKey(ARG_LIST);
		listProperties = arguments.containsKey(ARG_PROP);
		Provider[] providers = null;

		if (providerName == null) {
			System.out.println("List all providers");
			providers = Security.getProviders();
		}
		else {
			System.out.println("Finding provider " + providerName);
			Provider provider = Security.getProvider(providerName);
			if (provider == null)
				throw new InvalidObjectException("Provider " + providerName + " not found");
			providers = new Provider[1];
			providers[0] = provider;
		}

		for (int i = 0; i < providers.length; i++) {
			Provider provider = providers[i];
			printProviderInfo(provider);
			if (listService) {
				System.out.println(" - Provider services:");
				for (Service service : provider.getServices()) {
					System.out.println("        - " + service.getType() + ": " + service.getAlgorithm());
					if (printExt)
						System.out.println("          (" + service.getClassName() + ")");
				}
			}

		}
	}

	private void printProviderInfo(Provider provider) {
		Set<Service> services = provider.getServices();
		System.out.println("Name    : " + provider.getName() + " (" + services.size() + " serivces)");
		if (printExt)
			System.out.println("Info    : " + provider.getInfo());
		if (printExt)
			System.out.println("Version : " + provider.getVersion());
		if (listProperties) {
			Set<String> providerProperties = provider.stringPropertyNames();
			System.out.println("- - Provider properties:");
			for (String propertyKey : providerProperties) {
				String propertyVal = provider.getProperty(propertyKey);
				System.out.println("        - " + propertyKey + ": " + propertyVal);
			}
		}

	}

	public void help() {
		System.out.println("   " + ARG_NAME + " <providername>: Provider to list");
		System.out.println("   " + ARG_LIST + ": List services for selected providers");
		System.out.println("   " + ARG_EXT + ": List additional  info");
		System.out.println("   " + ARG_PROP + ": List provider properties");
	}
}
