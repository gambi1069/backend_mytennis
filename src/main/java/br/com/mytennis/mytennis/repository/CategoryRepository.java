package br.com.mytennis.mytennis.repository;

import br.com.mytennis.mytennis.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryModel, Integer> {

    public List<CategoryModel> findByNameContainsIgnoreCaseOrderByName(String name);
}
