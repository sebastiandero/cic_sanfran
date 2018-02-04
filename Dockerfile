FROM openjdk:8
RUN mkdir /var/cicsanfran
COPY target/cic_sanfran-0.0.1-SNAPSHOT.jar /var/cicsanfran
COPY sanfran_movies_dataset.xml /var/cicsanfran
WORKDIR /var/cicsanfran
EXPOSE 8080 80
ENTRYPOINT ["java","-jar","cic_sanfran-0.0.1-SNAPSHOT.jar"]