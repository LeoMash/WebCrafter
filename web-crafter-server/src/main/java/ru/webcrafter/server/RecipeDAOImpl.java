package ru.webcrafter.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import ru.webcrafter.core.entities.Recipe;

import java.util.List;

@Repository
public class RecipeDAOImpl implements RecipeDAO {

    @Autowired
    HibernateTemplate hibernateTemplate;

    public String addRecipe(Recipe recipe) {
        return (String) hibernateTemplate.save(recipe);
    }

    public void deleteRecipe(String id) {
        hibernateTemplate.delete(getRecipe(id));
    }

    public void updateRecipe(Recipe recipe) {
        hibernateTemplate.update(recipe);
    }

    public Recipe getRecipe(String id) {
        return hibernateTemplate.get(Recipe.class, id);
    }

    public List<Recipe> getAllRecipes() {
        return hibernateTemplate.find("from Recipe");
    }
}
