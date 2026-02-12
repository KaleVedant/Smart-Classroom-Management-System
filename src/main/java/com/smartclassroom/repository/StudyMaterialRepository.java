package com.smartclassroom.repository;

import com.smartclassroom.entity.ClassEntity;
import com.smartclassroom.entity.StudyMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long> {

    List<StudyMaterial> findByClassEntity(ClassEntity classEntity);
}
