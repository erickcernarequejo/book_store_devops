package com.ecernare.libros.exception;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private LocalDateTime dateTime;
    private String message;
    private String details;

}
