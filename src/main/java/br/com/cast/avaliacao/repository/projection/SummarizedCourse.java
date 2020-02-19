package br.com.cast.avaliacao.repository.projection;

import java.time.LocalDate;

public class SummarizedCourse {
	private Long id;
	private String description;
	private LocalDate startDate;
	private LocalDate finishDate;
	private Integer amountOfStudents;
	private String category;

	public SummarizedCourse(Long id, String description, LocalDate startDate, LocalDate finishDate,
			Integer amountOfStudents, String category) {
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
