FROM iits/jdk17-docker
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=test_mirali_bot
ENV BOT_TOKEN=5687760514:AAGLIZn14rYSuREKO4Sxl0RQM0z3QG_eEZU
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-jar", "/app.jar"]
