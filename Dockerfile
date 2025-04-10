# Используем официальный образ Maven для сборки проекта
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файлы проекта
COPY pom.xml .
COPY src ./src

# Собираем проект
RUN mvn clean package -DskipTests

# Используем минимальный образ Java для запуска приложения
FROM eclipse-temurin:21-jre

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR файл из предыдущего этапа
COPY --from=build /app/target/grant_competition_spring-0.0.1-SNAPSHOT.jar app.jar

# Указываем команду для запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
