package ca.allaxis.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Pierre Francis Roy <pierrefroy@allaxis.ca>
 */
@WebListener
public class ApplicationStartUpListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(ApplicationStartUpListener.class.getName());

    private static final String CONST_CONF_PROP_NAME = "ca.allaxis.config";

    @Override
    public void contextInitialized(final ServletContextEvent contextEvent) {
        ServletContextListener.super.contextInitialized(contextEvent);
        logger.info("Application starting...");
        ServletContext servletContext = contextEvent.getServletContext();
        printSystemEnvironment();
        printSystemProperties();
        loadProperties(servletContext);
        logger.info("Application started");
    }

    @Override
    public void contextDestroyed(final ServletContextEvent contextEvent) {
        ServletContextListener.super.contextDestroyed(contextEvent);
        logger.info("Application shutdown");
    }

    private void loadProperties(final ServletContext context) {
        final String defaultConfigFileName = context.getRealPath("/WEB-INF/config.properties");
        logger.log(Level.INFO, "defaultConfigFile: {0}", defaultConfigFileName);
        final File file = new File(defaultConfigFileName);
        if (file.canRead()) {
            try {
                Properties properties = load(file);
                setSystemProperties(properties, false);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }

        final String domainConfig = getCatalinaHome();
        logger.log(Level.INFO, "domainConfig: {0}", domainConfig);
        final File domainConfigFile = new File(getCatalinaHome() + "/config.properties");
        if (domainConfigFile.canRead()) {
            try {
                Properties properties = load(domainConfigFile);
                setSystemProperties(properties, true);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Error loading domain config.", ex);
            }
        }
    }

    private Properties load(final File file) throws IOException {
        logger.log(Level.INFO, "Loading {0}", file.getCanonicalPath());
        final Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            properties.load(inputStream);
            return properties;
        } finally {
        }
    }

    private void setSystemProperties(final Properties properties, final boolean forceLoad) {
        properties.stringPropertyNames().forEach(property -> {
            final String propertyValue = properties.getProperty(property);
            if (forceLoad || (System.getProperty(property) == null)) {
                System.setProperty(property, propertyValue);
                logger.log(Level.INFO, "Setting {0}={1}", new Object[]{property, propertyValue});
            }
        });
    }

    private void printSystemEnvironment() {
        Map<String, String> env = System.getenv();
        env.forEach((key, value) -> {
            logger.log(Level.INFO, "System [{0}={1}]", new Object[]{key, value});
        });
    }

    private void printSystemProperties() {
        final Properties properties = System.getProperties();
        properties.forEach((key, value) -> {
            logger.log(Level.INFO, "Property [{0}={1}]", new Object[]{key, value});
        });
    }

    private String getCatalinaHome() {
        return System.getProperty("catalina.home", System.getProperty("user.dir"));
    }

    private String getCatalinaBase() {
        return System.getProperty("catalina.base", getCatalinaHome());
    }

    private String getConfigBase() {
        return System.getProperty(CONST_CONF_PROP_NAME, getCatalinaBase());
    }

    private String getContextBaseName(final ServletContext context) {
        final String path = context.getContextPath();
        return (path.isEmpty() ? "ROOT" : path.substring(1).replace('/', '#'));
    }

}
