package com.atguigu.gmall.item.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {

    @RequestMapping("index")
    @ResponseBody
    public String index(){
        return "index";
    }
}
