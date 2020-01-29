# Changelog

## [1.0.2] - 2020-01-xx

### Added
- New `message.properties` file to configure the messages which are sent to users.
- New `ValidationMessages.properties` file to configure the JPA bean validation messages which are sent to users.
- New `CorsFilter` class to implement a filter which handles with the CORS constraint.
- New `ExceptionHandler` class to handle with the exceptions below: 
	- the `HttpMessageNotReadableException` exception when HTTP body contains invalid values;
	- the `MethodArgumentNotValidException` exception when there are invalid arguments in the HTTP method;
	- the `EmptyResultDataAccessException` exception when the resource is not found.
	- the `DataIntegrityViolationException` exception when an attempt to insert or update data results in violation of an integrity constraint.
	- the BusinessException` exception when there is a violation of any business rule. 
 - New `CategoryController` REST controller class. 
 - New `CategoryControllerTest` test class.
- Branch: `1_0_3_ui_integration`.

## [1.0.2] - 2020-01-22

### Changed
- The `Courses` repository, `CoursesQuery` interface and `CoursesQueryImpl` to implement the description filter and date checking.
- The `CourseService` and `CourseServiceTest` class to add the Business Exception verification. 

### Added
- The `CourseController` and `CourseControlleTest` to implement the REST controller.
- The `V003__insert_courses.sql` migration to input some Course's data.
- Branch: `1_0_2_rest_controller`.


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