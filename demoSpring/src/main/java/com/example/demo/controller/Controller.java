package com.example.demo.controller;

import com.example.demo.dto.FormDTO;
import com.example.demo.dto.FormDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {


    @RequestMapping("/")
    public String index(){
        return "index";
    }


    @Autowired
    private FormDao formDao;

    @RequestMapping(value = "/formNameList", method = RequestMethod.GET)
    private String getFormList(Model model){
        List<FormDTO> formList = formDao.findAll();
        model.addAttribute("formList", formList);

        return "formNameList";
    }
}
