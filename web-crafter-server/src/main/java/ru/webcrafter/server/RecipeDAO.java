package ru.webcrafter.server;

import ru.webcrafter.core.entities.Recipe;

import java.util.List;

public interface RecipeDAO {
    String addRecipe(Recipe recipe);

    void deleteRecipe(String id);

    void updateRecipe(Recipe recipe);

    Recipe getRecipe(String id);

    List<Recipe> getAllRecipes();

}
