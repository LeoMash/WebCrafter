package ru.webcrafter.client.web;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.webcrafter.core.WebCrafterService;
import ru.webcrafter.core.entities.ItemTemplate;
import ru.webcrafter.core.entities.Recipe;

import javax.annotation.PostConstruct;
import java.util.Map;

@Controller
public class WebCrafterControllerAdmin {

    private WebCrafterService service;

    @PostConstruct
    public void init() {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:client.xml");
        service = (WebCrafterService) applicationContext.getBean("rmiProxy");
    }

    @RequestMapping("/admin")
    public String onAdminPageLoaded(Map<String, Object> map) {
        map.put("newItemTemplate", new ItemTemplate());
        map.put("newRecipe", new Recipe());

        map.put("itemTemplateList", service.getAllItemTemplates());
        map.put("recipeList", service.getAllRecipes());

        return "admin";
    }


    @RequestMapping(value = "/addItemTemplate.do", method = RequestMethod.POST)
    public String addItemTemplate(@ModelAttribute("newItemTemplate") ItemTemplate itemTemplate,
                                  BindingResult result) {

        String newId = service.addItemTemplate(itemTemplate);

        return "redirect:/admin";
    }

    @RequestMapping("/deleteItemTemplate.do/{itemTemplateId}")
    public String deleteItemTemplate(@PathVariable("itemTemplateId") String itemTemplateId) {

        service.deleteItemTemplate(itemTemplateId);

        return "redirect:/admin";
    }

    @RequestMapping(value = "/addRecipe.do", method = RequestMethod.POST)
    public String addRecipe(@ModelAttribute("newRecipe") Recipe recipe,
                            BindingResult result) {

        String newId = service.addRecipe(recipe);

        return "redirect:/admin";
    }

    @RequestMapping("/deleteRecipe.do/{recipeId}")
    public String deleteRecipe(@PathVariable("recipeId") String recipeId) {

        service.deleteRecipe(recipeId);

        return "redirect:/admin";
    }

}
