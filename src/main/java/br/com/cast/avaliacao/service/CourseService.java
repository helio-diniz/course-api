package br.com.cast.avaliacao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cast.avaliacao.model.Course;
import br.com.cast.avaliacao.repository.Courses;
import br.com.cast.avaliacao.repository.projection.SummarizedCourse;

@Service
public class CourseService {

	@Autowired
	private Courses courses;

	@Autowired
	private MessageSource messageSource;

	public Optional<Course> findById(Long id) {
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
			String userMessage = this.messageSource.getMessage("message.course-invalid-id", null,
					LocaleContextHolder.getLocale());
			throw new BusinessException(userMessage);
		}
	}

	public Course save(Course course) {
		if (course == null || course.getId() != null) {
			return null;
		} else {
			if (checkCourseDates(course)) {
				return this.courses.save(course);
			} else {
				String userMessage = messageSource.getMessage("message.course-already-planned", null,
						LocaleContextHolder.getLocale());
				throw new BusinessException(userMessage);
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
					String userMessage = messageSource.getMessage("message.course-invalid-id", null,
							LocaleContextHolder.getLocale());
					throw new BusinessException(userMessage);
				}
				Course persistedCourse = optionalCourse.get();
				BeanUtils.copyProperties(course, persistedCourse, "codigo");
				this.courses.save(persistedCourse);
				return this.findById(persistedCourse.getId()).get();
			} else {
				String userMessage = messageSource.getMessage("message.course-already-planned", null,
						LocaleContextHolder.getLocale());
				throw new BusinessException(userMessage);
			}
		}
	}

	public boolean checkCourseDates(Course course) {
		if (course.getStartDate().isAfter(course.getFinishDate())) {
			String userMessage = messageSource.getMessage("message.course-invalid-period", null,
					LocaleContextHolder.getLocale());
			throw new BusinessException(userMessage);
		}
		return this.courses.checkDates(course);
	}

	public List<SummarizedCourse> findAllSummarizedCourse() {
		return this.courses.findAllSummarizedCourses();
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
