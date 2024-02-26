package com.backend.backendleeyejin.backend.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Builder
@Getter
@Setter
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long index;

    private String accessToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_index", referencedColumnName = "index")
    private Member member;

    @CreationTimestamp
    private LocalDateTime createDate;
}
