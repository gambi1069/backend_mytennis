package br.com.mytennis.mytennis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TennisDTO {

    private int id;

    private String name;

    private String brand;

    private String color;

    private int number;

    private CategoryDTO category;

}
