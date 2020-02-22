package com.morsstepan.getspotified;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/hello")
    public String hello(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
                        Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    @GetMapping("/hello")
    public String a(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Model model) {
        model.addAttribute("name", name);
        return "hello";
    }
}
