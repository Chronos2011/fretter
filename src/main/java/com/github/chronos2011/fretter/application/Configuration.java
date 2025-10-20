package com.github.chronos2011.fretter.application;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.SystemUtils;

import com.github.chronos2011.fretter.Fretter;

/**
 * Class Configuration hosts application specific configuration information.
 */
public class Configuration {
	/** Application name as defined in properties */
	public static final String applicationName;
	/** Application version as defined in properties */
	public static final String applicationVersion;
	/** Application description as defined in properties */
	public static final String applicationDescription;
	/** Flag indicating whether the host is a Windows OS */
	public static final boolean hostIsWindows;

	static {
		final Properties properties = new Properties();
		try {
			properties.load(Fretter.class.getClassLoader().getResourceAsStream("project.properties"));
		} catch (IOException exception) {
			System.out.println("Could not load application properties, aborting");
			System.exit(1);
		}
		applicationName = properties.getProperty("application.name");
		applicationVersion = properties.getProperty("application.version");
		applicationDescription = properties.getProperty("application.description");
		hostIsWindows = SystemUtils.IS_OS_WINDOWS;
	}
}
