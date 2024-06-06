package br.com.mytennis.mytennis.service;

import br.com.mytennis.mytennis.dto.TennisDTO;
import br.com.mytennis.mytennis.mapper.CustomModelMapper;
import br.com.mytennis.mytennis.model.TennisModel;
import br.com.mytennis.mytennis.repository.TennisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TennisService {

    @Autowired
    private TennisRepository repository;

    public TennisDTO create(TennisDTO dto){
        var model = CustomModelMapper.parseObject(dto, TennisModel.class);
        return CustomModelMapper.parseObject(repository.save(model), TennisDTO.class);
    }

    public Page<TennisDTO>findByName(String name, Pageable pageable){
        Page<TennisModel> page = repository.findByNameContainsIgnoreCaseOrderByName(name, pageable);
        return page.map(obj -> CustomModelMapper.parseObject(obj, TennisDTO.class));
    }

}
