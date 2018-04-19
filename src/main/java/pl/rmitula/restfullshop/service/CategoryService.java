package pl.rmitula.restfullshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rmitula.restfullshop.exception.ConflictException;
import pl.rmitula.restfullshop.exception.NotFoundException;
import pl.rmitula.restfullshop.model.Category;
import pl.rmitula.restfullshop.dao.CategoryRepository;
import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category findById(long id) {
        Category category = categoryRepository.findOne(id);

        if(category != null) {
            return category;
        } else {
            throw new NotFoundException("Not found category with id: " + id);
        }
    }

    public Category findByName(String name) {
        Category category = categoryRepository.findByNameIgnoreCase(name);

        if(category != null) {
            return category;
        } else {
            throw new NotFoundException("Not found category with name: " + name);
        }
    }

    public Long create(Category category) {
        Category categoryName = categoryRepository.findByNameIgnoreCase(category.getName());

        if(categoryName == null) {
            return categoryRepository.save(category).getId();
        } else {
            throw new ConflictException("This category already exists.");
        }
    }

    public void update(long id, String name) {
        Category category = categoryRepository.findOne(id);

        if(category != null) {

            Category categoryName = categoryRepository.findByNameIgnoreCase(name);

            if(categoryName != null && categoryName.getId() != id) {
                throw new ConflictException("This name is already associated with another category.");
                //FIXME: Overwriting same category name
            }

            category.setName(name);
            categoryRepository.save(category);
        } else {
            throw new NotFoundException("Not found category with id: " + id);
        }
    }

    public void delete(long id) {
        Category category = categoryRepository.findOne(id);

        if(category != null) {
            categoryRepository.delete(id);
        } else {
            throw new NotFoundException("Not found category with id: " + id);
        }
    }

}
