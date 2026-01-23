package com.beyond.basic.b2_board.author.domain;

import com.beyond.basic.b2_board.common.domain.BaseTimeEntity;
import com.beyond.basic.b2_board.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
//@Builder 패턴은 AllArgs생성자 기반으로 동작
@Builder
//JPA에게 Entity관리를 위임하기 위한 어노테이션
@Entity
public class Author extends BaseTimeEntity {
    @Id // pk 설정
//    identity : auto_increment 설정. auto:id생성전략을 jpa에게 자동설정하도록 위임.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long -> bigint , String -> varchar
    private Long id;
//    변수명이 컬럼명으로 그대로 생성. camelcase 는 언더스코어로 변경 예) nickName -> nick_name
    private String name;
//    길이를 varchar(50), 제약조건(unique,not null) 설정
//    길이는 디폴트 varchar(255)
    @Column(length = 50, unique = true, nullable = false)
    private String email;

//    @Column(name = "pw") : 컬럼명 변경이 가능하나, 일반적으로 일치시킴
//    @Setter
    private String password;
//    enum타입은 내부적으로 숫자값을 가지고 있으나, 문자형태로 저장하겠다는 어노테이션.
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;
//    일반적으로 OneToMany는 선택사항. ManyToOne 필수사항
//    mappedBy : ManyToOne쪽의 변수명을 문자열로 지정. ->조회해야할 컬럼을 명시
//    연관관계(fk)의 주인설정 -> 연관관계의 주인은 author변수를 가지고 있는 Post에 있음을 명시
//    orphanRemoval : 자식의 자식까지 연쇄적으로 삭제해야할 경우 모든 부모에 orphanRemoval = true 옵션 추가
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Post> postList = new ArrayList<>();
    @OneToOne(mappedBy = "author",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Address address;

    public void updatePassword(String password) {
        this.password = password;
    }
}
