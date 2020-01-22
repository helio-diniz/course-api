package br.com.cast.avaliacao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cast.avaliacao.model.Course;
import br.com.cast.avaliacao.repository.Courses;

@Service
public class CourseService {

	@Autowired
	private Courses courses;
	
	public Optional<Course> findById(Long id){
		return this.courses.findById(id);
	}

	public List<Course> findAll() {
		return this.courses.findAll();
	}

	public Page<Course> findPage(String description, Pageable pageable) {
		return this.courses.findPage(description, pageable);
	}
	
	public void delete(Long id) {
		Optional<Course> optionalCourse = this.findById(id);
		if (optionalCourse.isPresent()) {
			this.courses.deleteById(id);			
		} else {
			throw new BusinessException("Curso Inválido para exclusão.");
		}
	}

	public Course save(Course course) {
		if (course == null || course.getId() != null) {
			return null;
		} else {
			if (checkCourseDates(course)) {
				return this.courses.save(course);
			} else {				
				throw new BusinessException("Existe(m) curso(s) planejado(s) dentro do mesmo período.");		
			}
		}
	}

	public Course update(Long id, Course course) {
		if (course == null || course.getId() == null) {
			return null;
		} else {
			if (checkCourseDates(course)) {
				Optional<Course> optionalCourse = this.findById(id);
				if (!optionalCourse.isPresent()) {
					throw new BusinessException("Curso Inválido para exclusão.");
				}
				Course persistedCourse = optionalCourse.get();
				BeanUtils.copyProperties(course, persistedCourse, "codigo");	
				return this.courses.save(persistedCourse);
			} else {				
				throw new BusinessException("Existe(m) curso(s) planejado(s) dentro do mesmo período.");		
			}
		}
	}
	
	private boolean checkCourseDates(Course course) {
		if (course.getStartDate().isAfter(course.getFinishDate())) {
			throw new BusinessException("A data de início do curoso é maior que a data de finalização.");
		}
		return this.courses.checkDates(course);
	}

}
