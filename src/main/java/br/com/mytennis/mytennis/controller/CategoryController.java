package br.com.mytennis.mytennis.controller;

import br.com.mytennis.mytennis.dto.CategoryDTO;
import br.com.mytennis.mytennis.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping
    public CategoryDTO create(@RequestBody CategoryDTO dto){ return service.create(dto);}

    @GetMapping("/{id}")
    public CategoryDTO findById(@PathVariable("id")int id){
        CategoryDTO dto=service.findById(id);
        createLinks(dto);
        return dto;
    }

    @GetMapping("/find/categories")
    public List<CategoryDTO> findByName(@PathVariable("name") String name){return service.findByName(name);}

    @GetMapping
    public CollectionModel<CategoryDTO> findAll(){
        CollectionModel<CategoryDTO> categories = CollectionModel.of(service.findAll());
        for (CategoryDTO category: categories){
            createLinks(category);
        }
        createCollectionLink(categories);
        return categories;
    }

    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable("id") int id, @RequestBody CategoryDTO dto){
        dto.setId(id);
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id")int id){
        service.delete(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    private void createLinks(CategoryDTO dto){
        dto.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                this.getClass()).findById(dto.getId())
                ).withSelfRel()
        );
        dto.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                this.getClass()).delete(dto.getId())
                ).withRel("delete")
        );
    }

    private void createCollectionLink(CollectionModel<CategoryDTO> categories){
        categories.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                this.getClass()).findAll()
                ).withRel(IanaLinkRelations.COLLECTION)
        );
    }


}
