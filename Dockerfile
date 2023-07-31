FROM openjdk:17.0.1-jdk-slim
#COPY --from=python:3.10.12 / /

EXPOSE 9000

WORKDIR app
COPY target/TLADocumentDigitization-0.0.1-SNAPSHOT.jar ./
COPY python/requirements.txt ./

RUN apt update
RUN apt install python3.9 -y
RUN apt install pip -y
RUN pip install -r /app/requirements.txt
RUN apt install -y libglib2.0-0 libsm6 libxrender1 libxext6
RUN apt install -y libgl1
#RUN "pip3 install -r /app/requirements.txt"

CMD java -jar TLADocumentDigitization-0.0.1-SNAPSHOT.jar