package br.com.cast.avaliacao.model;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

@Entity
@Table(name = "curso")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Long id;
	@Column(name = "descricao")
	@NotNull
	@Size(min=0, max=100)
	private String description;
	@Column(name = "data_inicio")
	@NotNull
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate startDate;
	@Column(name = "data_termino")
	@NotNull
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate finishDate;
	@Column(name = "quantidade_alunos")
	private Integer amountOfStudents;
	@ManyToOne
	@JoinColumn(name = "categoria_codigo")
	@NotNull
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
