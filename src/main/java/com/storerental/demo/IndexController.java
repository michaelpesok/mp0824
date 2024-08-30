package com.storerental.demo;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.web.servlet.error.ErrorController;


@RestController
public class IndexController implements ErrorController{

    private static final String PATH = "/error";

    @RequestMapping(value = "/")
    public String welcome() {
        return "Welcome to Tool Rental Demo!";
    }
    
    @RequestMapping(value = PATH)
    public String error() {
        return "Error executing the HTTP request";
    }

    public String getErrorPath() {
        return PATH;
    }
}