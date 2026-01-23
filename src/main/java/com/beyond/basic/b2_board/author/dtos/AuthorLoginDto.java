package com.beyond.basic.b2_board.author.dtos;

import com.beyond.basic.b2_board.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorLoginDto {
    private String email;
    private String password;

    public AuthorLoginDto fromEntity(Author author){
        return AuthorLoginDto.builder()
                .email(author.getEmail())
                .password(author.getPassword())
                .build();
    }
}
