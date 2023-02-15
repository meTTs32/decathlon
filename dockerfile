FROM maven:latest

COPY . .

WORKDIR .

RUN apt-get -y update
RUN apt-get -y upgrade
RUN apt-get install -y libxrender1 libxtst6 libxi6
RUN mvn clean package

CMD ["java", "-jar", "/target/decathlon-workbench-jar-with-dependencies.jar"]