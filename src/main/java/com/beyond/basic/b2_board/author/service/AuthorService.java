package com.beyond.basic.b2_board.author.service;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class AuthorService {
    private AuthorRepository authorRepository;
    public AuthorService(){
        this.authorRepository = new AuthorRepository();
    }
    public void save(AuthorCreateDto dto){
//        객체 직접 조립
        Author author = new Author(null,dto.getName(), dto.getEmail(), dto.getPassword());
        authorRepository.save(author);
    }
    public List<AuthorListDto> findAll(){
        List<Author>authorList = authorRepository.findAll();
        List<AuthorListDto> dtoList = new ArrayList<>();
        for (Author author : authorList){
            AuthorListDto dto = new AuthorListDto(author.getId(),author.getName(), author.getEmail());
            dtoList.add(dto);
        }
        return dtoList;

    }
    public AuthorDetailDto findById(Long id){
        authorRepository.findById(id);
        Optional<Author> optAtuhor = authorRepository.findById(id);
        Author author = optAtuhor.orElseThrow(()-> new NoSuchElementException("entity is not found"));
//        dto  조립
        AuthorDetailDto dto = new AuthorDetailDto(author.getId(), author.getName(), author.getEmail(), author.getPassword());
        return dto;

    }
}
