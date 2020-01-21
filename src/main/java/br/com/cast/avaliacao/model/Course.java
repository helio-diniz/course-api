package br.com.cast.avaliacao.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "curso")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Long id;
	@Column(name = "descricao")
	private String description;
	@Column(name = "data_inicio")
	private LocalDate startDate;
	@Column(name = "data_termino")
	private LocalDate finishDate;
	@Column(name = "quantidade_alunos")
	private Integer amountOfStudents;
	@ManyToOne
	@JoinColumn(name = "categoria_codigo")
	private Category category;

	public Course() {
		super();
	}

	public Course(Long id, String description, LocalDate startDate, LocalDate finishDate, Integer amountOfStudents,
			Category category) {
		super();
		this.id = id;
		this.description = description;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.amountOfStudents = amountOfStudents;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(LocalDate finishDate) {
		this.finishDate = finishDate;
	}

	public Integer getAmountOfStudents() {
		return amountOfStudents;
	}

	public void setAmountOfStudents(Integer amountOfStudents) {
		this.amountOfStudents = amountOfStudents;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
