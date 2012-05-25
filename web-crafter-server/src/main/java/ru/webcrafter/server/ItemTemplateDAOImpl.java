package ru.webcrafter.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import ru.webcrafter.core.entities.ItemTemplate;

import java.util.List;

@Repository
public class ItemTemplateDAOImpl implements ItemTemplateDAO {

    @Autowired
    HibernateTemplate hibernateTemplate;

    public String addItemTemplate(ItemTemplate item) {
        return (String) hibernateTemplate.save(item);
    }

    public void deleteItemTemplate(String id) {
        hibernateTemplate.delete(getItemTemplate(id));
    }

    public void updateItemTemplate(ItemTemplate item) {
        hibernateTemplate.update(item);
    }

    public ItemTemplate getItemTemplate(String id) {
        return hibernateTemplate.get(ItemTemplate.class, id);
    }

    public List<ItemTemplate> getAllItemTemplates() {
        return hibernateTemplate.find("from ItemTemplate");
    }
}
