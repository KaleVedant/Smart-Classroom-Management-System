package com.smartclassroom.repository;

import com.smartclassroom.entity.ClassEntity;
import com.smartclassroom.entity.ClassStudent;
import com.smartclassroom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassStudentRepository extends JpaRepository<ClassStudent, Long> {

    List<ClassStudent> findByStudent(User student);

    boolean existsByClassEntityAndStudent(ClassEntity classEntity, User student);
}
