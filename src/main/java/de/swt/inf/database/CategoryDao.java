package de.swt.inf.database;


import de.swt.inf.model.Category;

import java.util.List;

public interface CategoryDao {

    public abstract Category getCategory(int id);

    public abstract Category getCategory(Category category);

    public abstract List<Category> getAllCategories();

    public abstract Category getCategoryByName(String name);

    public abstract boolean updateCategory(Category category);

    public abstract boolean deleteCategory(int id);

    public abstract boolean deleteCategory(Category category);

    public abstract boolean addCategory(Category category);

}
