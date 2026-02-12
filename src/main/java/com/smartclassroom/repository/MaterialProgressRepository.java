package com.smartclassroom.repository;

import com.smartclassroom.entity.MaterialProgress;
import com.smartclassroom.entity.StudyMaterial;
import com.smartclassroom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialProgressRepository extends JpaRepository<MaterialProgress, Long> {

    Optional<MaterialProgress> findByMaterialAndStudent(
            StudyMaterial material, User student);
}
