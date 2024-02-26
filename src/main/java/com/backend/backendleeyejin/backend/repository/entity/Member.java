package com.backend.backendleeyejin.backend.repository.entity;

import com.backend.backendleeyejin.backend.controller.dto.SignUpDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long index;

    @Column(unique = true)
    private String userId;

    private String password;

    private String name;

    @Column(unique = true)
    private String regNo;

    @CreationTimestamp
    private LocalDateTime createDate;

    public static Member toMember(SignUpDTO signupdto){
        Member member = new Member();
        member.setUserId(signupdto.getUserId());
        member.setPassword(signupdto.getPassword());
        member.setName(signupdto.getName());
        member.setRegNo(signupdto.getRegNo());
        return member;
    }
}
