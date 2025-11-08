# ---------- STAGE 1: Build ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Cache de dependências
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Código-fonte
COPY src ./src

# Build (sem testes)
RUN mvn -q clean package -DskipTests

# ---------- STAGE 2: Runtime ----------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# (Opcional) timezone PT-BR
# RUN apk add --no-cache tzdata && cp /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime

# A porta exposta é só informativa; o Render usa $PORT
EXPOSE 8080

# Copia o jar gerado (qualquer nome) e renomeia para app.jar
COPY --from=build /app/target/*.jar /app/app.jar

# Garanta que o Spring suba na porta $PORT do Render
# Ative perfil se quiser (ex.: prod) e passe envs do Oracle no painel do Render
ENV JAVA_OPTS="-Dserver.port=${PORT} -Dspring.profiles.active=prod"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
