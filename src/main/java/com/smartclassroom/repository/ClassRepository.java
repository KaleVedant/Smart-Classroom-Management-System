package com.smartclassroom.repository;

import com.smartclassroom.entity.ClassEntity;
import com.smartclassroom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {

    List<ClassEntity> findByTeacher(User teacher);
}
