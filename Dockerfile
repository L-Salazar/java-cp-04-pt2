# Etapa 1: Build da aplicação
FROM ubuntu:latest AS build

# Atualiza pacotes e instala o JDK 17 e Maven
RUN apt-get update && apt-get install -y openjdk-17-jdk maven

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml e o código fonte
COPY pom.xml .
COPY src ./src

# Compila o projeto (sem testes)
RUN mvn clean install -DskipTests

# Etapa 2: Execução
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Copia o JAR gerado da etapa de build
COPY --from=build /app/target/performancekids-0.0.1-SNAPSHOT.jar /app/app.jar

# Define o comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
