package org.geektimes.configuration.microprofile.config.annotation;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;

/**
 * @author FanJiang
 * @since
 */
@ConfigSource(ordinal = 200, resource = "filepath:D:\\chiangfan\\Workspace\\Learn\\小马哥 P7\\Project\\config\\test.properties")
@ConfigSource(ordinal = 200, resource = "filepath:D:\\chiangfan\\Workspace\\Learn\\小马哥 P7\\Project\\config\\test.yaml",
        factory = YamlConfigSourceFactory.class)
public class Week6Test {

    @Before
    public void initConfigSourceFactory() throws Throwable {
        ConfigSource[] configSources = getClass().getAnnotationsByType(ConfigSource.class);
        for (ConfigSource configSource : configSources) {
            String name = configSource.name();
            int ordinal = configSource.ordinal();
            String encoding = configSource.encoding();
            String resource = configSource.resource();
            URL resourceURL = new URL(resource);
            Class<? extends ConfigSourceFactory> configSourceFactoryClass = configSource.factory();
            if (ConfigSourceFactory.class.equals(configSourceFactoryClass)) {
                configSourceFactoryClass = DefaultConfigSourceFactory.class;
            }

            ConfigSourceFactory configSourceFactory = configSourceFactoryClass.newInstance();
            org.eclipse.microprofile.config.spi.ConfigSource source =
                    configSourceFactory.createConfigSource(name, ordinal, resourceURL, encoding);
            System.out.println(source.getProperties());
        }
    }

    @Test
    public void test() {

    }
}
