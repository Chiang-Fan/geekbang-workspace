package org.geektimes.microprofile.rest;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.jws.WebParam;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author FanJiang
 * @since todo (2021/7/21)
 */
public class Week3Demo {

    public static void main(String[] args) throws MalformedURLException {
//        EchoService echoService = RestClientBuilder.newBuilder()
//                .baseUrl(new URL("http://127.0.0.1:8080"))
//                .build(EchoService.class);
//
//        System.out.println(echoService.echo("1111"));

        ActuatorService actuatorService = RestClientBuilder.newBuilder()
                .baseUrl(new URL("http://127.0.0.1:8080"))
                .build(ActuatorService.class);

        System.out.println(actuatorService.shutdown());
    }

    @Path("/actuator")
    interface ActuatorService {

        @POST
        @Path("/shutdown")
        String shutdown();
    }

    interface EchoService {

        @POST
        @Path("/echo")
        String echo(@BeanParam String msg);
    }
}
