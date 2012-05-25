package ru.webcrafter.client.standalone;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import ru.webcrafter.core.WebCrafterService;
import ru.webcrafter.core.entities.ItemTemplate;
import ru.webcrafter.core.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class WebCrafterStandaloneClientMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:client.xml");
        WebCrafterService service = (WebCrafterService) applicationContext.getBean("rmiProxy");

        try {
            ItemTemplate wood = service.getItemTemplate("Wood");
            if (wood == null) {
                wood = new ItemTemplate("Wood");
                service.addItemTemplate(wood);
            }
            ItemTemplate fire = service.getItemTemplate("Fire");
            if (fire == null) {
                fire = new ItemTemplate("Fire");
                service.addItemTemplate(fire);
            }
            ItemTemplate coal = service.getItemTemplate("Coal");
            if (coal == null) {
                coal = new ItemTemplate("Coal");
                service.addItemTemplate(coal);
            }
            Recipe makeWoodCoal = service.getRecipe("Coal");
            if (makeWoodCoal == null) {
                List<ItemTemplate> ingredients = new ArrayList<ItemTemplate>();
                ingredients.add(wood);
                ingredients.add(fire);
                makeWoodCoal = new Recipe("Coal", ingredients, coal);
                service.addRecipe(makeWoodCoal);
            }
            System.out.println(makeWoodCoal.getId());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
