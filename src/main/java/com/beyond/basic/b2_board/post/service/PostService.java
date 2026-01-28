package com.beyond.basic.b2_board.post.service;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.dtos.PostCreateDto;
import com.beyond.basic.b2_board.post.dtos.PostDetailDto;
import com.beyond.basic.b2_board.post.dtos.PostListDto;
import com.beyond.basic.b2_board.post.dtos.PostSearchDto;
import com.beyond.basic.b2_board.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<PostListDto> findAll(Pageable pageable, PostSearchDto serchDto) {
//        List<Post>postList = postRepository.findByDelYn("N");
//        List<Post>postList = postRepository.findAllFetchInnerJoin();
//        Page<Post> postList = postRepository.findAll(pageable);
//        검색을 위한 Specification 객체 조립
        Specification<Post> specification = new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                predicateList.add(criteriaBuilder.equal(root.get("delyn"),"N"));
                predicateList.add(criteriaBuilder.equal(root.get("appointment"),"N"));
//                root : 엔티티의 컬럼명을 접근하기 위한 객체, criteriaBuilder : 쿼리를 생성하기 위한 객체
                if (serchDto.getTitle() != null){
                    predicateList.add(criteriaBuilder.like(root.get("title"),"%" + serchDto.getTitle() + "%"));
                }
                if (serchDto.getCategory() != null){
                    predicateList.add(criteriaBuilder.equal(root.get("category"),serchDto.getCategory()));
                }
                if (serchDto.getContents() != null){
                    predicateList.add(criteriaBuilder.like(root.get("contents"),"%"+serchDto.getContents()+"%"));
                }
                Predicate[] predicateArr = new Predicate[predicateList.size()];
                for (int i = 0; i<predicateArr.length; i++){
                    predicateArr[i] = predicateList.get(i);
                }
//                Predicate에는 검색조건들이 담길것이고, 이 predicate List를 한줄의 predicate로 조힙
                Predicate predicate = criteriaBuilder.and(predicateArr);
                return predicate;
            }
        };
        Page<Post> postList = postRepository.findAll(specification, pageable);
//        List<PostListDto>dtoList = new ArrayList<>();
//        for (Post p : postList){
//            PostListDto dto  = PostListDto.fromEntity(p);
//            dtoList.add(dto);
//        }
//        Page 객체 안에 Entity에서 Dto로 쉽게 변환할수 있는 편의 제공
        return postList.map(p -> PostListDto.fromEntity(p));
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
