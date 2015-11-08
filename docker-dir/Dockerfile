FROM resin/rpi-raspbian:wheezy
MAINTAINER Taariq Levack <taariq@mtq.io>

# Install dependencies
RUN echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu precise main" | tee -a /etc/apt/sources.list
RUN echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu precise main" | tee -a /etc/apt/sources.list
RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886 && apt-get update
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections
RUN apt-get install -y oracle-java8-installer git

# Define working directory
WORKDIR /var/rhiot

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# RUN usermod -a -G video pi

ADD face-detection-1.0-SNAPSHOT.jar /var/rhiot/app.jar

RUN bash -c 'touch /var/rhiot/app.jar'

EXPOSE 9090

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/var/rhiot/app.jar"]
