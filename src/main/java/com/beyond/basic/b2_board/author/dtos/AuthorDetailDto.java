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
public class AuthorDetailDto {
    private Long id;
    private String name;
    private String email;
    private int postCount;
    private String password;

    public static AuthorDetailDto formEntity(Author author, int postcount) {
        return AuthorDetailDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .postCount(postcount)
                .password(author.getPassword())
                .build();

    }

}
