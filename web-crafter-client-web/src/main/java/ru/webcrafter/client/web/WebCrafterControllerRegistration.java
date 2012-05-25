package ru.webcrafter.client.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.webcrafter.client.user.UserAuth;
import ru.webcrafter.client.user.UserService;
import ru.webcrafter.core.WebCrafterService;
import ru.webcrafter.core.entities.User;

import javax.annotation.PostConstruct;
import java.util.Map;

@Controller
public class WebCrafterControllerRegistration {

    private WebCrafterService service;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:client.xml");
        service = (WebCrafterService) applicationContext.getBean("rmiProxy");
    }

    @RequestMapping("/register")
    public String onAdminPageLoaded(Map<String, Object> map) {
        map.put("newUser", new UserAuth());

        return "register";
    }


    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    public String addItemTemplate(@ModelAttribute("newUser") UserAuth userAuth,
                                  BindingResult result) {

        userAuth.setActive(true);
        Md5PasswordEncoder enc = new Md5PasswordEncoder();
        userAuth.setPasswordHash(enc.encodePassword(userAuth.getPasswordHash(), null));
        userService.createUser(userAuth);
        User user = new User();
        user.setId(userAuth.getUserId());
        service.addUser(user);

        return "redirect:/index";
    }


}
