package multipartDemo.MultipartLearn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;

    private String phone;


    @Lob
    @Column(name = "photo")
    private byte[] photo;
    private String photoContentType;
    private String photoFileName;
    private Long photoSize;

    @Lob
    @Column(name ="aadhaar_card")
    private byte[] aadhaarCard;
    private String aadhaarFileName;

    private String aadhaarContentType;

    private Long aadhaarSize;
    private LocalDateTime createdAt;


}
