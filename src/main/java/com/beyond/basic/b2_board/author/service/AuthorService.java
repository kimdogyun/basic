package com.beyond.basic.b2_board.author.service;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.repository.AuthorJdbcRepository;
import com.beyond.basic.b2_board.author.repository.AuthorMemoryRepository;
import com.beyond.basic.b2_board.author.repository.AuthorMybatisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

//Component 어노테이션을 통행 싱글톤(단하나의)객체가 생성되고, 스프링에 의해 스프링컨텍스트에서 관리
@Service
//반드시 초기화 되어야 하는 필드(final 변수 등)를 대상으로 생성자를 자동 생성
//@RequiredArgsConstructor
public class AuthorService {
//   의존성주입(DI)방법1. 필드주입 : Autowired 어노테이션사용(간편방식)
//    @Autowired
//    private AuthorRepository authorRepository;

    //    의존성주입(DI)방법2. 생성자주입방식 (가장 많이 사용되는 방식)
//    장점1)final을 통해 상수로 사용 가능(안정성향상)
//    장점2)다형성 구현가능(interface 사용가능)
//    장점3)순환참조방지(컴파일타임에 에러check)
    private final AuthorMybatisRepository authorMemoryRepository;

    //    생성자가 하나밖에 없을떄에는 Autowired생략가능
    @Autowired
    public AuthorService(AuthorMybatisRepository authorMemoryRepository) {
        this.authorMemoryRepository = authorMemoryRepository;
    }
//    의존성주입방법3.@RequiredArgsConstructor 어노테이션 사용
//    반드시 초기화되어야 하는 필드를 선언하고,@RequiredArgsConstructor어노테이션 선언시 생성자주입방식으로 의존성이 주입
//    단점 : 다형성 설계는 불가
//    private final AuthorRepository authorRepository;

    public void save(AuthorCreateDto dto) {
//       방법1. 객체 직접 조립
//       1-1) 생성자만을 활용한 객체 조립
//       Author author = new Author(null, dto.getName(), dto.getEmail(), dto.getPassword());
//       1-2) Builder 패턴을 활용해 객체 조립
//       장점 : 1)매개변수의 개수의 유연성 2)매개변수의 순서의 유연성
//        Author author = Author.builder()
//                .email(dto.getEmail())
//                .name(dto.getName())
//                .password(dto.getPassword())
//                .build();
//       방법2. toEntity, fromEntity 패턴을 통한 객체 조립
//       객체조립이라는 반복적이 작업을 별도의 코드로 떄어내 공통화

//        email중복 여부 검증
        if (authorMemoryRepository.findByEmail(dto.getEmail()).isPresent()){
       throw new IllegalArgumentException("email 중복입니다");
        }
         Author author = dto.toEntity();
        authorMemoryRepository.save(author);
    }

    public AuthorDetailDto findById(Long id) {
        Optional<Author> optAtuhor = authorMemoryRepository.findById(id);
        Author author = optAtuhor.orElseThrow(() -> new NoSuchElementException("entity is not found"));
//        dto  조립
//        AuthorDetailDto dto = AuthorDetailDto.builder()
//                .id(author.getId())
//                .name(author.getName())
//                .email(author.getEmail())
//                .password(author.getPassword())
//                .build();

//        fromEntity는 아직 dto 객체가 만들어지지 않은 상태이므로 static 메서드로 설계
        AuthorDetailDto dto = AuthorDetailDto.formEntity(author);
        return dto;
    }

    public List<AuthorListDto> findAll() {
//        List<Author> authorList = authorRepository.findAll();
//        List<AuthorListDto> dtoList = new ArrayList<>();
//        for (Author author : authorList) {
//            AuthorListDto dto = new AuthorListDto(author.getId(), author.getName(), author.getEmail());
//            dtoList.add(dto);
//        }
//        for (Author author : authorList) {
//            AuthorListDto dto = AuthorListDto.fromEntity(author);
//            dtoList.add(dto);
//        }
        return authorMemoryRepository.findAll().stream().map(a -> AuthorListDto.fromEntity(a)).collect(Collectors.toList());
    }

    public void delete(Long id) {
//        데이터 조회 후 없다면 예외처리
        Optional<Author> opta = authorMemoryRepository.findById(id);
        Author author = opta.orElseThrow(() -> new NoSuchElementException("NO"));
//    삭제작업
        authorMemoryRepository.delete(id);
    }


    }


