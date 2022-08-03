package com.tave_app_1.senapool.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@Table(name = "user")
@AllArgsConstructor // @Builder 를 이용하기 위해서 항상 같이 처리해야 컴파일 에러가 발생하지 않는다
@NoArgsConstructor
@DynamicUpdate
@PropertySource("classpath:application.properties")
public class User {

    @Id
    @Column(name = "user_pk")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPK;

    @Column(name = "user_id",nullable = false, unique = true)
    @NotEmpty
    private String userId;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "email",nullable = false, unique = true)
    @NotEmpty
    private String email;

    @Column(name = "user_image",nullable = true)
    private String userImageName;

    @Column(name = "activated")
    private boolean activated;

    public void setPassword(String enPw) {
        this.password = enPw;
    }

    @JsonManagedReference
    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<MyPlant> myPlantList;



    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_pk", referencedColumnName = "user_pk")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}