package br.com.cast.avaliacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cast.avaliacao.model.Course;
import br.com.cast.avaliacao.repository.course.CoursesQuery;

public interface Courses extends JpaRepository<Course, Long>, CoursesQuery{

}
