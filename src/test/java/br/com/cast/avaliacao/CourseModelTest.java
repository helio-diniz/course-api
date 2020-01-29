package br.com.cast.avaliacao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.cast.avaliacao.model.Category;
import br.com.cast.avaliacao.model.Course;

@SpringBootTest
public class CourseModelTest {
/*
	@Test
	public void categoryCreation() {
		Category category = new Category();
		category.setId(new Long(1));
		category.setDescription("Comportamental");
		assertThat(category).isNotNull();
		assertThat(category.getId()).isNotNull();
		assertThat(category.getId()).isEqualTo(1L);
		assertThat(category.getDescription());
		assertThat(category.getDescription()).isEqualTo("Comportamental");
	}

	@Test
	public void courseCreation() {
		Category category = new Category();
		category.setId(2L);
		category.setDescription("Programação");

		Course course = new Course();
		course.setId(10L);
		course.setStartDate(LocalDate.of(2020, 02, 1));
		course.setFinishDate(LocalDate.of(2020, 12, 4));
		course.setAmountOfStudents(new Integer(40));
		course.setCategory(category);

		assertThat(course).isNotNull();
		assertThat(course.getId()).isNotNull();
		assertThat(10L).isEqualTo(course.getId());
		assertThat(course.getStartDate()).isNotNull();
		assertThat(LocalDate.of(2020, 02, 1)).isEqualTo(course.getStartDate());
		assertThat(course.getFinishDate()).isNotNull();
		assertThat(LocalDate.of(2020, 12, 4)).isEqualTo(course.getFinishDate());
		assertThat(course.getCategory()).isNotNull();
		assertThat(course.getCategory().getId()).isNotNull();
		assertThat(course.getCategory().getId()).isEqualTo(2L);
		assertThat(course.getCategory().getDescription());
		assertThat(course.getCategory().getDescription()).isEqualTo("Programação");
	}
*/
}
