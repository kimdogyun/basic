package com.beyond.basic.b2_board.author.domain;

import com.beyond.basic.b2_board.common.BassTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
//@Builder 패턴은 AllArgs생성자 기반으로 동작
@Builder
//JPA에게 Entity관리를 위임하기 위한 어노테이션
@Entity
public class Author extends BassTimeEntity {
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

    public void updatePssword(String password){
        this.password = password;
    }
}
