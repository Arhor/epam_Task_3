package by.epam.task3.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PropertiesHandler {
    
    private static final Logger LOG = LogManager.getLogger();

    public static Properties readProperties(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            Properties prop = new Properties();
            prop.load(fis);
            return prop;
        } catch (IOException e) {
            LOG.fatal("Exception occured whithin loading '" + path + "'\n");
            throw new RuntimeException(e);
        }
    }
}
