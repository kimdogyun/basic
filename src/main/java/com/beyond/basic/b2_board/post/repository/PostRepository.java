package com.beyond.basic.b2_board.post.repository;

import com.beyond.basic.b2_board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByDelYn(String delYn);
//    List<Post> findAllByAuthorIdAndDelYn(Long authorId,String delYn);

//    jpql을 활용한 일반 inner join : N+1문제 해결X
//    jpql과 raw쿼리의 차이
//    1. jpql을 사용한 inner join시, 별도의 on 조건 필요 X
//    2. jpql은 컴파일타입에 에러를 check
//    순수raw : select p* from Post p inner join author a on a. id = p.author_id;
    @Query("select p from Post p inner join p.author")
    List<Post> findAllInnerJoin();

//    jpql을 활용한 inner join(fetch) : N+1 문제 해결O
//    순수row : select * from Post p inner join author a on a. id = p.author_id;
    @Query("select p from Post p inner join fetch p.author")
    List<Post> findAllFetchInnerJoin();

//    Page객체 안에는 content(List<post>), totalPages, totalElement등의 정보 포함
//    Page<Post> findAllByDelYn(Pageable pageable, String DelYn);

//    검색 + 페이징처리까지 할 경우, 아래와 같이 매개변수 선언(Specification, Pageable 순서 - SimpleJpaRepository에서 정의)
//    @EntityGraph(attributePaths =  "author") ->fetch join 어노테이션
    Page<Post> findAll(Specification<Post> specification, Pageable pageable);

//    예약 글쓰기를 위한 findAllByAppointment 별도 추가
    List<Post>findAllByAppointment (String appointment);

}
