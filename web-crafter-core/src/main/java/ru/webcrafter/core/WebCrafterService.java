package ru.webcrafter.core;

import ru.webcrafter.core.entities.ItemTemplate;
import ru.webcrafter.core.entities.Recipe;
import ru.webcrafter.core.entities.User;

import java.util.List;

public interface WebCrafterService {
    String addRecipe(Recipe recipe);

    void updateRecipe(Recipe recipe);

    void deleteRecipe(String id);

    Recipe getRecipe(String id);

    List<Recipe> getAllRecipes();

    String addItemTemplate(ItemTemplate itemTemplate);

    void updateItemTemplate(ItemTemplate itemTemplate);

    void deleteItemTemplate(String id);

    ItemTemplate getItemTemplate(String id);

    List<ItemTemplate> getAllItemTemplates();

    void addUser(User user);

    void resetUserData(User user);

    User getUser(long id);

    void updateUser(User user);

    void userCraft(long id, String recipeId);

    void userBuyItem(long id, String itemId);

    void userSellItem(long id, String itemId);
}

