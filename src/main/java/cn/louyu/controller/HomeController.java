package cn.louyu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Home")
public class HomeController {
    @RequestMapping(value = {"","/","/Index"})
    public String index(){
        return "/views/index";
    }

    @RequestMapping(value ="/Console")
    public String console(){
        return "/views/home/console";
    }
}
