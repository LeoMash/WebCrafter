package ru.webcrafter.client.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.webcrafter.client.user.UserAuth;
import ru.webcrafter.client.user.UserService;
import ru.webcrafter.client.web.model.RecipeId;
import ru.webcrafter.core.WebCrafterService;
import ru.webcrafter.core.entities.User;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.Map;

@Controller
public class WebCrafterControllerCraft {

    private WebCrafterService service;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:client.xml");
        service = (WebCrafterService) applicationContext.getBean("rmiProxy");
    }

    @RequestMapping("/craft")
    public String onIndexLoaded(Map<String, Object> map, Principal principal) {
        map.put("itemTemplateList", service.getAllItemTemplates());
        map.put("recipeList", service.getAllRecipes());
        map.put("recipeId", new RecipeId());

        if (principal != null) {
            // HACKZZZ
            final String userName = principal.getName();
            UserAuth userAuth = userService.getUser(userName);
            if (userAuth != null) {
                long userId = userAuth.getUserId();
                User craftUser = service.getUser(userId);
                map.put("userCrafter", craftUser);
            }
        }

        return "craft";
    }

    @RequestMapping("/buy.do/{itemTemplateId}")
    public String buyItemTemplate(@PathVariable("itemTemplateId") String itemTemplateId, Principal principal) {
        if (principal == null) {
            return "redirect:/admin";
        }
        final String userName = principal.getName();
        UserAuth userAuth = userService.getUser(userName);
        if (userAuth != null) {
            long userId = userAuth.getUserId();
            service.userBuyItem(userId, itemTemplateId);
        }

        return "redirect:/craft";
    }

    @RequestMapping("/sell.do/{itemTemplateId}")
    public String sellItemTemplate(@PathVariable("itemTemplateId") String itemTemplateId, Principal principal) {
        if (principal == null) {
            return "redirect:/craft";
        }
        final String userName = principal.getName();
        UserAuth userAuth = userService.getUser(userName);
        if (userAuth != null) {
            long userId = userAuth.getUserId();
            service.userSellItem(userId, itemTemplateId);
        }

        return "redirect:/craft";
    }

    @RequestMapping(value = "/craft.do", method = RequestMethod.POST)
    public String applyCraft(@ModelAttribute("recipeId") RecipeId recipeId, Principal principal) {
        if (principal == null) {
            return "redirect:/craft";
        }
        final String userName = principal.getName();
        UserAuth userAuth = userService.getUser(userName);
        if (userAuth != null) {
            long userId = userAuth.getUserId();
            service.userCraft(userId, recipeId.getRecipeId());
        }

        return "redirect:/craft";
    }


    @RequestMapping(value = "/resetItems.do", method = RequestMethod.POST)
    public String onResetUserItems(@ModelAttribute("userCrafter") User userCrafter, BindingResult result) {
        service.resetUserData(userCrafter);
        return "redirect:/craft";
    }


}
