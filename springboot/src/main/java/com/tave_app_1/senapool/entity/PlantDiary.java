package com.tave_app_1.senapool.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "plantDiary")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlantDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_pk")
    private Long plantDiaryPK;

    //일기 제목
    @Column(nullable = false)
    private String title;

    //일기 내용
    @Column(nullable = false)
    private String content;

    //업로드 사진
    @Column
    private String diaryImage;

    //공개 여부
    @Column
    private Boolean publish;

    //생성 시간을 지정
    @Column
    private LocalDate createDate;

//    @PrePersist //DB에 insert시 이 함수와 함께 실행
//    public void createDate(){
//        this.createDate = LocalDateTime.now();
//    }

    //식물 정보 매핑
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "plant_pk")
    private MyPlant myPlant;

    //유저 정보 매핑
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_pk")
    private User user;

    public PlantDiary(User user,MyPlant myPlant,String title,String content,Boolean publish,LocalDate createDate,String diaryImage){
        this.user = user;
        this.myPlant = myPlant;
        this.title = title;
        this.content = content;
        this.publish = publish;
        this.createDate = createDate;
        this.diaryImage = diaryImage;
    }


    public void update(String title, String content,String diaryImage,Boolean publish,LocalDate createDate) {
        this.title=title;
        this.content=content;
        this.diaryImage=diaryImage;
        this.publish=publish;
        this.createDate = createDate;
    }

    //좋아요 개수 매핑
    @JsonIgnoreProperties({"diary"})
    @JsonBackReference
    @OneToMany(mappedBy = "diary", cascade = CascadeType.REMOVE)
    private List<Likes> likesList;

    @Transient
    private long likesCount;

    @Transient
    private boolean likesState;

    @Transient
    private String diaryImageUrl;

    @Transient
    private String userID;

    @Transient
    private String userImage;

    public void updateLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public void updateLikesState(boolean likesState) {
        this.likesState = likesState;
    }

    public void updateDiaryImageUrl(String diaryImageUrl) { this.diaryImageUrl = diaryImageUrl; }

    public void setUserInfo(){this.userID = this.user.getUserId(); this.userImage = "http://ec2-3-39-104-218.ap-northeast-2.compute.amazonaws.com:8080/images/user/" + this.user.getUserImageName();}

}
