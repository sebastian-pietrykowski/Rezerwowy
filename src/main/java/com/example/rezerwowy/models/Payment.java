package com.example.rezerwowy.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(name = "payment_public_id", unique = true)
    @NotNull
    @Builder.Default
    private UUID publicId = UUID.randomUUID();

    @Embedded
    @NotNull
    @Valid
    private Buyer buyer;

    @Column(name = "date")
    @NotNull
    @DateTimeFormat
    @PastOrPresent(message = "Reservation should be made in the past")
    private LocalDate date;

    @PrimaryKeyJoinColumn(name = "reservation_id")
    @OneToOne
    private Reservation reservation;
}
