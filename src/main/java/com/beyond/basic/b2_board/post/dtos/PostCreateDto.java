package com.beyond.basic.b2_board.post.dtos;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.post.domain.Post;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreateDto {
    private String title;
    private String contents;
    private String category;
    private String authorEmail;

    public Post toEntity(Author author){
        return Post.builder()
                .title(this.title)
                .contents(this.contents)
                .category(this.category)
                .authorId(author.getId())
                .build();
    }
}
