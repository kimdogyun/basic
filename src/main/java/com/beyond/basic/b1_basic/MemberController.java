package com.beyond.basic.b1_basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//Controller 어노테이션을 통해 스프링에 의해 객체가 생성되고, 관리되어 개발자가 직접 객체를 생성할필요 없음.
//Controller 어노테이션과 url매핑을 통해 사용자의 요청이 메서드로 분기처리
@Controller
@RequestMapping("/member")
public class MemberController {
//    get요청 리턴의 case : text, json, html(mvc)
//    case1. 서버가 사용자에게 text데이터 return
    @GetMapping("")
//    data(text, json)를 리턴할때에는 ResponseBody사용 , 화면(html)을 리턴할떄에는 ResponseBody 생략
//    Controller + ResponseBody = RestController
    @ResponseBody
    public String textDataReturn() {
        return "hongildong1";

    }

//    case2. 서버가 사용자에게 json 형식의 문자데이터 return
    @GetMapping("/json")
    @ResponseBody
    public Member jsonDataReturn() throws JsonProcessingException {
        Member m1 = new Member("h1","h1@naver.com");
//        직접 json을 직렬화 할 필요 없이 return, 타입에 객체가 있다면 자동으로 직렬화
//        ObjectMapper o1 = new ObjectMapper();
//        String data = o1.writeValueAsString(m1);
        return m1;
    }
//    case3. 서버가 사용자에게 html을 return
    @GetMapping("/html")
//    ResponseBody 가 없고, return 타입이 String인 경우 스프링은 templates폴더 밑에 simple_html.html을 찾아서 리턴
//    타임리프 의존성이 필요
    public String htmlReturn(){
        return "simple_html";

    }
}