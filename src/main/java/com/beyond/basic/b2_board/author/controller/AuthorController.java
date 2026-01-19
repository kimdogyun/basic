package com.beyond.basic.b2_board.author.controller;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//1.회원가입 - url : /author/create -json
//2.회원목록조회 - url : /author/list -json ,List<Author>
//3.회원상세조회 - url :  /author/ - id조회 ,id만 출력
//4.회원탈퇴 - DeleteMapping - id 삭제 ,id만 출력
@RestController
@RequestMapping("/author")

public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    //회원가입
    @PostMapping("/create")
    public String create(@RequestBody AuthorCreateDto dto) {
        authorService.save(dto);
        return "ok";
    }
    //    회원목록조회
    @GetMapping("/list")
    public List<AuthorListDto> findAll() {
        List<AuthorListDto> dtoList = authorService.findAll();
        return dtoList;
    }
    //    회원상세조회
    @GetMapping("/{id}")
    public AuthorDetailDto findById(@PathVariable long id) {
        AuthorDetailDto dto = authorService.findById(id);
        return dto;
    }
    //    회원삭제
    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        System.out.println(id);
        return "ok";
    }
}
