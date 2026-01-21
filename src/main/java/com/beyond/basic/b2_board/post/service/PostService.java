package com.beyond.basic.b2_board.post.service;

import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.dtos.PostCreateDto;
import com.beyond.basic.b2_board.post.dtos.PostDetailDto;
import com.beyond.basic.b2_board.post.dtos.PostListDto;
import com.beyond.basic.b2_board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public void save(PostCreateDto dto){
        if (authorRepository.findByEmail(dto.getAuthorEmail()).isEmpty()) {
            throw new IllegalArgumentException("없는 이메일 입니다.");
        }
        Post post = dto.toEntity();
        this.postRepository.save(post);
    }
    @Transactional(readOnly = true)
    public List<PostListDto> findAll(){
        return postRepository.findByDelYn("N").stream()
                .map(a-> PostListDto.fromEntity(a))
                .collect(Collectors.toList());

    }
    @Transactional(readOnly = true)
    public PostDetailDto findById(Long id){
        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.orElseThrow(()->new NoSuchElementException("entity is not found"));
        if ("Y".equals(post.getDelYn())) {
            throw new NoSuchElementException("삭제된 게시글입니다.");
        }
        PostDetailDto dto = PostDetailDto.fromEntity(post);
        return dto;
    }
    public void delete(Long id){
        Post post = postRepository.findById(id)
        .orElseThrow(()->new NoSuchElementException("entity is not found"));
        post.deletePost();

    }
}
