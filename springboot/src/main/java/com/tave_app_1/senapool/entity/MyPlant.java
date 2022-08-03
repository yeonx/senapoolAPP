package com.tave_app_1.senapool.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "myPlant")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPlant extends BaseTime{

    @Id @GeneratedValue
    @Column(name = "plant_pk")
    private Long plantPK;

    @Column(nullable = false, name = "plant_name")
    private String plantName;

    @Column(nullable = false, name = "plant_type")
    private String plantType;

    @Column(nullable = false, name = "water_period")
    private Integer waterPeriod;

    @Column(nullable = false, name = "plant_Image")
    private String plantImage;

    /*
     nullable 해제
     */
    @Column(nullable = true, name = "start_day")
    private LocalDate startDay;

    /*
     nullable 해제
     */
    @Column(nullable = true, name = "last_water")
    private LocalDate lastWater;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_pk")
    private User user;

    @JsonManagedReference
    @JsonBackReference
    @OneToMany(mappedBy = "myPlant", cascade = CascadeType.REMOVE)
    private List<PlantDiary> plantDiaryList;

    public MyPlant(User user, String plantName, String plantType, Integer waterPeriod, LocalDate lastWater, LocalDate startDay, String plantImage) {
        this.user = user;
        this.plantName = plantName;
        this.plantType = plantType;
        this.waterPeriod = waterPeriod;
        this.plantImage = plantImage;
        this.lastWater = lastWater;
        this.startDay = startDay;
    }

    public void updatePlant(String plantImage, String plantName, String plantType, Integer waterPeriod) {
        this.plantImage = plantImage;
        this.plantName = plantName;
        this.plantType = plantType;
        this.waterPeriod = waterPeriod;
    }

    public void updatePlant(String plantName, String plantType, Integer waterPeriod) {
        this.plantName = plantName;
        this.plantType = plantType;
        this.waterPeriod = waterPeriod;
    }
}
