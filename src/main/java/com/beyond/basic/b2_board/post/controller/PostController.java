package com.beyond.basic.b2_board.post.controller;

import com.beyond.basic.b2_board.post.dtos.PostCreateDto;
import com.beyond.basic.b2_board.post.dtos.PostListDto;
import com.beyond.basic.b2_board.post.dtos.PostDetailDto;
import com.beyond.basic.b2_board.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//-게시글 : id, title(not null), contents(3000자이하), category, authorEmail(not null), delYn(String)
//        -fromEntity, toEntity 활용
//
//1.게시글등록(/post/create)
//-title, contents, category, authorEmail
//-authorEmail 존재 유효성 체크 => authorRepository 주입 및 findByEmail 호출
//
//2.게시글목록조회(/posts)
//-id, title, category, authorEmail
//-삭제된 데이터 조회 제외 => List<Post> findByDelYn(String delYn)
//
//3.게시글상세조회(/post/1)
//-id, title, category, contents, authorEmail
//
//4.게시글삭제(/post/1) => DeleteMapping 쓰면서 실질은 update작업

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void PostCreate(@RequestBody PostCreateDto dto) {
        postService.save(dto);
    }

    @GetMapping("/posts")
    public List<PostListDto> findAll() {
        List<PostListDto> dtoList = postService.findAll();
        return dtoList;
    }

    @GetMapping("/post/{id}")
    public PostDetailDto findById(@PathVariable Long id) {
        PostDetailDto dto = postService.findById(id);
        return dto;
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "ok";
    }
}
