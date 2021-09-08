package org.geektimes.project.week10.provider.service;

/**
 * @author FanJiang
 * @since
 */
public class EchoService implements IEchoService{
    @Override
    public String echo(String name) {
        return "hi " + name;
    }
}
