package com.ecernare.libros.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO implements Serializable {

    @Schema(title = "Identification code", example = "1")
    @Digits(integer = 3, fraction = 0, message = "Max 5 digits, author identification code")
    @PositiveOrZero(message = "Identification code not be negative")
    private Long id;

    @Schema(title = "Author Name", example = "Joana Nimar")
    @Size(max = 80, message = "Max 80 characters, author name")
    @NotNull
    private String name;

    @Schema(title = "Author Genre", example = "History")
    @Size(max = 80, message = "Max 80 characters, author genre")
    @NotNull
    private String genre;

    @Schema(title = "Age in years", example = "45")
    @Digits(integer = 3, fraction = 0, message = "Max 3 digits, age in years")
    @PositiveOrZero(message = "Age must not be negative")
    private int age;

    @JsonProperty(value = "books")
    @Valid
    private List<BookDTO> booksDTO = new ArrayList<>();

}
