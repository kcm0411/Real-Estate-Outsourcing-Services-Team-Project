package com.sparta.realestatefeed.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follow")
@NoArgsConstructor
public class Follow extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "followee_id", nullable = false)
    private User followee;

    public Follow(User followee, User follower) {
        this.followee = followee;
        this.follower = follower;
    }
}
