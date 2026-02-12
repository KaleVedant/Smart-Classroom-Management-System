package com.smartclassroom.service;

import com.smartclassroom.dto.MaterialRequest;
import com.smartclassroom.entity.*;
import com.smartclassroom.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    private final ClassStudentRepository classStudentRepository;
    private final UserRepository userRepository;
    private final StudyMaterialRepository studyMaterialRepository;

    // ✅ constructor injection (ALL repositories)
    public ClassService(
            ClassRepository classRepository,
            ClassStudentRepository classStudentRepository,
            UserRepository userRepository,
            StudyMaterialRepository studyMaterialRepository) {

        this.classRepository = classRepository;
        this.classStudentRepository = classStudentRepository;
        this.userRepository = userRepository;
        this.studyMaterialRepository = studyMaterialRepository;
    }

    // 👨‍🏫 Teacher creates class
    public ClassEntity createClass(String teacherEmail, String className, String subject) {

        User teacher = userRepository.findByEmail(teacherEmail)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        ClassEntity classEntity = ClassEntity.builder()
                .className(className)
                .subject(subject)
                .teacher(teacher)
                .createdAt(LocalDateTime.now())
                .build();

        return classRepository.save(classEntity);
    }

    // 👨‍🎓 Student joins class
    public void joinClass(Long classId, String studentEmail) {

        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (classStudentRepository.existsByClassEntityAndStudent(classEntity, student)) {
            throw new RuntimeException("Already joined");
        }

        ClassStudent cs = ClassStudent.builder()
                .classEntity(classEntity)
                .student(student)
                .joinedAt(LocalDateTime.now())
                .build();

        classStudentRepository.save(cs);
    }

    // 👨‍🏫 View teacher classes
    public List<ClassEntity> getTeacherClasses(String email) {
        User teacher = userRepository.findByEmail(email).orElseThrow();
        return classRepository.findByTeacher(teacher);
    }

    // 👨‍🎓 View student classes
    public List<ClassStudent> getStudentClasses(String email) {
        User student = userRepository.findByEmail(email).orElseThrow();
        return classStudentRepository.findByStudent(student);
    }

    // 👨‍🏫 Teacher uploads study material
    public StudyMaterial uploadMaterial(String teacherEmail, MaterialRequest request) {

        User teacher = userRepository.findByEmail(teacherEmail)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        ClassEntity classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        // ensure teacher owns the class
        if (!classEntity.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("Not allowed");
        }

        StudyMaterial material = StudyMaterial.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .materialUrl(request.getMaterialUrl())
                .classEntity(classEntity)
                .createdAt(LocalDateTime.now())
                .build();

        return studyMaterialRepository.save(material);
    }
}
