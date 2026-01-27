package com.beyond.basic.b2_board.author.controller;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.*;
import com.beyond.basic.b2_board.author.service.AuthorService;
import com.beyond.basic.b2_board.common.auth.JwtTokenProvider;
import com.beyond.basic.b2_board.common.dtos.CommonErrorDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

//1.회원가입 - url : /author/create -json
//2.회원목록조회 - url : /author/list -json ,List<Author>
//3.회원상세조회 - url :  /author/ - id조회 ,id만 출력
//4.회원탈퇴 - DeleteMapping - id 삭제 ,id만 출력
@RestController
@RequestMapping("/author")

public class AuthorController {
    private final AuthorService authorService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthorController(AuthorService authorService, JwtTokenProvider jwtTokenProvider) {
        this.authorService = authorService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //회원가입
    @PostMapping("/create")
//    dto에 있는 validation 어노테이션과 @Valid가 한쌍
    public ResponseEntity<?> create(@RequestBody @Valid AuthorCreateDto dto) {
//        아래 예외처리는 ExceptionHandler에서 전역적으로 예외처리
//        try {
//            authorService.save(dto);
//            return ResponseEntity.status(HttpStatus.CREATED).body("ok");
//        }catch (IllegalArgumentException e){
//            e.printStackTrace();
//            CommonErrorDto commonErrorDto = CommonErrorDto.builder()
//                    .status_code(400)
//                    .error_message(e.getMessage())
//                    .build();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonErrorDto);
//        }
        authorService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

    //    회원목록조회
    @GetMapping("/list")
//    @PreAuthorize : Authentication 객체 안의 권한정보를 확인하는 어노테이션
//    2개 이상의 Role을 허용하는 경우 :  "hasRole('ADMIN') or hasRole('SELLER')"
    @PreAuthorize("hasRole('ADMIN')")
    public List<AuthorListDto> findAll() {
        List<AuthorListDto> dtoList = authorService.findAll();
        return dtoList;
    }

    //    회원상세조회
//    아래와 같이 http응답 body를 분기처리 한다 하더라도 상태코드는 200으로 고정
//    @GetMapping("/{id}")
//    public Object findById(@PathVariable long id) {
//        try {
//            AuthorDetailDto dto = authorService.findById(id);
//            return dto;
//        }catch (NoSuchElementException e){
//            e.printStackTrace();
//            return CommonErrorDto.builder()
//                    .status_code(404)
//                    .error_message(e.getMessage())
//                    .build();
//        }
//    }
    //    회원삭제
    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        authorService.delete(id);
        return "ok";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            AuthorDetailDto dto = authorService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            CommonErrorDto dto = CommonErrorDto.builder()
                    .status_code(404)
                    .error_message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        }
    }

    //    비밀번호 수정
    @PatchMapping("/update/password")
    public void updatepw(@RequestBody AuthorUpdatePwDto dto) {
        authorService.updatepw(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthorLoginDto dto) {
        Author author =  authorService.login(dto);
//        토큰 생성 및 리턴
        String token = jwtTokenProvider.createToken(author);
        return token;
    }
    @GetMapping("/myInfo")
//    public ResponseEntity<?> myInfo(){
    public ResponseEntity<?> myInfo(@AuthenticationPrincipal String principal){
        System.out.println(principal);
        AuthorDetailDto dto = authorService.myInfo();
        return ResponseEntity
                .status(HttpStatus.OK).body(dto);

    }
}
