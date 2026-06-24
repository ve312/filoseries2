# FiloSeries

A full-stack web application that bridges philosophical concepts with television series to promote critical thinking through entertainment.

Built with **Spring Boot 3.4.5**, **Thymeleaf**, and **PostgreSQL**, this platform allows users to explore series, browse episodes, and read philosophical analyses that connect screen narratives with thinkers such as Nietzsche, Plato, and Kant.

## Overview

FiloSeries was developed as a personal educational project by a Software Engineering student. The application organizes content around three core layers: **Series** (with episodes grouped by season), **Philosophers & Categories**, and **Philosophical Analyses** (each linked to a specific episode, philosopher, and category). Users can register, log in, read localized analyses, and administrators can manage users and generate reports.

All content is stored in three languages (Spanish, English, German) with session-based runtime switching.

## Features

- **Series catalog** with multilingual titles, descriptions, and cover images
- **Episode browser** with season tabs, episode numbers, and previous/next navigation
- **Philosophical analyses** linked to episodes, philosophers, and philosophical categories (e.g., Ethics, Existentialism)
- **User authentication** (registration, login, logout) with form-based Spring Security
- **Role-based authorization** — regular users access content; administrators manage users
- **User management panel** (admin-only) — paginated listing, search by ID, toggle active/inactive status, delete users
- **PDF user report** generated via JasperReports (admin-only)
- **Internationalization (i18n)** — Spanish (default), English, and German with runtime language switching
- **Comment system** with moderation workflow (pending / approved / rejected) and optimistic locking
- **Responsive UI** built with Bootstrap 5, custom CSS per view, and client-side JavaScript validation
- **HTTPS** enabled via self-signed JKS keystore
- **OpenAPI documentation** via SpringDoc

## Technologies

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.4.5 |
| Web | Spring MVC, Thymeleaf, Thymeleaf Spring Security 6 |
| Security | Spring Security (form login, BCrypt, role-based) |
| Persistence | Spring Data JPA, Hibernate, PostgreSQL |
| Build | Maven (WAR packaging) |
| Reporting | JasperReports 7.0.0 |
| API Docs | SpringDoc OpenAPI 2.1.0 |
| Frontend | Bootstrap 5.3, Font Awesome, Google Fonts, jQuery |
| Validation | Jakarta Validation, client-side JS |
| Dev Tools | Spring Boot DevTools |

## Architecture

The application follows a **layered MVC architecture**:

```
Controller  →  Service (interface + impl)  →  Repository (Spring Data JPA)  →  Database
     ↓
Thymeleaf Templates (View)
```

- **Controllers** handle HTTP requests, populate the `Model`, and return view names.
- **Services** encapsulate business logic and localization utilities.
- **Repositories** extend `JpaRepository` for database access.
- **Templates** use Thymeleaf with a shared layout fragment (`layout.html`) for consistent navigation, footer, and alerts.
- **Security** is configured via `SecurityConfig` and `UserDetailsServiceImpl`.

## Project Structure

```
filoseries/
├── pom.xml
├── mvnw
├── src/
│   ├── main/
│   │   ├── java/ve312/com/filoseries/
│   │   │   ├── FiloseriesApplication.java
│   │   │   ├── ServletInitializer.java
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── WebConfig.java
│   │   │   │   ├── PasswordEncoderConfig.java
│   │   │   │   ├── UserDetailsServiceImpl.java
│   │   │   │   └── InactiveUserException.java
│   │   │   ├── controllers/
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── SerieController.java
│   │   │   │   ├── EpisodioController.java
│   │   │   │   ├── AnalisisFilosoficoController.java
│   │   │   │   └── AdminController.java
│   │   │   ├── domain/
│   │   │   │   ├── Serie.java
│   │   │   │   ├── Episodio.java
│   │   │   │   ├── AnalisisFilosofico.java
│   │   │   │   ├── CategoriaFilosofica.java
│   │   │   │   ├── Filosofo.java
│   │   │   │   ├── Comentario.java
│   │   │   │   ├── Usuario.java
│   │   │   │   └── Rol.java
│   │   │   ├── repository/
│   │   │   │   ├── SerieRepository.java
│   │   │   │   ├── EpisodioRepository.java
│   │   │   │   ├── AnalisisFilosoficoRepository.java
│   │   │   │   ├── CategoriaFilosoficaRepository.java
│   │   │   │   ├── FilosofoRepository.java
│   │   │   │   ├── ComentarioRepository.java
│   │   │   │   ├── UsuarioRepository.java
│   │   │   │   └── RolRepository.java
│   │   │   ├── service/
│   │   │   │   ├── SerieService / impl
│   │   │   │   ├── EpisodioService / impl
│   │   │   │   ├── AnalisisFilosoficoService / impl
│   │   │   │   ├── CategoriaFilosoficaService / impl
│   │   │   │   ├── FilosofoService / impl
│   │   │   │   ├── ComentarioService / impl
│   │   │   │   ├── UsuarioService / impl
│   │   │   │   └── RolService / impl
│   │   │   └── util/
│   │   │       ├── LocalizationUtil.java
│   │   │       └── MessageUtil.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── messages[_xx].properties
│   │       ├── reporte_usuarios.jrxml
│   │       ├── ve312.jks
│   │       ├── static/
│   │       │   ├── css/   (estilos*.css)
│   │       │   └── js/    (validacion*.js)
│   │       └── templates/
│   │           ├── layout.html
│   │           ├── home.html
│   │           ├── auth/       (login.html, registro.html)
│   │           ├── series/     (lista.html)
│   │           ├── detalle/    (serie.html, episodio.html, analisis.html)
│   │           ├── admin/      (usuarios.html)
│   │           └── error/      (acceso-denegado.html)
│   └── test/java/ve312/com/filoseries/
│       └── FiloseriesApplicationTests.java
```

