package ru.webcrafter.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.webcrafter.core.WebCrafterService;
import ru.webcrafter.core.entities.ItemTemplate;
import ru.webcrafter.core.entities.Recipe;
import ru.webcrafter.core.entities.User;

import java.util.*;

@Service(value = "WebCrafterService")
public class WebCrafterServiceImpl implements WebCrafterService {
    @Autowired
    private RecipeDAO recipeDAO;
    @Autowired
    private ItemTemplateDAO itemTemplateDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional
    public String addRecipe(Recipe recipe) {
        return recipeDAO.addRecipe(recipe);
    }

    @Transactional
    public void deleteRecipe(String id) {
        recipeDAO.deleteRecipe(id);
    }

    @Transactional
    public Recipe getRecipe(String id) {
        return recipeDAO.getRecipe(id);
    }

    @Transactional
    public void updateRecipe(Recipe recipe) {
        recipeDAO.updateRecipe(recipe);
    }


    @Transactional
    public String addItemTemplate(ItemTemplate itemTemplate) {
        return itemTemplateDAO.addItemTemplate(itemTemplate);
    }

    @Transactional
    public void updateItemTemplate(ItemTemplate itemTemplate) {
        itemTemplateDAO.updateItemTemplate(itemTemplate);
    }

    @Transactional
    public void deleteItemTemplate(String id) {
        List<Recipe> recipes = getAllRecipes();
        for (Recipe recipe : recipes) {
            if (recipe.getResult().getId().equals(id)) {
                deleteRecipe(recipe.getId());
                continue;
            }
            for (ItemTemplate itemTemplate : recipe.getIngredients()) {
                if (itemTemplate.getId().equals(id)) {
                    deleteRecipe(recipe.getId());
                    break;
                }
            }
        }
        List<User> users = userDAO.getAllUsers();
        ItemTemplate itemTemplate = getItemTemplate(id);
        for (User user : users) {
            Map<String, Long> items = user.getItems();
            if (items.containsKey(itemTemplate.getId())) {
                items.remove(itemTemplate.getId());
            }
            user.setItems(items);
            updateUser(user);
        }
        itemTemplateDAO.deleteItemTemplate(id);
    }

    @Transactional
    public ItemTemplate getItemTemplate(String id) {
        return itemTemplateDAO.getItemTemplate(id);
    }

    @Transactional
    public List<Recipe> getAllRecipes() {
        return recipeDAO.getAllRecipes();
    }

    @Transactional
    public List<ItemTemplate> getAllItemTemplates() {
        return itemTemplateDAO.getAllItemTemplates();
    }

    @Transactional
    public void addUser(User user) {
        randomizeUserData(user);
        userDAO.addUser(user);
    }

    @Transactional
    public void resetUserData(User user) {
        randomizeUserData(user);
        updateUser(user);
    }

    private void randomizeUserData(User user) {
        Random rnd = new Random();

        int amount = rnd.nextInt(10000) + 10000;
        user.setAmount(amount);

        int itemsCount = rnd.nextInt(15) + 15;
        List<ItemTemplate> itemTemplates = getAllItemTemplates();
        int itemTemplatesCount = itemTemplates.size();
        Map<String, Long> items = new HashMap<String, Long>();
        if (itemTemplatesCount != 0) {
           for (int i = 0; i < itemsCount; ++i) {
               int idxItem = rnd.nextInt(itemTemplatesCount);
               ItemTemplate itemTpl = itemTemplates.get(idxItem);
               if (items.containsKey(itemTpl.getId())) {
                   items.put(itemTpl.getId(), items.get(itemTpl.getId()) + 1);
               } else {
                   items.put(itemTpl.getId(), 1L);
               }
           }
        }
        user.setItems(items);
    }


    public User getUser(long id) {
        return userDAO.getUser(id);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void userCraft(long id, String recipeId) {
        User user = getUser(id);
        Recipe recipe = getRecipe(recipeId);

        List<ItemTemplate> ingredients = recipe.getIngredients();
        List<String> ingrNames = new ArrayList<String>();
        for (ItemTemplate item : ingredients) {
            ingrNames.add(item.getId());
        }
        // check that user has all ingredients
        Set<String> itemKeys = user.getItems().keySet();
        if (!itemKeys.containsAll(ingrNames)) {
            return;
        }

        // apply craft
        Map<String, Long> items = user.getItems();
        for (ItemTemplate ingr : ingredients) {
            Long cnt = items.get(ingr.getId());
            cnt--;
            if (cnt == 0L) {
                items.remove(ingr.getId());
            } else {
                items.put(ingr.getId(), cnt);
            }
        }
        ItemTemplate res = recipe.getResult();
        if (items.containsKey(res.getId())) {
            items.put(res.getId(), items.get(res.getId()) + 1);
        } else {
            items.put(res.getId(), 1L);
        }
        user.setItems(items);

        updateUser(user);

    }

    public void userBuyItem(long id, String itemId) {
        User user = getUser(id);
        ItemTemplate itemTemplate = getItemTemplate(itemId);

        Long amount = user.getAmount();
        if (amount < itemTemplate.getCost()) {
            return;
        }
        user.setAmount(amount - itemTemplate.getCost());
        Map<String, Long> items = user.getItems();
        if (items.containsKey(itemId)) {
            items.put(itemId, items.get(itemId) + 1);
        } else {
            items.put(itemId, 1L);
        }
        user.setItems(items);

        updateUser(user);

    }

    @Transactional
    public void userSellItem(long id, String itemId) {
        User user = getUser(id);

        Map<String, Long> items = user.getItems();
        if (!items.containsKey(itemId)) {
            return;
        }
        Long cnt = items.get(itemId);
        cnt--;
        if (cnt == 0L) {
            items.remove(itemId);
        } else {
            items.put(itemId, cnt);
        }
        user.setItems(items);
        ItemTemplate itemTemplate = getItemTemplate(itemId);
        user.setAmount(user.getAmount() + itemTemplate.getCost());

        updateUser(user);

    }


}
