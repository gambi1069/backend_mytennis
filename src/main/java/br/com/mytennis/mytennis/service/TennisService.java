package br.com.mytennis.mytennis.service;

import br.com.mytennis.mytennis.dto.TennisDTO;
import br.com.mytennis.mytennis.exceptions.ResourceNotFoundException;
import br.com.mytennis.mytennis.mapper.CustomModelMapper;
import br.com.mytennis.mytennis.model.CategoryModel;
import br.com.mytennis.mytennis.model.TennisModel;
import br.com.mytennis.mytennis.repository.TennisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
public class TennisService {

    @Autowired
    private TennisRepository repository;

    public TennisDTO create(TennisDTO dto){
        TennisModel model = CustomModelMapper.toTennisModel(dto);
        TennisModel savedModel = repository.save(model);
        return CustomModelMapper.toTennisDTO(savedModel);
    }

    public Page<TennisDTO>findByName(String name, Pageable pageable){
        Page<TennisModel> page = repository.findByNameContainsIgnoreCaseOrderByName(name, pageable);
        return page.map(CustomModelMapper::toTennisDTO);
    }

    public List<TennisDTO> findAll(){
        List<TennisModel> models = repository.findAll();
        return models.stream()
                .map(CustomModelMapper::toTennisDTO)
                .collect(Collectors.toList());
    }

    public TennisDTO findById(int id){
        Optional<TennisModel> optionalModel =repository.findById(id);
        if (optionalModel.isPresent()){
            return CustomModelMapper.toTennisDTO(optionalModel.get());
        }else{
            throw new ResourceNotFoundException("Não foi encontrado: "+ id);
        }
    }

    public TennisDTO update(TennisDTO dto){
        Optional<TennisModel> optionalModel =repository.findById(dto.getId());
        if (optionalModel.isPresent()){
            TennisModel model = optionalModel.get();
            model.setName(dto.getName());
            model.setBrand(dto.getBrand());
            model.setColor(dto.getColor());
            model.setNumber(dto.getNumber());
            if (dto.getCategoryId() != null){
                CategoryModel category= new CategoryModel();
                category.setId(dto.getCategoryId());
                model.setCategory(category);
            }
            TennisModel updatedModel = repository.save(model);
            return CustomModelMapper.toTennisDTO(updatedModel);
        }else{
            throw new ResourceNotFoundException("Não foi encontrado"+ dto.getId());
        }
    }

    public void delete(int id){
        TennisModel model = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Não foi encontrado"+ id));
        repository.delete(model);
    }

}
