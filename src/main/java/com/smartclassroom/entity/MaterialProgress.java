package com.smartclassroom.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "material_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private StudyMaterial material;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    private boolean completed;

    private LocalDateTime completedAt;
}
