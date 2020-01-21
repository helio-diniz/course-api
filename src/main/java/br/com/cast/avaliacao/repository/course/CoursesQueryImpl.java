package br.com.cast.avaliacao.repository.course;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cast.avaliacao.model.Course;
import br.com.cast.avaliacao.service.BusinessException;

public class CoursesQueryImpl implements CourseQuery {

	@Override
	public Page<Course> findPage(String description, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkDates(Course course) throws BusinessException {
		// TODO Auto-generated method stub
		return true;
	}

}
