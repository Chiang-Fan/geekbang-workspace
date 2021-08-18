/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geektimes.configuration.microprofile.config.annotation;

import org.eclipse.microprofile.config.spi.ConfigSource;
import org.geektimes.configuration.microprofile.config.source.MapConfigSource;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

/**
 * The default implementation of  {@link ConfigSourceFactory}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public class YamlConfigSourceFactory implements ConfigSourceFactory {

    @Override
    public ConfigSource createConfigSource(String name, int ordinal, URL resource, String encoding) {
        ConfigSource configSource = null;
        try (InputStream inputStream = resource.openStream();
             InputStreamReader reader = new InputStreamReader(inputStream, encoding)) {
            Yaml yaml = new Yaml();

            String actualName = StringUtils.isEmpty(name) ? resource.toString() : name;
            configSource = new MapConfigSource(actualName, ordinal, yaml.loadAs(reader, Properties.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return configSource;
    }
}