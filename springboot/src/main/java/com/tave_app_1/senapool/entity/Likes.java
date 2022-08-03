package com.tave_app_1.senapool.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likes")
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_pk")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_pk")
    private PlantDiary diary;

    @Builder
    public Likes(User user, PlantDiary diary){
        this.diary = diary;
        this.user = user;
    }

    @Transient
    private long likesCount;

    public void updateLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }
}