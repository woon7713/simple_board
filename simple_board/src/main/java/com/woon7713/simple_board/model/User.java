package com.woon7713.simple_board.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="users") // name 지정안하면 클래스명으로 디폴트로 테이블명 지정됨.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50) // @Valid의 길이제한과 다르게 해당 length 제한은 sql단에서
    private String username;

    @Column(nullable = false)
    private String password;


}