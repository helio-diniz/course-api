# Changelog

## [1.0.5] - 2020-02-18

### Added
- The `CustomTokenEnhancer` class was created to add the logged user name in the pay load area of JwtToken.
- The `ApplicationUser` class was created to wrap the `org.springframework.security.core.userdetails.User` class, adding a `AppUser` object for the current user. 
- The `loadUserByUsername` method of the `AppUserDetailsService` was changed to return new `ApplicationUser` object for the current user.
- Branch: `1_0_5_token_enhancer`.

### Changed
- The `AuthorizationServerConfig`, `OAuthSecurityConfig` and `ResourceServerConfig` classes were moved to `br.com.cast.avaliacao.config` package.
- Branch: `1_0_5_token_enhancer`.


## [1.0.4] - 2020-02-10

### Added
- New `V004__create_and_insert_users_and_permissions.sql` migration file to define user and permission tables.
- New `AppUser` and `Permission` model classes.
- New `AppUserRepository` repository interface;
- New `AuthorizationServerConfig`, `ResourceServerConfig` and `OAuthSecurityConfig` classes to configure the OAuth2 security.
- New `AppUserDetailService` service class to get user credentials from database.
- New `RefreshTokenPreProcessorFilter` filter class to get the refresh token from a cookie.
- New `RefreshTokenPostProcessor` filter class to remove the refresh token from the cookie on logout request.
- New `OAuthSecurityTest` and `OAuthTokenGenerator` to tests the access token getting.
- Branch: `1_0_4_api_security`.

### Changed
- The `CategoryControllerTest`, `CourseControllerTest`, `CursoModelTest` and `CourseServiceTest` classes to implement OAuth2 security in the test cases.
- The `CategoryController` and `CourseController` classes to implement authority and grant validation.
- The `CoursesQuery` interface and `CoursesQueryImpl` classes to implement the courses' summary searching.
- Branch: `1_0_4_api_security`.


## [1.0.3] - 2020-01-29

### Changed
- The `Category` and `Course` model class to implement the JPA bean validation at its attributes.
- The `CourseController` class to execute require JPA bean validation of the `saveCourse` and `updateCourse` methods' parameters.
- The `CoursesQueryImpl` to fix the date checking.
- The `application.properties` to force a fail when there is an unknown property in the HTTP body.
- Branch: `1_0_3_ui_integration`
 
### Added
- New `message.properties` file to configure the messages which are sent to users.
- New `ValidationMessages.properties` file to configure JPA bean validation messages which are sent to users.
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
- Branch: `1_0_2_rest_controller`.

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