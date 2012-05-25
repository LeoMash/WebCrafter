package ru.webcrafter.client.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.webcrafter.client.user.UserService;
import ru.webcrafter.core.WebCrafterService;

import javax.annotation.PostConstruct;
import java.util.Map;

@Controller
public class WebCrafterController {

    private WebCrafterService service;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:client.xml");
        service = (WebCrafterService) applicationContext.getBean("rmiProxy");
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String onIndexLoaded(Map<String, Object> map) {
        map.put("itemTemplateList", service.getAllItemTemplates());
        map.put("recipeList", service.getAllRecipes());

        return "main";
    }

}
