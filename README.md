# Промышленный бэкенд 2023/24 - заключительный этап
## О проекте
Данный проект является RESTful API сервисом, позволяющим управлять списком кинофильмов и режиссеров
## Описание настроек
* Настройки представляют из себя следующий `yaml` файл:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/example
    username: postgres
    password: postgres
  flyway:
    baseline-on-migrate: true

```
* Измените url базы данных, если ваша локальная база данных называется иначе
  * Пример `url: jdbc:postgresql://localhost:5432/myDataBase`
* Измените credentials пользователя, через которого будут производиться действия с бд в целях безопасности
  * Пример `username: mySecureUser`,  `password: mySecurePassword`

## Как запустить
* Создать базу данных для postgresql с именем, указанном в `application.yaml`
* `git clone https://github.com/Kre4/itmo-backend-olymp.git`
* `cd itmo-backend-olymp`
* `./mvnw package`
* `java -jar target/*.jar`

## Что сделано:
### Реализованы эндпоинты
* Для управления кинофильмами
  * `/GET /api/movies` - список всех кинофильмов 
    * Пример ответа на запрос `curl http://localhost:8080/api/movies`
    ```json
      {
    "list": [
        {
            "id": 2,
            "title": "Example movie",
            "year": 2018,
            "director": 1,
            "length": "02:30:00",
            "rating": 8
        },
        {
            "id": 1,
            "title": "Example movie",
            "year": 2018,
            "director": 1,
            "length": "02:30:00",
            "rating": 8
        },
        {
            "id": 4,
            "title": "post test",
            "year": 2018,
            "director": 1,
            "length": "02:30:00",
            "rating": 8
        }
    ]
}
      ```
  * `GET /api/movies/:id` - информация о кинофильме с указанным id
    * Пример ответа `curl http://localhost:8080/api/movies/1`
    ```json
    {
    "movie": {
        "id": 1,
        "title": "Example movie",
        "year": 2018,
        "director": 1,
        "length": "02:30:00",
        "rating": 8
      }
    }
    ```
    * Или же по несуществующему идентификатору `curl -I http://localhost:8080/api/movies/900`
    ```json
    HTTP/1.1 404
    Content-Length: 0
    Date: Sat, 23 Mar 2024 15:48:43 GMT
    ```
  * `POST /api/movies` - добавление новой записи о кинофильме
    * Пример ответа
    ```json
    {
    "movie": {
        "id": 5,
        "title": "post test",
        "year": 2018,
        "director": 1,
        "length": "02:30:00",
        "rating": 8
      }
    }
    ```
  * `PATCH /api/movies/:id` - изменение информации о кинофильме с указанным id
    * Пример ответа:
    ```json
    {
    "movie": {
        "id": 2,
        "title": "post test",
        "year": 2018,
        "director": 1,
        "length": "02:30:00",
        "rating": 8
      }
    }
    ```
  * `DELETE /api/movies/:id` - удаление записи о кинофильме с указанным id
    * Пример ответа при удалении несуществующей записи
    ```json
    HTTP/1.1 404
    Content-Length: 0
    Date: Sat, 23 Mar 2024 15:59:43 GMT
    ```
* Для управлениями режиссерами
  * `/GET /api/directors` - список всех режиссеров
  * `GET /api/directors/:id` - информация о режиссере с указанным id
  * `POST /api/directors` - добавление новой записи о режиссере
  * `PATCH /api/directors/:id` - изменение информации о режиссере с указанным id
  * `DELETE /api/directors/:id` - удаление записи о режиссере с указанным id

### Написаны тесты
* `movie\src\test\java\com\itmo\olymp\MovieApplicationTests.java`
* 

## Что дальше?
