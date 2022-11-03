package com.semicolon.itaxi.data.models;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String pickUpAddress;
    private String dropOffAddress;
    private String location;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "driver_id")
//    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Driver driver;
//    @OneToOne
//    @JoinColumn(name = "payment_id")
//    private Payment payment;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time;
}
