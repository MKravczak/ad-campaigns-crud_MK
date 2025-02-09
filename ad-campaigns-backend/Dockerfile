# Wybieramy obraz bazowy z OpenJDK 21
FROM openjdk:21-jdk as build

# Ustalamy katalog roboczy w kontenerze
WORKDIR /app

# Kopiujemy plik pom.xml i katalog src do kontenera
COPY pom.xml .
COPY src ./src

# Instalacja Maven
RUN apt-get update && apt-get install -y maven

# Budujemy aplikację z Mavenem (ale nie tworzymy pliku .jar)
RUN mvn clean install -DskipTests

# Wybieramy obraz bazowy dla aplikacji (będziemy uruchamiać Spring Boot z Mavenem)
FROM openjdk:21-jdk-slim

# Ustalamy katalog roboczy w kontenerze
WORKDIR /app

# Kopiujemy tylko plik pom.xml i katalog src do kontenera
COPY pom.xml .
COPY src ./src

# Instalacja Mavena
RUN apt-get update && apt-get install -y maven

# Ustawiamy zmienną środowiskową, która ustawia port na 8080
ENV SERVER_PORT=8080

# Otwieramy port 8080
EXPOSE 8080

# Uruchamiamy aplikację Spring Boot przy pomocy komendy Maven
CMD ["mvn", "spring-boot:run"]
