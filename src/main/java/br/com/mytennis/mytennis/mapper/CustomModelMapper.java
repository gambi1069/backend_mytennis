package br.com.mytennis.mytennis.mapper;

import br.com.mytennis.mytennis.dto.CategoryDTO;
import br.com.mytennis.mytennis.dto.TennisDTO;
import br.com.mytennis.mytennis.model.CategoryModel;
import br.com.mytennis.mytennis.model.TennisModel;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CustomModelMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static <Origin, Destination> Destination parseObject(
            Origin originClass, Class<Destination> destinationClass){
        return mapper.map(originClass, destinationClass);
    }

    public static <Origin, Destination> List<Destination> parseObjectList(
            List<Origin> originList, Class<Destination> destinationClass){
        List<Destination> destinationList = new ArrayList<Destination>();
        for (Origin o : originList){
            destinationList.add(mapper.map(o, destinationClass));
        }
        return destinationList;
    }

    public static TennisDTO toTennisDTO(TennisModel model){
        TennisDTO dto = parseObject(model, TennisDTO.class);
        if(model.getCategory() != null){
            dto.setCategoryId(model.getCategory().getId());
        }
        return dto;
    }

    public static TennisModel toTennisModel(TennisDTO dto){
        TennisModel model = parseObject(dto, TennisModel.class);
        if (dto.getCategoryId() != null && dto.getCategoryId() > 0){
            CategoryModel category = new CategoryModel();
                category.setId(dto.getCategoryId());
                model.setCategory(category);
        }
        return model;
    }

    public static CategoryDTO toCategoryDTO(CategoryModel model) {return parseObject(model, CategoryDTO.class);
    }

    public static CategoryModel toCategoryModel(CategoryDTO dto){
        return parseObject(dto, CategoryModel.class);
    }

}
