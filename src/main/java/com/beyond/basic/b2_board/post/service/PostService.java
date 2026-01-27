package com.beyond.basic.b2_board.post.service;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.dtos.PostCreateDto;
import com.beyond.basic.b2_board.post.dtos.PostDetailDto;
import com.beyond.basic.b2_board.post.dtos.PostListDto;
import com.beyond.basic.b2_board.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public void save(PostCreateDto dto) {
//       Author author = authorRepository.findByEmail(dto.getAuthorEmail())
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        System.out.println(email);
       Author author = authorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("없는 이메일 입니다."));

        Post post = dto.toEntity(author);
        this.postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Page<PostListDto> findAll(Pageable pageable) {
//        List<Post>postList = postRepository.findByDelYn("N");
//        List<Post>postList = postRepository.findAllFetchInnerJoin();
        Page<Post> postList = postRepository.findAll(pageable);
//        List<PostListDto>dtoList = new ArrayList<>();
//        for (Post p : postList){
//            PostListDto dto  = PostListDto.fromEntity(p);
//            dtoList.add(dto);
//        }
//        Page 객체 안에 Entity에서 Dto로 쉽게 변환할수 있는 편의 제공
        return postList.map(p->PostListDto.fromEntity(p));
    }

    @Transactional(readOnly = true)
    public PostDetailDto findById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.orElseThrow(() -> new EntityNotFoundException("entity is not found"));
        if ("Y".equals(post.getDelYn())) {
            throw new NoSuchElementException("삭제된 게시글입니다.");
        }
//        Author author = authorRepository.findById(post.getAuthorId()).orElseThrow(()->new EntityNotFoundException("X"));
//        PostDetailDto dto = PostDetailDto.fromEntity(post,author);
        PostDetailDto dto = PostDetailDto.fromEntity(post);
        return dto;
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("entity is not found"));
        post.deletePost();


    }
}
