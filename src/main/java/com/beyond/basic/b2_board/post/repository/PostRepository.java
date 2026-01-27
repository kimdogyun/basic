package com.beyond.basic.b2_board.post.repository;

import com.beyond.basic.b2_board.post.domain.Post;
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
}
