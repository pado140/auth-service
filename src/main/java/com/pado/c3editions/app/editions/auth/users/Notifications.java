package com.pado.c3editions.app.editions.auth.users;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity

@Table(name="notifications")
public class Notifications implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String message,titre,lien;
    @Builder.Default
    private boolean isread=false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "created_by")
    private Users createdby;

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime modified;

}