## Database

The application uses **PostgreSQL** with Hibernate's `ddl-auto=update` for schema management.

### Schema overview

| Table | Description |
|---|---|
| `serie` | TV series with multilingual titles and descriptions |
| `episodio` | Episodes linked to a series (season + episode number) |
| `analisis_filosofico` | Philosophical analyses linked to episode, philosopher, and category |
| `filosofo` | Philosophers referenced in analyses |
| `categoria_filosofica` | Philosophical categories (multilingual) |
| `comentario` | User comments on analyses (with status and optimistic locking) |
| `usuario` | Registered users (BCrypt password, active/inactive state) |
| `rol` | User roles |
| `usuario_rol` | Many-to-many join table between users and roles |

## Security

- **Authentication**: Form-based login with Spring Security; passwords hashed with **BCrypt**.
- **Authorization**:
  - `/series/**`, `/episodios/**`, `/analisis/**` — accessible to `ROLE_USER` and `ROLE_ADMIN`
  - `/admin/**` — restricted to `ROLE_ADMIN`
  - `/login`, `/registro`, static resources — public
- **Inactive user handling**: Custom `InactiveUserException` and `AuthenticationFailureHandler` return a specific error message when a disabled user attempts to log in.
- **Access denied**: Custom error page at `/acceso-denegado`.
- **SSL**: HTTPS enabled with a self-signed JKS keystore (port 8443).

## Installation

### Prerequisites

- Java 21+
- Maven (or use the provided Maven wrapper `mvnw`)
- PostgreSQL running on `localhost:5432`

### Steps

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-username/filoseries.git
   cd filoseries
   ```

2. **Create the database**

   ```sql
   CREATE DATABASE filoseries;
   ```

3. **Configure credentials** (optional)

   Edit `src/main/resources/application.properties` and update the database password:

   ```properties
   spring.datasource.username=postgres
   spring.datasource.password=your_password
   ```

4. **Build and run**

   ```bash
   ./mvnw spring-boot:run
   ```

   The application starts on **`https://localhost:8443`**.

5. **Access the application**

   Open `https://localhost:8443` in your browser (accept the self-signed certificate warning).

## Running the Application

| Command | Description |
|---|---|
| `./mvnw spring-boot:run` | Run in development mode |
| `./mvnw package` | Build WAR for deployment |
| `./mvnw test` | Run tests |

The default admin credentials must be inserted directly into the database (no seed data is provided). To create an admin user, register via the UI, then manually set the `ROLE_ADMIN` role in the `usuario_rol` table.

## Configuration

Key properties in `application.properties`:

| Property | Value | Description |
|---|---|---|
| `server.port` | `8443` | HTTPS port |
| `server.ssl.enabled` | `true` | SSL enabled |
| `server.ssl.key-store` | `classpath:ve312.jks` | Keystore location |
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/filoseries` | Database connection |
| `spring.jpa.hibernate.ddl-auto` | `update` | Auto-schema management |
| `spring.jpa.show-sql` | `true` | SQL logging (dev) |
| `spring.thymeleaf.cache` | `false` | Template hot-reload (dev) |

To disable SSL for local development, set `server.ssl.enabled=false` and change the port to `8080`.

## Future Improvements

- **Admin CRUD for content** — currently, series, episodes, analyses, philosophers, and categories must be inserted directly into the database. An admin panel with forms would make content management self-service.
- **Comment moderation UI** — the database supports comment statuses (pending / approved / rejected), but no moderator interface currently exists to review or approve comments.
- **Search functionality** — repositories support searching by title, but there is no dedicated search page or endpoint exposed in the UI.
- **User profile page** — users cannot view or edit their own profile, check their comment history, or change their password.
- **Seed data / data migration** — adding a `data.sql` or Flyway migration would make it easier to bootstrap the database with initial content.
- **Unit and integration tests** — the project has only a single context-loading test; expanding test coverage would improve maintainability.
- **Docker support** — a `docker-compose.yml` for PostgreSQL + the application would simplify local setup.
- **REST API** — although SpringDoc is configured, no REST endpoints are implemented. Adding an API layer would enable future frontend clients.

## Author

Desarrollado por [Daniel Felipe Ordoñez Amaya](https://github.com/ve312)

---

*This project was created for educational purposes as part of a personal portfolio.*
