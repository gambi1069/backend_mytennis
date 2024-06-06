package br.com.mytennis.mytennis.service;

import br.com.mytennis.mytennis.dto.CategoryDTO;
import br.com.mytennis.mytennis.exceptions.ResourceNotFoundException;
import br.com.mytennis.mytennis.mapper.CustomModelMapper;
import br.com.mytennis.mytennis.model.CategoryModel;
import br.com.mytennis.mytennis.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;



    public CategoryDTO create(CategoryDTO dto){
        CategoryModel model = CustomModelMapper.parseObject(dto, CategoryModel.class);
        return CustomModelMapper.parseObject(repository.save(model), CategoryDTO.class);
    }

    public CategoryDTO findById(int id){
        CategoryModel model = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(null));
        return CustomModelMapper.parseObject(model, CategoryDTO.class);
    }

    public List<CategoryDTO> findByName(String name){
        List<CategoryModel> categories = repository.findByNameContainsIgnoreCaseOrderByName(name);
        return CustomModelMapper.parseObjectList(categories, CategoryDTO.class);
    }

    public List<CategoryDTO> findAll(){
        var categories =repository.findAll();
        return CustomModelMapper.parseObjectList(categories, CategoryDTO.class);
    }

    public CategoryDTO update(CategoryDTO dto){
        CategoryModel found=repository.findById(dto.getId()).orElseThrow(
                ()-> new ResourceNotFoundException("Não encontrado!"));
        found.setName(dto.getName());
        found.setDescription(dto.getDescription());
        return CustomModelMapper.parseObject(repository.save(found), CategoryDTO.class);
    }

    public void delete(int id){

        var found=repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Não encontrado!"));
        repository.delete(found);
    }

}
