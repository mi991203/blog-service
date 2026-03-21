# Repository Guidelines

## Project Structure & Module Organization
This repository is a multi-module Maven project rooted at `pom.xml`. Main modules are:

- `blog-api`: REST entrypoint and controllers under `src/main/java/com/blog/api`.
- `blog-user`: user domain DTOs, entities, services, MyBatis mappers, and mapper XML files.
- `blog-article`: article domain logic and its Spring Boot application.
- `blog-common`: shared constants, utilities, results, and cross-module support code.

Java sources follow the standard Maven layout in `src/main/java`; resources live in `src/main/resources`. SQL bootstrap data is in `db.sql`. Runtime logs are written to `logs/` and should not be committed.

## Build, Test, and Development Commands
- `mvn clean package`: build all modules and produce JARs.
- `mvn test`: run the current test suite across modules.
- `mvn -pl blog-api spring-boot:run`: start the API gateway module locally.
- `mvn -pl blog-article spring-boot:run`: run the article service directly.
- `mvn -pl blog-user mybatis-generator:generate`: regenerate MyBatis artifacts when generator config is added under module resources.

Run commands from the repository root unless you are targeting a single module.

## Coding Style & Naming Conventions
Use 4-space indentation and UTF-8 source files. Keep packages under `com.blog.<module>`. Class names use PascalCase (`UserController`, `LoginVO`); methods and fields use camelCase; constants use `UPPER_SNAKE_CASE`. Match existing suffixes: `*Controller`, `*Service`, `*Mapper`, `*DTO`, `*VO`, `*Application`.

Prefer Lombok and Spring annotations consistently with existing code. Keep MyBatis XML files in `src/main/resources/mapper` and align them with interface names such as `AppUserMapper.xml`.

## Testing Guidelines
Spring Boot test dependencies are present, but the repository currently has little or no committed test coverage. Add tests under `src/test/java` using the same package path as production code. Name unit tests `*Test` and integration-style tests `*IT` where needed. Run `mvn test` before opening a PR.

## Commit & Pull Request Guidelines
Recent commits use short, imperative Chinese summaries such as `文章点赞功能补充` and `article模块初始化`. Keep commit messages focused on one change. For pull requests, include:

- a short description of the problem and fix
- affected modules, such as `blog-user` or `blog-api`
- local verification steps or command output
- API examples or screenshots when request/response behavior changes

## Security & Configuration Tips
Do not commit secrets, local database credentials, or generated logs. Review `application.yml` before sharing branches, and keep environment-specific overrides out of version control.
