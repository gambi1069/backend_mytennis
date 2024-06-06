package br.com.mytennis.mytennis.mapper;

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
            List<Origin> originList, Class<Destination> destinationClass
    ){
        List<Destination> destinationList = new ArrayList<Destination>();
        for (Origin o : originList){
            destinationList.add(mapper.map(o, destinationClass));
        }
        return destinationList;
    }

}
