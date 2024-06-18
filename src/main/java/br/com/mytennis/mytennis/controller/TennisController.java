package br.com.mytennis.mytennis.controller;

import br.com.mytennis.mytennis.dto.TennisDTO;
import br.com.mytennis.mytennis.service.TennisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tennis")
public class TennisController {

    @Autowired
    private TennisService service;

    @Autowired
    private PagedResourcesAssembler<TennisDTO> assembler;

    @PostMapping
    public ResponseEntity<EntityModel<TennisDTO>>create(@RequestBody TennisDTO dto){
        TennisDTO createdTenis = service.create(dto);
        createLinks(createdTenis);
        EntityModel<TennisDTO> entityModel = EntityModel.of(createdTenis);
        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TennisDTO>> findById(@PathVariable("id") int id){
        TennisDTO dto=service.findById(id);
        createLinks(dto);
        EntityModel<TennisDTO> entityModel = EntityModel.of(dto);
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<PagedModel<EntityModel<TennisDTO>>> findByName(
        @RequestParam(value = "name", required = true) String name,
        @RequestParam(value = "page", required = true, defaultValue = "0") int page,
        @RequestParam(value = "size", required = true, defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<TennisDTO> tennis = service.findByName(name, pageable);

        PagedModel<EntityModel<TennisDTO>> pagedModel = assembler.toModel(tennis);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TennisDTO>>>findAll(){
        List<TennisDTO> tennis = service.findAll();
        for (TennisDTO tenis : tennis){
            createLinks(tenis);
        }
        CollectionModel<EntityModel<TennisDTO>> tennisColletion = CollectionModel.of(
            tennis.stream().map(EntityModel::of).toList()
        );
        createCollectionLink(tennisColletion);
        return new ResponseEntity<>(tennisColletion, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TennisDTO>>update(@PathVariable("id")int id, @RequestBody TennisDTO dto){
        dto.setId(id);
        TennisDTO updatedTenis = service.update(dto);
        createLinks(updatedTenis);
        EntityModel<TennisDTO> entityModel = EntityModel.of(updatedTenis);
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void createLinks(TennisDTO dto){
        dto.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(this.getClass()).findById(dto.getId())
                ).withSelfRel()
        );

        dto.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(this.getClass()).delete(dto.getId())
                ).withRel("delete")
        );


        if (dto.getCategoryId() != null && dto.getCategoryId() > 0) {
            dto.add(
                    WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(CategoryController.class).findById(dto.getCategoryId())
                    ).withRel("category")
            );
        }
    }

    private void createCollectionLink(CollectionModel<EntityModel<TennisDTO>> tennis) {

        tennis.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(this.getClass()).findAll()
                ).withRel(IanaLinkRelations.COLLECTION)
        );
    }

}
