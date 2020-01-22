package br.com.cast.avaliacao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import br.com.cast.avaliacao.controller.CourseController;
import br.com.cast.avaliacao.model.Category;
import br.com.cast.avaliacao.model.Course;
import br.com.cast.avaliacao.service.BusinessException;
import br.com.cast.avaliacao.service.CourseService;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CourseService courseService;
	private Map<Long, Category> categoryMap = new HashMap<>();
	private Map<Long, Course> courseMap = new HashMap<>();
	private String URL = "/cursos";

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
		this.courseMap.put(4L,
				new Course(4L, "Scrum", LocalDate.of(2021, 1, 5), LocalDate.of(2021, 02, 28), 30, getCategoryById(4L)));
		this.courseMap.put(5L,
				new Course(5L, "XP", LocalDate.of(2021, 3, 11), LocalDate.of(2021, 8, 1), 30, getCategoryById(4L)));
		this.courseMap.put(6L, new Course(6L, "Testes de Aceitação", LocalDate.of(2021, 8, 10),
				LocalDate.of(2020, 12, 10), 10, getCategoryById(3L)));
		MockitoAnnotations.initMocks(CourseApiApplicationTests.class);
	}

	@Test
	public void findScrumCourseById() throws Exception {
		Course course = getCourseById(4L);
		when(this.courseService.findById(4L)).thenReturn(Optional.of(course));
		String jsonResponse = "{\"id\":4,\"description\":\"Scrum\",\"startDate\":\"2021-01-05\",\"finishDate\":\"2021-02-28\",\"amountOfStudents\":30,\"category\":{\"id\":4,\"description\":\"Processos\"}}";
		this.mockMvc.perform(get("/cursos/4")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonResponse));
	}

	@Test
	public void findXPCourseById() throws Exception {
		Course course = getCourseById(5L);
		when(this.courseService.findById(5L)).thenReturn(Optional.of(course));
		String jsonResponse = "{\"id\":5,\"description\":\"XP\",\"startDate\":\"2021-03-11\",\"finishDate\":\"2021-08-01\",\"amountOfStudents\":30,\"category\":{\"id\":4,\"description\":\"Processos\"}}";
		this.mockMvc.perform(get("/cursos/5")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonResponse));
	}

	@Test
	public void findByIdInexistentCourse() throws Exception {
		when(courseService.findById(10L)).thenReturn(Optional.empty());

		this.mockMvc.perform(get("/cursos/10")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	public void findAllCourses() throws Exception {
		when(this.courseService.findAll()).thenReturn(getAllCourses());
		String jsonResponse = "["
				+ "{\"id\":1,\"description\":\"JavaEE\",\"startDate\":\"2020-01-15\",\"finishDate\":\"2020-03-15\",\"amountOfStudents\":20,\"category\":{\"id\":2,\"description\":\"Programação\"}},"
				+ "{\"id\":2,\"description\":\"JavaWeb\",\"startDate\":\"2020-07-01\",\"finishDate\":\"2020-10-14\",\"amountOfStudents\":30,\"category\":{\"id\":2,\"description\":\"Programação\"}},"
				+ "{\"id\":3,\"description\":\"Angular\",\"startDate\":\"2020-04-30\",\"finishDate\":\"2020-06-30\",\"amountOfStudents\":20,\"category\":{\"id\":2,\"description\":\"Programação\"}},"
				+ "{\"id\":4,\"description\":\"Scrum\",\"startDate\":\"2021-01-05\",\"finishDate\":\"2021-02-28\",\"amountOfStudents\":30,\"category\":{\"id\":4,\"description\":\"Processos\"}},"
				+ "{\"id\":5,\"description\":\"XP\",\"startDate\":\"2021-03-11\",\"finishDate\":\"2021-08-01\",\"amountOfStudents\":30,\"category\":{\"id\":4,\"description\":\"Processos\"}},"
				+ "{\"id\":6,\"description\":\"Testes de Aceitação\",\"startDate\":\"2021-08-10\",\"finishDate\":\"2020-12-10\",\"amountOfStudents\":10,\"category\":{\"id\":3,\"description\":\"Qualidade\"}}"
				+ "]";
		this.mockMvc.perform(get("/cursos/all")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonResponse));
	}

	@Test
	public void findFirstPagedCourses() throws Exception {
		int page = 0;
		int size = 3;
		Page<Course> coursePage = getPagedCourses("", PageRequest.of(page, size));
		assertThat(coursePage).isNotNull();
		when(courseService.findPage("%", PageRequest.of(0, 3))).thenReturn(coursePage);

		String jsonResponse = "["
				+ "{\"id\":1,\"description\":\"JavaEE\",\"startDate\":\"2020-01-15\",\"finishDate\":\"2020-03-15\",\"amountOfStudents\":20,\"category\":{\"id\":2,\"description\":\"Programação\"}},"
				+ "{\"id\":2,\"description\":\"JavaWeb\",\"startDate\":\"2020-07-01\",\"finishDate\":\"2020-10-14\",\"amountOfStudents\":30,\"category\":{\"id\":2,\"description\":\"Programação\"}},"
				+ "{\"id\":3,\"description\":\"Angular\",\"startDate\":\"2020-04-30\",\"finishDate\":\"2020-06-30\",\"amountOfStudents\":20,\"category\":{\"id\":2,\"description\":\"Programação\"}}"
				+ "]";
		this.mockMvc.perform(get(this.URL + "?description=%&page=" + page + "&size="+ size)).andDo(print()).andExpect(status().isOk());
				//.andExpect(content().json(jsonResponse));
		System.out.println("Oi");
	}

	@Test
	public void deleteXPCourse() throws Exception {
		Course course = getCourseById(5L);
		deleteCourse(course);
		doNothing().when(this.courseService).delete(5L);

		this.mockMvc.perform(delete("/cursos/5")).andDo(print()).andExpect(status().is2xxSuccessful());
	}

	@Test
	public void deleteInexistingCourse() throws Exception {
		doThrow(new BusinessException("Curso Inválido para exclusão.")).when(this.courseService).delete(50L);
		this.mockMvc.perform(delete("/cursos/50")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	public void saveHibernateCourse() throws Exception {
		Course persistendCourse = new Course(7L, "JPA-Hibernate", LocalDate.of(2020, 3, 20), LocalDate.of(2020, 4, 20),
				15, this.getCategoryById(2L));
		when(this.courseService.save(any(Course.class))).thenReturn(persistendCourse);

		String json = "{\"id\":null,\"description\":\"JPA-Hibernate\",\"startDate\":\"2020-03-20\",\"finishDate\":\"2020-04-20\",\"amountOfStudents\":15,\"category\":{\"id\":2,\"description\":\"Programação\"}}";
		String jsonResponse = "{\"id\":7,\"description\":\"JPA-Hibernate\",\"startDate\":\"2020-03-20\",\"finishDate\":\"2020-04-20\",\"amountOfStudents\":15,\"category\":{\"id\":2,\"description\":\"Programação\"}}";
		MvcResult result = this.mockMvc.perform(post(this.URL).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().is2xxSuccessful()).andExpect(content().json(jsonResponse)).andReturn();
		System.out.println(result);
	}

	@Test
	public void saveHibernateStartDateAfterThatFinishDateCourse() throws Exception {
		when(this.courseService.save(any(Course.class)))
				.thenThrow(new BusinessException("A data de início do curoso é maior que a data de finalização."));

		String json = "{\"id\":null,\"description\":\"JPA-Hibernate\",\"startDate\":\"2020-04-20\",\"finishDate\":\"2020-04-20\",\"amountOfStudents\":15,\"category\":{\"id\":2,\"description\":\"Programação\"}}";
		MvcResult result = this.mockMvc.perform(post(this.URL).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().is4xxClientError()).andExpect(content().json(json)).andReturn();
		System.out.println(result);
	}

	@Test
	public void updateAngularCourse() throws Exception {
		Course persistendCourse = getCourseById(3L);

		assertThat(persistendCourse.getDescription()).isEqualTo("Angular");
		assertThat(persistendCourse.getFinishDate().isEqual(LocalDate.of(2020, 6, 30)));
		persistendCourse.setDescription("Angular 6");
		persistendCourse.setFinishDate(LocalDate.of(2020, 6, 20));
		String json = "{\"id\":3,\"description\":\"Angular\",\"startDate\":\"2020-04-30\",\"finishDate\":\"2020-06-30\",\"amountOfStudents\":20,\"category\":{\"id\":2,\"description\":\"Programação\"}}";
		String jsonResponse = "{\"id\":3,\"description\":\"Angular 6\",\"startDate\":\"2020-04-30\",\"finishDate\":\"2020-06-20\",\"amountOfStudents\":20,\"category\":{\"id\":2,\"description\":\"Programação\"}}";
		when(this.courseService.update(any(Long.class), any(Course.class))).thenReturn(persistendCourse);
		MvcResult result = this.mockMvc
				.perform(put(this.URL + "/3").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().is2xxSuccessful()).andExpect(content().json(jsonResponse)).andReturn();
		System.out.println(result);
	}

	@Test
	public void updateAngularCourseInvalidStartDate() throws Exception {
		String json = "{\"id\":3,\"description\":\"Angular\",\"startDate\":\"2020-04-30\",\"finishDate\":\"2020-06-30\",\"amountOfStudents\":20,\"category\":{\"id\":2,\"description\":\"Programação\"}}";
		when(this.courseService.update(any(Long.class), any(Course.class)))
				.thenThrow(new BusinessException("Existe(m) curso(s) planejado(s) dentro do mesmo período."));
		MvcResult result = this.mockMvc
				.perform(put(this.URL + "/3").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().is4xxClientError()).andExpect(content().json(json)).andReturn();
		System.out.println(result);
	}

	@Test
	public void updateInexistentCourse() throws Exception {
		String json = "{\"id\":30,\"description\":\"Javascript\",\"startDate\":\"2020-04-30\",\"finishDate\":\"2020-06-30\",\"amountOfStudents\":20,\"category\":{\"id\":2,\"description\":\"Programação\"}}";
		when(this.courseService.update(any(Long.class), any(Course.class)))
				.thenThrow(new BusinessException("Curso Inválido para exclusão."));
		MvcResult result = this.mockMvc
				.perform(put(this.URL + "/30").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().is4xxClientError()).andExpect(content().json(json)).andReturn();
		System.out.println(result);
	}

	private Category getCategoryById(Long id) {
		return this.categoryMap.get(id);
	}

	private Course getCourseById(Long id) {
		return this.courseMap.get(id);
	}

	private List<Course> getAllCourses() {
		List<Course> courseList = new ArrayList<>();
		courseList.addAll(this.courseMap.values());
		return courseList;
	}

	private Page<Course> getPagedCourses(String description, Pageable pageable) {
		PageRequest pageRequest = (PageRequest) pageable;
		List<Course> courses = getFilteredCourses(description);
		List<Course> pagedCourses = new ArrayList<>();

		int pageBegin = pageRequest.getPageNumber() * (pageRequest.getPageSize());
		int pageEnd = ((pageBegin + pageRequest.getPageSize()) > courses.size()) ? courses.size()
				: (pageBegin + pageRequest.getPageSize());
		for (int index = pageBegin; index < (pageEnd); index++) {
			pagedCourses.add(courses.get(index));
		}
		return new PageImpl<Course>(pagedCourses);
	}

	private List<Course> getFilteredCourses(String description) {
		List<Course> filteredCourseList = new ArrayList<>();
		if (description.equals("")) {
			filteredCourseList.addAll(this.courseMap.values());
		} else {
			List<Course> courseList = getAllCourses();
			for (int index = 0; index < courseList.size(); index++) {
				if (courseList.get(index).getDescription().contains(description)) {
					filteredCourseList.add(courseList.get(index));
				}
			}
		}
		return filteredCourseList;
	}

	private void deleteCourse(Course course) {
		if (course != null) {
			this.courseMap.remove(course.getId());
		}
	}
}
