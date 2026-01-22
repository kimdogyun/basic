package com.beyond.basic.b2_board.post.dtos;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostListDto {
    private Long id;
    private String title;
    private String category;
    private String authorEmail;

    public static PostListDto fromEntity(Post post, Author author){
        return PostListDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .category(post.getCategory())
                .authorEmail(author.getEmail())
                .build();
    }
}
