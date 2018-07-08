package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping("/hi") //@ResponseBody
    public String home(ModelMap modelMap){
        modelMap.put("hello", "Witaj świecie");
        return "hi";
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/hello")
    public String sayHello(@RequestParam("name") String name, Model model){
        model.addAttribute("name", name);
        return "hi";
    }
}
