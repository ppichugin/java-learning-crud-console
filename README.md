## Консольное CRUD приложение 

### Задача:
Необходимо реализовать консольное CRUD приложение, которое взаимодействует с БД и позволяет выполнять все CRUD операции над сущностями:

```
Writer (id, firstName, lastName, List<Post> posts)
Post (id, content, created, updated, List<Label> labels)
Label (id, name)
PostStatus (enum ACTIVE, UNDER_REVIEW, DELETED)
```
> Результатом выполнения проекта должен быть отдельный репозиторий на github, с описанием задачи, проекта и инструкцией по локальному запуску проекта.

### Требования:
* Придерживаться шаблона **MVC** (пакеты model, repository, service, controller, view)
* Для миграции БД использовать https://www.liquibase.org/
* Сервисный слой приложения должен быть покрыт юнит тестами (junit + mockito)
* Для импорта библиотек использовать Maven

### Технологии: 
Java, MySQL, JDBC, Maven, Liquibase, JUnit, Mockito

### Инструкции для запуска: