package com.sparta.realestatefeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qna_like")
@Getter
@NoArgsConstructor
public class QnALike extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "qna_id", nullable = false)
    private QnA qnA;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public QnALike(QnA qnA, User user) {
        this.qnA = qnA;
        this.user = user;
    }
}
