## Запуск авто-тестов

 Запустить в терминале контейнеры: MySQL, Postgres, Node.js
  
``` 
docker-compose up -d
```
### Запуск для MySQL

Запустить SUT
``` 
java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar
```
Запустить тесты
```
gradlew clean test -Dtest.db.url=jdbc:mysql://localhost:3306/app
```
 

### Запуск для Postgres

Запустить SUT
```
java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/postgres -jar artifacts/aqa-shop.jar
```
Запустить тесты
```
gradlew clean test -Durl=jdbc:postgresql://localhost:5432/postgres
```

### Остановить и удалить контейнеры
``` 
docker-compose down
``` 
### Формировать Allure report для отчёта
```
gradlew allureReport
gradlew allureServe
```


