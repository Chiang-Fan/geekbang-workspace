package org.geektime.week1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FanJiang
 * @since todo (2021/7/21)
 */
@RestController
public class EchoServiceController {

    @PostMapping("/echo")
    public String echo(@RequestBody String msg) {
        return String.format("handle msg = { %s }", msg);
    }
}
