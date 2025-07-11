package com.woon7713.simple_board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
//@RequiredArgsConstructor
public class SignupDto {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min=6, max=100)
    private String password;


}
