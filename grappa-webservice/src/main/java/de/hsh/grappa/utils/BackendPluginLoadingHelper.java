package de.hsh.grappa.utils;

import de.hsh.grappa.plugin.BackendPlugin;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BackendPluginLoadingHelper {
    private static final String CONFIG_CLASS_PATHES = "grappa.plugin.grader.classpathes";
    private static final String CONFIG_CLASS_NAME = "grappa.plugin.grader.class";
    private static final String CONFIG_GRADER_CONFIG_PATH = "grappa.plugin.grader.config";
    private static final String CONFIG_FILE_EXTENSIONS = "grappa.plugin.grader.fileextensions";
    private static final String CONFIG_FILE_NAME = "grappa-grader-backend-starter.properties";

    private static final Logger log = LoggerFactory.getLogger(BackendPluginLoadingHelper.class);
    private static ClassPathClassLoader<BackendPlugin> classLoader;

//    // Use as singleton since this class makes use of a ClassLoader needs to be reused
//    // due to
//    private static BackendPluginLoadingHelper singleton = new BackendPluginLoadingHelper();
//
//    public static BackendPluginLoadingHelper getInstance() {
//        return singleton;
//    }
	
	public static void loadClasspathLibsFromProperties(Properties config) throws Exception{
		String classpathes = config.getProperty(CONFIG_CLASS_PATHES);
        String extensions = config.getProperty(CONFIG_FILE_EXTENSIONS);
        loadClasspathLibs(classpathes, extensions);
	}

	public static void loadClasspathLibs(String classpath, String fileExtensions) throws Exception {
        log.debug("Current classpathes: {}", classpath);
	    String[] classpathParts = classpath.split(";");
        log.debug("Current extensions: {}", fileExtensions);
        String[] extensionsParts = fileExtensions.split(";");
        classLoader = new ClassPathClassLoader(classpathParts, extensionsParts);
    }

    public static BackendPlugin loadGraderPluginFromProperties(Properties config) throws Exception {
        String className = config.getProperty(CONFIG_CLASS_NAME);
        String graderConfPath = config.getProperty(CONFIG_GRADER_CONFIG_PATH);
        return loadGraderPlugin(className, graderConfPath);
    }

    public static BackendPlugin loadGraderPlugin(String className, String backendPluginConfigPath) throws Exception {
        Properties pluginConfig = new Properties();

        if (backendPluginConfigPath != null && !backendPluginConfigPath.isEmpty()) {
            try (InputStream is = new FileInputStream(new File(backendPluginConfigPath))) {
                pluginConfig.load(is);
            } catch (IOException e) {
                log.error("Error while loading grader config file: {}", backendPluginConfigPath);
                log.error(e.getMessage());
                log.error(ExceptionUtils.getStackTrace(e));
                throw e;
            }
        } else {
            log.warn("BackendPlugin config file is not configured.");
        }

        BackendPlugin graderPlugin = (BackendPlugin) classLoader.instantiateClass(className, BackendPlugin.class);

        try {
            // It does not matter if the BackendPlugin's config file doesn't exist, or even if it's empty.
            // BackendPlugin.init() still needs to be called.
            graderPlugin.init(pluginConfig);
        } catch (Exception e) {
            log.error("Error while initializing the grader plugin with config file: {}", backendPluginConfigPath);
            log.error(e.getMessage());
            log.error(ExceptionUtils.getStackTrace(e));
            throw e;
        }

        log.info("Grader plugin loaded successfully.");
        return graderPlugin;
    }

//    public static BackendPlugin loadGraderPlugin(Properties config) throws Exception {
//    	BackendPlugin graderPlugin = null;
//
//        Properties backendConf = new Properties();
//        String graderConfPath = config.getProperty(CONFIG_GRADER_CONFIG_PATH);
//        if (graderConfPath != null && !graderConfPath.isEmpty()) {
//            try {
//                backendConf.load(new FileInputStream(graderConfPath));
//            } catch (IOException e) {
//                log.error("Error while loading grader config file: {}", graderConfPath);
//                log.error(e.getMessage());
//                log.error(ExceptionUtils.getStackTrace(e));
//                throw e;
//            }
//        } else {
//            log.warn("BackendPlugin config file is not configured.");
//        }
//
//        String className = config.getProperty(CONFIG_CLASS_NAME);
//        graderPlugin = (BackendPlugin) classLoader.instantiateClass(className, BackendPlugin.class);
//
//        // tmp comment: do this as a separate commit: call graderPlugin.init even if no config file
//        // exists or if its empty
//        if (backendConf == null) {
//            // It does not matter if the BackendPlugin's config file doesn't exist, or even if it's empty.
//            // BackendPlugin.init() still needs to be called.
//            log.info("BackendPlugin config file does not exist... initializing BackendPlugin with an " +
//                "empty config.");
//            backendConf = new Properties();
//        }
//
//        try {
//            graderPlugin.init(backendConf);
//        } catch (Exception e) {
//            log.error("Error while initializing the grader plugin with config file: {}", graderConfPath);
//            log.error(e.getMessage());
//            log.error(ExceptionUtils.getStackTrace(e));
//            throw e;
//        }
//
//        log.info("Grader plugin loaded successfully.");
//        return graderPlugin;
//    }
}
