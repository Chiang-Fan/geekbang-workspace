package org.geektime.week1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author FanJiang
 * @since todo (2021/7/7)
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("/index.jsp");
    }
}
