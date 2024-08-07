package ru.geekbrains.homework5;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *  2. Запустить проект и открыть страницу http://localhost:8080/v3/api-docs
 *  3. Сохранить текст в файл, отформатировать в читаемый
 *  4. Файл загрузить файл в гит репозиторий (не обязательно пустой репозиторий, можно и с проектом)
 *    и ссылку на файл прислать в качестве ответа на дз.
 *   Дополнительное:
 *  1. Пофантазировать на тему какое api будет у проекта для создания заметок.
 *  Сущности используемые в проекте: заметки, теги, пользователь(автор).
 *  2. Попробовать в обычном текстовом файле описать api в формате "GET /user/{id} - получить пользователя по ID"
 *  3. Прикрепить файл в тот же репозиторий. Дополнительно в ответе указать ссылку на этот текстовый файл
 */
@SpringBootApplication
public class Main {

	@SneakyThrows
	public static void main(String[] args) {
//		SpringApplication.run(Homework5Application.class, args);

		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

		//region JDBC true
//
//		DataSource dataSource = context.getBean(DataSource.class);
//		try (Connection connection = dataSource.getConnection()){
//			try (Statement statement = connection.createStatement()) {
//				statement.execute("create table books(id bigint, name varchar(512))");
//			}
//
//			try(Statement statement = connection.createStatement()) {
//				statement.execute("insert into books(id, name) values(1, 'Мастер и Маргарита')");
//			}
//
//			try(Statement statement = connection.createStatement()) {
//				ResultSet resultSet = statement.executeQuery("select * from books");
//
//				while (resultSet.next()) {
//					System.out.println(resultSet.getLong("id"));
//					System.out.println(resultSet.getString("name"));
//				}
//			}
//		}
		//endregion

		//region JDBC Spring

//		JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
//
//		jdbcTemplate.execute("create table readers(id bigint, name varchar(512))");
//		jdbcTemplate.execute("insert into readers(id, name) values(1, 'Андрей')");
//		jdbcTemplate.execute("insert into readers(id, name) values(2, 'Василий')");
//
//		List<Reader> list = jdbcTemplate.query("select * from readers", new RowMapper<Reader>() {
//			@Override
//			public Reader mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return new Reader(rs.getLong("id"), rs.getString("name"));
//			}
//		});
//		list.forEach(System.out::println);

		//endregion

	}

}
