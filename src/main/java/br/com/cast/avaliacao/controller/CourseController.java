package br.com.cast.avaliacao.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.cast.avaliacao.model.Course;
import br.com.cast.avaliacao.service.CourseService;

@RestController
@RequestMapping("/cursos")
public class CourseController {
	@Autowired
	private CourseService courseService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public Page<Course> findPage(@RequestParam(required = false, defaultValue = "") String description,
			Pageable pageable) {
		Page<Course> pagedCourse = this.courseService.findPage(description, pageable);
		return pagedCourse;
	}

	@GetMapping("/todos")
	public @ResponseBody List<Course> findAllCourses() {
		return this.courseService.findAll();
	}

	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<Course> findCourseById(@PathVariable Long id) {
		Optional<Course> opcionalCourse = courseService.findById(id);

		return opcionalCourse.isPresent() ? ResponseEntity.ok(opcionalCourse.get()) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
		this.courseService.delete(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/novo")
	public ResponseEntity<Course> saveCourse(@Valid @RequestBody Course course, HttpServletResponse response) {
		Course persistedCourse = this.courseService.save(course);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(course.getId())
				.toUri();
		response.setHeader("Localion", uri.toASCIIString());
		return ResponseEntity.status(HttpStatus.CREATED).body(persistedCourse);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Course> updateCourse(@PathVariable Long id, @Valid @RequestBody Course course,
			HttpServletResponse response) {
		Course persistedCourse = this.courseService.update(id, course);
		return ResponseEntity.status(HttpStatus.CREATED).body(persistedCourse);

	}
}
