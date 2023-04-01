FROM eclipse-temurin:17-jdk-alpine

#ENV DB_HOSTNAME $host
#ENV DB_PORT $dbport
#ENV DB_NAME $dbname
#ENV DB_USERNAME $username
#ENV DB_PASSWORD $pass

COPY . /app

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos Gradle para o contêiner
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .

# Executa o build do Gradle
RUN ./gradlew clean build

# Copia o diretório de origem do código para o contêiner
COPY src /src

# Expõe a porta do aplicativo
EXPOSE 8080

# Define o comando padrão para ser executado quando o contêiner é iniciado
CMD ["java", "-jar", "build/libs/hackaton-0.0.1.jar"]
