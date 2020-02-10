package br.com.cast.avaliacao.repository.course;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cast.avaliacao.model.Course;
import br.com.cast.avaliacao.repository.projection.SummarizedCourse;
import br.com.cast.avaliacao.service.BusinessException;

public interface CoursesQuery {
	public Page<Course> findPage(String description, Pageable pageable);
	public boolean checkDates(Course course) throws BusinessException;
	public List<SummarizedCourse> findAllSummarizedCourses();
}
