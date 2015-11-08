# face-detection
Face detection using camel-webcam -> camel-openimaj

### Clone repo
git clone https://github.com/levackt/face-detection.git

### Build
cd face-detection

mvn install

### Run
java -jar target/face-detection-1.0-SNAPSHOT.jar

### Open browser (given Pi is named rhiot-pi, and rhiot-pi.local points to given Pi)
http://rhiot-pi.local:9090/index.html 

Smile :)

### Docker on Pi

###todo reference getting started on Hypriot blog with raspi-config to enable camera etc

###todo copy fatjar to docker-dir

For now, copy/sftp the fat jar and the docker-dir/Dockerfile to the Pi.
#### On the Pi, Build the image;
 
    docker build -t myrhiot/face-detector .'
    
Now run it and be sure to specify the video device, or simply --privileged as follows;
    
    docker run -it --privileged -p 9090:9090 myrhiot/face-detector 
 