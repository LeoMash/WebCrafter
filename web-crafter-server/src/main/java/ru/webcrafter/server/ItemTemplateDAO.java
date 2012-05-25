package ru.webcrafter.server;

import ru.webcrafter.core.entities.ItemTemplate;

import java.util.List;

public interface ItemTemplateDAO {
    String addItemTemplate(ItemTemplate item);
    void deleteItemTemplate(String id);
    void updateItemTemplate(ItemTemplate item);
    ItemTemplate getItemTemplate(String id);
    List<ItemTemplate> getAllItemTemplates();

}
