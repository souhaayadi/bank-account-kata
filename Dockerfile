# ============ 1️⃣ Build Angular Frontend ============ #
FROM node:20-alpine AS frontend-build
WORKDIR /app

# Copier uniquement le frontend
COPY frontend/ ./frontend/
WORKDIR /app/frontend

# Installer les dépendances et builder Angular
RUN npm install
RUN npm run build --prod

# ============ 2️⃣ Build Spring Boot Backend ============ #
FROM maven:3.9.6-eclipse-temurin-17 AS backend-build
WORKDIR /app

# Copier le backend
COPY backend/ ./backend/
WORKDIR /app/backend

# Copier le build Angular dans le dossier "static" de Spring Boot
COPY --from=frontend-build /app/frontend/dist/ /app/backend/src/main/resources/static/

# Compiler le backend et générer le JAR
RUN mvn clean package -DskipTests

# ============ 3️⃣ Image finale ============ #
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copier le JAR depuis le build Maven
COPY --from=backend-build /app/backend/target/*.jar app.jar

# Exposer le port
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
