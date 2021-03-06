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
package org.geektimes.cache.week5;

import org.geektimes.cache.annotation.interceptor.CachePutInterceptor;
import org.geektimes.cache.annotation.interceptor.CacheRemoveInterceptor;
import org.geektimes.interceptor.DefaultInterceptorEnhancer;
import org.geektimes.interceptor.InterceptorEnhancer;
import org.junit.Test;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * {@link CachePutInterceptor} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public class Week5Test {

    private DataRepository dataRepository = new InMemoryDataRepository();

    private InterceptorEnhancer enhancer = new DefaultInterceptorEnhancer();

    private CachingProvider cachingProvider = Caching.getCachingProvider();

    private CacheManager cacheManager = cachingProvider.getCacheManager();

    @Test
    public void testRemove() {
        DataRepository repository = enhancer.enhance(dataRepository, DataRepository.class, new CachePutInterceptor(), new CacheRemoveInterceptor());
        assertTrue(repository.create("A", 1));
        assertTrue(repository.remove("A"));
        assertNull(repository.get("A"));
    }
}
