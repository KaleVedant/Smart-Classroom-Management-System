package com.smartclassroom.controller;

import com.smartclassroom.dto.MaterialRequest;
import com.smartclassroom.entity.ClassEntity;
import com.smartclassroom.entity.StudyMaterial;
import com.smartclassroom.service.ClassService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    // 👨‍🏫 TEACHER ONLY
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/create")
    public ClassEntity createClass(
            @RequestParam String className,
            @RequestParam String subject,
            Authentication auth) {

        return classService.createClass(
                auth.getName(), className, subject);
    }

    // 👨‍🎓 STUDENT ONLY
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/join/{classId}")
    public String joinClass(
            @PathVariable Long classId,
            Authentication auth) {

        classService.joinClass(classId, auth.getName());
        return "Joined successfully";
    }

    // 👨‍🏫 Teacher view
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/teacher")
    public List<ClassEntity> teacherClasses(Authentication auth) {
        return classService.getTeacherClasses(auth.getName());
    }

    // 👨‍🎓 Student view
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student")
    public Object studentClasses(Authentication auth) {
        return classService.getStudentClasses(auth.getName());
    }

    // 👨‍🏫 TEACHER uploads study material
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/material/upload")
    public StudyMaterial uploadMaterial(
            @RequestBody MaterialRequest request,
            Authentication auth) {

        return classService.uploadMaterial(auth.getName(), request);
    }
}
