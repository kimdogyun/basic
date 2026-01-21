package com.beyond.basic.b2_board.post.dtos;

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
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    @Size(max = 3000)
    private String contents;
    private String category;
    @Column(nullable = false)
    private String authorEmail;
    private String delYn;

    public Post toEntity(){
        return Post.builder()
                .title(this.title)
                .contents(this.contents)
                .category(this.category)
                .delYn("N")
                .authorEmail(this.authorEmail).build();
    }
}
