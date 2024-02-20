package com.ecernare.libros.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO implements Serializable {

    private Long id;

    private String name;

    private String genre;

    private int age;

}
