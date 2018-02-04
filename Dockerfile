FROM openjdk:8
RUN mkdir /var/cicsanfran
COPY target/cic_sanfran-0.0.1-SNAPSHOT.jar /var/cicsanfran
WORKDIR /var/cicsanfran
ENTRYPOINT ["java","-jar","cic_sanfran-0.0.1-SNAPSHOT.jar"]