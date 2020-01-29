package br.com.cast.avaliacao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.cast.avaliacao.model.Category;
import br.com.cast.avaliacao.model.Course;
import br.com.cast.avaliacao.repository.Categories;
import br.com.cast.avaliacao.repository.Courses;
import br.com.cast.avaliacao.service.BusinessException;
import br.com.cast.avaliacao.service.CategoryService;
import br.com.cast.avaliacao.service.CourseService;

@SpringBootTest
public class CourseServiceTest {
/*	
	@Autowired
	private MessageSource messageSource;
	@InjectMocks
	private CategoryService categoryService;
	@InjectMocks
	private CourseService courseService;
	@Mock
	private Categories categories;
	@Mock
	private Courses courses;
	private Map<Long, Category> categoryMap = new HashMap<>();
	private Map<Long, Course> courseMap = new HashMap<>();

	@BeforeEach
	public void initTest() {
		this.categoryMap.clear();
		this.categoryMap.put(1L, new Category(1L, "Comportamental"));
		this.categoryMap.put(2L, new Category(2L, "Programação"));
		this.categoryMap.put(3L, new Category(3L, "Qualidade"));
		this.categoryMap.put(4L, new Category(4L, "Processos"));
		
		this.courseMap.clear();
		this.courseMap.put(1L, new Course(1L, "JavaEE", LocalDate.of(2020, 1, 15), LocalDate.of(2020, 3, 15), 20, 
				getCategoryById(2L)));
		this.courseMap.put(2L, new Course(2L, "JavaWeb", LocalDate.of(2020, 7, 1), LocalDate.of(2020, 10, 14), 30, 
				getCategoryById(2L)));
		this.courseMap.put(3L, new Course(3L, "Angular", LocalDate.of(2020, 4, 30), LocalDate.of(2020, 6, 30), 20, 
				getCategoryById(2L)));
		this.courseMap.put(4L, new Course(4L, "Scrum", LocalDate.of(2021, 1, 5), LocalDate.of(2021, 02, 28), 30, 
				getCategoryById(4L)));
		this.courseMap.put(5L, new Course(5L, "XP", LocalDate.of(2021, 3, 11), LocalDate.of(2021, 8, 1), 30, 
				getCategoryById(4L)));
		this.courseMap.put(6L, new Course(6L, "Testes de Aceitação", LocalDate.of(2021, 8, 10), LocalDate.of(2020, 12, 10), 10, 
				getCategoryById(3L)));
		
		this.courseService.setMessageSource(this.messageSource);
		MockitoAnnotations.initMocks(CourseServiceTest.class);
	}

	@Test
	public void findByIdComportamentalCategory() {
		Category category = getCategoryById(1L);
		when(categories.findById(1L)).thenReturn(Optional.of(category));

		Optional<Category> optionalCategory = categoryService.findById(1L);
		assertThat(optionalCategory.isPresent()).isTrue();
		assertThat(optionalCategory.get()).isEqualTo(category);
	}

	@Test
	public void findByIdQualidadeCategory() {
		Category category = getCategoryById(3L);
		when(categories.findById(1L)).thenReturn(Optional.of(category));

		Optional<Category> optionalCategory = categoryService.findById(1L);
		assertThat(optionalCategory.isPresent()).isTrue();
		assertThat(optionalCategory.get()).isEqualTo(category);
	}

	@Test
	public void findByIdInexistentCategory() {
		when(categories.findById(10L)).thenReturn(Optional.empty());

		Optional<Category> optionalCategory = categoryService.findById(10L);
		assertThat(optionalCategory.isPresent()).isFalse();
	}
	
	@Test
	public void findAllCategory() {
		List<Category> categoryList = getAllCategories();
		assertThat(categoryList).isNotNull();
		when(categories.findAll()).thenReturn(categoryList);

		List<Category> persistedCategoryList = categoryService.findAll();
		assertThat(persistedCategoryList).isNotNull();
		assertThat(4).isEqualTo(persistedCategoryList.size());
		assertThat(persistedCategoryList).isEqualTo(categoryList);
	}
	
	@Test 
	void findByIdJavaEECourse() {
		Course course = getCourseById(1L);
		when(courses.findById(1L)).thenReturn(Optional.of(course));
		Optional<Course> optionalCourse = this.courseService.findById(1L);
		assertThat(optionalCourse.isPresent()).isTrue();
		assertThat(optionalCourse.get()).isEqualTo(course);
	}

	@Test 
	void findByIdScrumCourse() {
		Course course = getCourseById(4L);
		when(courses.findById(4L)).thenReturn(Optional.of(course));
		Optional<Course> optionalCourse = this.courseService.findById(4L);
		assertThat(optionalCourse.isPresent()).isTrue();
		assertThat(optionalCourse.get()).isEqualTo(course);
	}
	
	@Test
	public void findByIdInexistentCourse() {
		when(courses.findById(10L)).thenReturn(Optional.empty());

		Optional<Course> optionalCourse = courseService.findById(10L);
		assertThat(optionalCourse.isPresent()).isFalse();
	}
	
	@Test
	public void findAllCourses() {
		List<Course> courseList = getAllCourses();
		assertThat(courseList).isNotNull();
		when(courses.findAll()).thenReturn(courseList);

		List<Course> persistedCourseList = courseService.findAll();
		assertThat(persistedCourseList).isNotNull();
		assertThat(6).isEqualTo(persistedCourseList.size());
		assertThat(persistedCourseList).isEqualTo(courseList);
	}
	
	@Test
	public void findFirstPagedCourses() {
		Page<Course> coursePage = getPagedCourses("", PageRequest.of(0, 4));
		assertThat(coursePage).isNotNull();
		when(courses.findPage("", PageRequest.of(0, 4))).thenReturn(coursePage);

		Page<Course> persistedCoursePage = courseService.findPage("", PageRequest.of(0, 4));
		assertThat(persistedCoursePage).isNotNull();
		assertThat(4).isEqualTo(persistedCoursePage.getContent().size());
		assertThat(persistedCoursePage).isEqualTo(coursePage);
	}
	
	@Test
	public void findLastPagedCourses() {
		Page<Course> coursePage = getPagedCourses("", PageRequest.of(1, 4));
		assertThat(coursePage).isNotNull();
		when(courses.findPage("", PageRequest.of(1, 4))).thenReturn(coursePage);

		Page<Course> persistedCoursePage = courseService.findPage("", PageRequest.of(1, 4));
		assertThat(persistedCoursePage).isNotNull();
		assertThat(2).isEqualTo(persistedCoursePage.getContent().size());
		assertThat(persistedCoursePage).isEqualTo(coursePage);
	}
	
	@Test
	public void findFirstPagedJavaCourses() {
		Page<Course> coursePage = getPagedCourses("Java", PageRequest.of(0, 4));
		assertThat(coursePage).isNotNull();
		assertThat(coursePage.getContent().size()).isEqualTo(2);
		assertThat(coursePage.getContent().get(0).getDescription()).isEqualTo("JavaEE");
		assertThat(coursePage.getContent().get(1).getDescription()).isEqualTo("JavaWeb");
		when(courses.findPage("Java", PageRequest.of(0, 4))).thenReturn(coursePage);

		Page<Course> persistedCoursePage = courseService.findPage("Java", PageRequest.of(0, 4));
		assertThat(persistedCoursePage).isNotNull();
		assertThat(2).isEqualTo(persistedCoursePage.getContent().size());
		assertThat(persistedCoursePage).isEqualTo(coursePage);
	}
	
	@Test
	public void deleteScrumCourse() {
		Course course = getCourseById(4L);
		assertThat(course).isNotNull();
		deleteCourse(course);
		List<Course> courseList = getAllCourses();
		doNothing().when(courses).deleteById(4L);
		when(courses.findById(4L)).thenReturn(Optional.of(course));
		this.courseService.delete(course.getId());
		
		List<Course> persistedCourseList = getAllCourses();
		assertThat(courseList).isEqualTo(persistedCourseList);
		course = getCourseById(4L);
		assertThat(course).isNull();
	}
	
	@Test
	public void deleteInexistentCourse() {
		Course course = getCourseById(40L);
		assertThat(course).isNull();
		deleteCourse(course);
		List<Course> courseList = getAllCourses();
		doNothing().when(courses).deleteById(40L);
		when(courses.findById(40L)).thenReturn(Optional.empty());
		
		try {
			this.courseService.delete(40L);
		} catch (BusinessException e) {

			String userMessage = messageSource.getMessage("message.course-invalid-id", null,
					LocaleContextHolder.getLocale());
			assertThat(e.getMessage()).isEqualTo(userMessage);
			List<Course> persistedCourseList = getAllCourses();
			assertThat(courseList).isEqualTo(persistedCourseList);
			return;
		}
		assertThat(false).isTrue();
	}
	
	@Test
	public void saveHibernateCourse() {
		Course course = new Course(null, "JPA-Hibernate", LocalDate.of(2020, 3, 20), LocalDate.of(2020, 4, 20), 15,
				this.getCategoryById(2L));
		assertThat(course.getId()).isNull();
		Course persistedCourse = this.saveCourse(course);
		when(courses.checkDates(course)).thenReturn(true);
		when(courses.save(course)).thenReturn(persistedCourse);
		persistedCourse = this.courseService.save(course);
		assertThat(persistedCourse).isNotNull();
		assertThat(persistedCourse.getId()).isNotNull();
		assertThat(persistedCourse.getId()).isEqualTo(7L);
	}
	
	@Test
	public void saveHibernateStartDateAfterThatFinishDateCourse() {
		Course course = new Course(null, "JPA-Hibernate", LocalDate.of(2020, 4, 20), LocalDate.of(2020, 3, 20), 15,
				this.getCategoryById(2L));
		assertThat(course.getId()).isNull();
		when(this.courses.checkDates(course)).thenReturn(true);
		try {
			this.courseService.save(course);
		} catch (BusinessException e) {
			String userMessage = messageSource.getMessage("message.course-invalid-period", null,
					LocaleContextHolder.getLocale());
			assertThat(e.getMessage()).isEqualTo(userMessage);
			return;
		} 
		assertThat(false).isTrue();
	}
	
	@Test
	public void saveHibernateInvalidStartDateCourse() {
		Course course = new Course(null, "JPA-Hibernate", LocalDate.of(2020, 2, 20), LocalDate.of(2020, 4, 20), 15,
				this.getCategoryById(2L));
		assertThat(course.getId()).isNull();
		when(this.courses.checkDates(course)).thenThrow(new BusinessException("Existe(m) curso(s) planejado(s) dentro do mesmo período."));

		try {
			this.courseService.save(course);
		} catch (BusinessException e) {
			String userMessage = messageSource.getMessage("message.course-already-planned", null,
					LocaleContextHolder.getLocale());
			assertThat(e.getMessage()).isEqualTo(userMessage);
			return;
		} 
		assertThat(false).isTrue();
	}
	
	@Test
	public void updateAngularCourse() {
		Course course = getCourseById(3L);
		when(courses.findById(3L)).thenReturn(Optional.of(course));		
		
		assertThat(course.getDescription()).isEqualTo("Angular");
		assertThat(course.getFinishDate().isEqual(LocalDate.of(2020, 6, 30)));
		course.setDescription("Angular 6");
		course.setFinishDate(LocalDate.of(2020, 6, 20));
		assertThat(this.courseMap.size()).isEqualTo(6);
		
		Course persistedCourse = this.saveCourse(course);
		when(courses.checkDates(course)).thenReturn(true);
		when(courses.save(course)).thenReturn(persistedCourse);
		persistedCourse = this.courseService.update(3L, course);
		assertThat(persistedCourse).isNotNull();
		assertThat(persistedCourse.getId()).isNotNull();
		assertThat(persistedCourse.getId()).isEqualTo(3L);
		assertThat(course.getDescription()).isEqualTo("Angular 6");
		assertThat(course.getFinishDate().isEqual(LocalDate.of(2020, 6, 20)));
		
		assertThat(this.courseMap.size()).isEqualTo(6);
	}
	
	@Test
	public void updateAngularCourseStartDateAfterThatFinishDate() {
		Course course = getCourseById(3L);
		when(courses.findById(3L)).thenReturn(Optional.of(course));	
		assertThat(course.getFinishDate().isEqual(LocalDate.of(2020, 4, 30)));
		assertThat(course.getFinishDate().isEqual(LocalDate.of(2020, 6, 30)));

		course.setStartDate(LocalDate.of(2020, 6, 30));
		course.setFinishDate(LocalDate.of(2020, 5, 20));
		when(this.courses.checkDates(course)).thenReturn(true);
		try {
			this.courseService.update(3L, course);
		} catch (BusinessException e) {
			String userMessage = messageSource.getMessage("message.course-invalid-period", null,
					LocaleContextHolder.getLocale());
			assertThat(e.getMessage()).isEqualTo(userMessage);
			return;
		} 
		assertThat(false).isTrue();
	}
	
	public void updateAngularCourseInvalidStartDate() {
		Course course = getCourseById(3L);
		when(courses.findById(3L)).thenReturn(Optional.of(course));	
		assertThat(course.getFinishDate().isEqual(LocalDate.of(2020, 4, 30)));
		assertThat(course.getFinishDate().isEqual(LocalDate.of(2020, 6, 30)));

		course.setStartDate(LocalDate.of(2020, 2, 20));
		course.setFinishDate(LocalDate.of(2020, 4, 20));
		when(this.courses.checkDates(course)).thenThrow(new BusinessException("Existe(m) curso(s) planejado(s) dentro do mesmo período"));
		try {
			this.courseService.update(3L, course);
		} catch (BusinessException e) {
			String userMessage = messageSource.getMessage("message.course-invalid-period", null,
					LocaleContextHolder.getLocale());
			assertThat(e.getMessage()).isEqualTo(userMessage);
			return;
		} 
		assertThat(false).isTrue();
	}
	
	@Test
	public void updateInexistentCourse() {
		Course course = getCourseById(3L);
		course.setId(30L);
		when(courses.findById(30L)).thenReturn(Optional.empty());	
		when(this.courses.checkDates(course)).thenReturn(true);
		try {
			this.courseService.update(30L, course);
		} catch (BusinessException e) {
			String userMessage = messageSource.getMessage("message.course-invalid-id", null,
					LocaleContextHolder.getLocale());
			assertThat(e.getMessage()).isEqualTo(userMessage);
			return;
		} 
		assertThat(false).isTrue();
	}
	
	
	private Course saveCourse(Course course) {
		if (course == null) {
			return null;
		}
		if (course.getId() == null) {
			Long key = new Long(courseMap.size() + 1);
			Course resultingCourse = new Course(key, course.getDescription(), course.getStartDate(), course.getFinishDate(), course.getAmountOfStudents(), course.getCategory());
			this.courseMap.put(key, resultingCourse);
			return resultingCourse;		
		} else {
			return this.courseMap.put(course.getId(), course);
		}
	}

	private Category getCategoryById(Long id) {
		return this.categoryMap.get(id);
	}
	
	private List<Category> getAllCategories(){
		List<Category> categoryList = new ArrayList<>();
		categoryList.addAll(this.categoryMap.values());
		return categoryList;
	}
	
	private Course getCourseById(Long id) {
		return this.courseMap.get(id);
	}

	private List<Course> getAllCourses() {
		List<Course> courseList = new ArrayList<>();
		courseList.addAll(this.courseMap.values());
		return courseList;
	}
	
	private List<Course> getFilteredCourses(String description) {
		List<Course> filteredCourseList = new ArrayList<>();
		if (description.equals("")) {			
			filteredCourseList.addAll(this.courseMap.values());
		} else {
			List<Course> courseList = getAllCourses();
			for (int index=0; index < courseList.size(); index ++) {
				if (courseList.get(index).getDescription().contains(description)) {
					filteredCourseList.add(courseList.get(index));
				}
			}
		}
		return filteredCourseList;
	}
	
	private Page<Course> getPagedCourses(String description, Pageable pageable) {
		PageRequest pageRequest = (PageRequest)pageable;
		List<Course> courses = getFilteredCourses(description);
		List<Course> pagedCourses = new ArrayList<>();
		
		int pageBegin = pageRequest.getPageNumber() * (pageRequest.getPageSize());
		int pageEnd = ((pageBegin + pageRequest.getPageSize()) > courses.size()) ? courses.size(): (pageBegin + pageRequest.getPageSize());
		for(int index = pageBegin; index < (pageEnd); index ++) {
			pagedCourses.add(courses.get(index));
		}
		return new PageImpl<Course>(pagedCourses);
	}
	
	private void deleteCourse(Course course) {
		if (course != null) {
			this.courseMap.remove(course.getId());
		}
	}
*/
}
