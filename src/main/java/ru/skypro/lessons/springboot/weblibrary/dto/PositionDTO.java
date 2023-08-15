package ru.skypro.lessons.springboot.weblibrary.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PositionDTO {

    private Integer id;

    private String position_name;

}
