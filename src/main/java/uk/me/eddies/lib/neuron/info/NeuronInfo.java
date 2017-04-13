/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.info;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class NeuronInfo {

	private static final URL BUILD_PROPERTIES_RESOURCE =
			NeuronInfo.class.getResource("build.properties");
	private static final String VERSION_PROPERTY =
			"uk.me.eddies.lib.neuron.info.version";

	private Properties buildProperties;

	public NeuronInfo() throws IOException {
		buildProperties = loadProperties(BUILD_PROPERTIES_RESOURCE);
	}

	public String getVersionNumber() {
		return buildProperties.getProperty(VERSION_PROPERTY);
	}

	private static Properties loadProperties(URL resource) throws IOException {
		try (InputStream resourceStream = resource.openStream()) {
			Properties properties = new Properties();
			properties.load(resourceStream);
			return properties;
		}
	}
}
