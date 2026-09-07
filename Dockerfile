# 1. Etapa de Build (compilação do Java com Maven)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia os arquivos de dependência primeiro (otimiza cache do Docker)
COPY pom.xml .
COPY src ./src

# Compila e gera o arquivo .jar (pulando os testes no build da imagem)
RUN mvn clean package -DskipTests

# 2. Etapa de Execução (Imagem final leve apenas com o JRE)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão da API
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]