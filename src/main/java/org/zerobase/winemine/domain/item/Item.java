package org.zerobase.winemine.domain.item;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.zerobase.winemine.domain.user.User;


import java.time.LocalDate;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Item {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String text; // 물건에 대한 상세설명

    private String imgName; // 이미지 파일명

    private String imgPath; // 이미지 조회 경로

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin")
    private User admin;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 상품 등록 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDate.now();
    }

}



