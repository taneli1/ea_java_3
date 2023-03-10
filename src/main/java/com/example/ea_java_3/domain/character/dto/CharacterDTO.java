package com.example.ea_java_3.domain.character.dto;

import lombok.Data;

import java.util.Optional;
import java.util.Set;

@Data
public class CharacterDTO {
    private int id;
    private String name;
    private String alias;
    private String gender;
    private String picture;
    private Set<Integer> movieIds;
}
