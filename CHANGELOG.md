# Changelog

## [1.0.1] - 2020-01-21 

### Added
- New `Category` and `Course` model classes.
- New `Categories`, `Courses` and `CoursesQuery` interface to implement Hibernate repositories;
- New `CoursesQueryImpl` classes to implement the course finding filtered by description and the date checking.
- New `CategoryService` and `CourseService` classes.
- New `BusinessException` run time exception to inform validation messages in the service. 
- New `CourseModelTest` and `CourseServiceTest` classes to implement the model's and service's unit tests.
- Branch: `1_0_1_model_and_service`.


## [1.0.0] - 2020-01-18 

### Added
- New Spring Boot Project for RESTFUL API development.
- New `V001__create_and_insert_categories.sql` and `V002__create_course.sql` migrations files to create the `categoria` and `produto` datatables.  
- Branch: `1_0_0_creating_project`.