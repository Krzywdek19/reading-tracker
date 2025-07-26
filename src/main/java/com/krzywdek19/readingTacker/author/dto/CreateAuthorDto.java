package com.krzywdek19.readingTacker.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAuthorDto {
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String description;
}
