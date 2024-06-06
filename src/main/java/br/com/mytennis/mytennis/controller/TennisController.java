package br.com.mytennis.mytennis.controller;

import br.com.mytennis.mytennis.dto.TennisDTO;
import br.com.mytennis.mytennis.service.TennisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tennis")
public class TennisController {

    @Autowired
    private TennisService service;

    @PostMapping
    public TennisDTO create(@RequestBody TennisDTO dto){ return service.create(dto);}

    @GetMapping("/find")
    public ResponseEntity<PagedModel<TennisDTO>> findByName(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "page", required = true, defaultValue = "0") int page,
            @RequestParam(value = "size", required = true, defaultValue = "10") int size,
            PagedResourcesAssembler<TennisDTO> assembler
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<TennisDTO> tennis = service.findByName(name, pageable);

        return new ResponseEntity(assembler.toModel(tennis), HttpStatus.OK);
    }

}
