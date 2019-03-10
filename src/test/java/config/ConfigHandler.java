package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigHandler {
	private static final String CONFIG_FILE_NAME = "config.properties";
	private Logger log = Logger.getLogger(ConfigHandler.class);
	Properties prop;

	public ConfigHandler() {
		initFromFile();
	}

	private void initFromFile() {
		try {
			File file = new File(CONFIG_FILE_NAME);
			FileInputStream fis = new FileInputStream(file);
			prop = new Properties();
			prop.load(fis);
			fis.close();
		} catch (IOException e) {
			log.error("Error while reading config file");
		}
	}

	public String getBaseURI() {
		return prop.getProperty("baseURI");
	}

	public String getAuthID() {
		return prop.getProperty("AUTH_ID");
	}

	public String getAuthToken() {
		return prop.getProperty("AUTH_TOKEN");
	}

}
