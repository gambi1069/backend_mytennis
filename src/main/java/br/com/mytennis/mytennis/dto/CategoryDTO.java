package br.com.mytennis.mytennis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO extends RepresentationModel<CategoryDTO> {

    private int id;
    private String name;
    private String description;

//    public String getFullCategory(){return this.name + "-"+ this.description;}

}
