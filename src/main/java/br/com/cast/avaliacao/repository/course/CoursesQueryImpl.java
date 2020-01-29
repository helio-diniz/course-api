package br.com.cast.avaliacao.repository.course;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.cast.avaliacao.model.Course;
import br.com.cast.avaliacao.service.BusinessException;

public class CoursesQueryImpl implements CoursesQuery {
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Course> findPage(String description, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Course> criteria = builder.createQuery(Course.class);
		Root<Course> root = criteria.from(Course.class);
		
		Predicate[] predicates = createFilters(description, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Course> query = manager.createQuery(criteria);
		addPageFilters(query, pageable);
		List<Course> courseList = query.getResultList();
		long totalRecords = total(description);
		return new PageImpl<>(courseList, pageable, totalRecords);
	}
	
	private long total(String description) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Course> root = criteria.from(Course.class);
		
		Predicate[] predicates = createFilters(description, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}

	@Override
	public boolean checkDates(Course course) throws BusinessException {
		CriteriaBuilder cb =  manager.getCriteriaBuilder();
		
		CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
		Root<Course> root = criteria.from(Course.class);
		/*
		if (course.getId() == null) {
			criteria.where(
					cb.or(
							cb.and( cb.lessThanOrEqualTo(root.get("startDate"), course.getStartDate()),
									cb.greaterThanOrEqualTo(root.get("finishDate"), course.getStartDate())
									),
							cb.and( cb.lessThanOrEqualTo(root.get("startDate"), course.getFinishDate()),
									cb.greaterThanOrEqualTo(root.get("finishDate"), course.getFinishDate())
									)
							)	
			);			
		} else {
			criteria.where(
					cb.and(
					    cb.notEqual(root.get("id"), course.getId()),		
						cb.or(
								cb.and( cb.lessThanOrEqualTo(root.get("startDate"), course.getStartDate()),
										cb.greaterThanOrEqualTo(root.get("finishDate"), course.getStartDate())
								),
								cb.and( cb.lessThanOrEqualTo(root.get("startDate"), course.getFinishDate()),
										cb.greaterThanOrEqualTo(root.get("finishDate"), course.getFinishDate())
								)
						)	
					)
			);	
		}*/
		if (course.getId() == null) {
			criteria.where(
					cb.or(
							cb.and( cb.greaterThanOrEqualTo(root.get("startDate"), course.getStartDate()),
									cb.lessThanOrEqualTo(root.get("startDate"), course.getFinishDate())
									),
							cb.and( cb.greaterThanOrEqualTo(root.get("finishDate"), course.getFinishDate()),
									cb.lessThanOrEqualTo(root.get("finishDate"), course.getFinishDate())
									)
							)	
			);			
		} else {
			criteria.where(
					cb.and(
					    cb.notEqual(root.get("id"), course.getId()),		
						cb.or(
								cb.and( cb.greaterThanOrEqualTo(root.get("startDate"), course.getStartDate()),
										cb.lessThanOrEqualTo(root.get("startDate"), course.getFinishDate())
								),
								cb.and( cb.greaterThanOrEqualTo(root.get("finishDate"), course.getStartDate()),
										cb.lessThanOrEqualTo(root.get("finishDate"), course.getFinishDate())
								)
						)	
					)
			);	
		}
		criteria.select(cb.count(root));
		
		Long count = manager.createQuery(criteria).getSingleResult();
		return (count==0)?true: false;
	}
	
	private Predicate[] createFilters(String description, CriteriaBuilder builder, Root<Course> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(description)) {
			predicates.add(builder.like(builder.lower(root.get("description")),
					"%" + description.toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void addPageFilters(TypedQuery<Course> query, Pageable pageable) {
		int currentPage = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		int pageFirstRecord = currentPage * pageSize;

		query.setFirstResult(pageFirstRecord);
		query.setMaxResults(pageSize);
	
	}


}
