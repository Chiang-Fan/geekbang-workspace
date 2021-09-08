package org.geektimes.project.week10.provider.service;

import org.geektimes.project.week10.bulkhead.Bulkhead;

/**
 * @author FanJiang
 * @since
 */
public interface IEchoService {

    @Bulkhead
    String echo(String name);
}
