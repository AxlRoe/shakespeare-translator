FROM openjdk:11
EXPOSE 5000
ADD target/shakespeare-translator.jar shakespeare-translator.jar
ENTRYPOINT ["java","-jar","/shakespeare-translator.jar"]
