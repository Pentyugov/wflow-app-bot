#FROM openjdk:11
#
#COPY target/zolloz.war zolloz-server.war
#
#CMD ["java", "-jar", "/zolloz-server.war"]

FROM maven:3.8.5-jdk-11

COPY ./ ./

RUN mvn clean package -DskipTests=true

CMD ["java", "-jar", "/target/wflow-bot.jar"]