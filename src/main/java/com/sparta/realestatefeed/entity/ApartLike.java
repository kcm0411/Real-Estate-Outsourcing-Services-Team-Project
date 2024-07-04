package com.sparta.realestatefeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "apart_like")
@Getter
@NoArgsConstructor
public class ApartLike extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apart_like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "apart_id", nullable = false)
    private Apart apart;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public ApartLike(Apart apart, User user) {
        this.apart = apart;
        this.user = user;
    }
}
