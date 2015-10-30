
package examples.webcam.face;

import io.rhiot.component.webcam.WebcamComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.boot.FatJarRouter;
import org.apache.commons.codec.binary.Base64;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * A Camel route that streams images from the webcam, and if a face is detected it is sent to the web-socket to be viewed from a web page
 */
@SpringBootApplication
public class FaceDetectionRoute extends FatJarRouter {

    @Autowired
    private CamelContext camelContext;
    
    @Override
    public void configure() throws Exception {

        //1. Poll the webcam (max) every 500ms, forward only images when we're reasonably sure there's a human
        from("webcam:cam?consumer.delay=500").routeId("face").streamCaching().setHeader("image", bodyAs(byte[].class)).
                enrich("openimaj:face-detection?confidence=5", (oldExchange, newExchange) -> {
        
            DetectedFace detectedFace = newExchange.getIn().getBody(DetectedFace.class);
            
            //2. Base64 encode the image and indicate confidence in the face detection
            if (detectedFace != null) {
                String message = "Face detected with confidence : [" + detectedFace.getConfidence() + "]";
                LOG.info(message);
                
                oldExchange.getOut().setBody(new MyWebcamPojo(Base64.encodeBase64String(oldExchange.getIn().getHeader("image", byte[].class)), message));
            } else {
                oldExchange.getOut().setBody(new MyWebcamPojo("Face undetected"));
                LOG.warn("Face undetected");
            }
            return oldExchange;
            //3. marshal to JSON and send to websocket. Also have camel-websocket serve static resources from classpath:webapp
        }).marshal().json(JsonLibrary.Jackson).to("websocket://camel-webcam?port=9090&sendToAll=true&staticResources=classpath:webapp");
    }

    @Bean(name = "webcam")
    public WebcamComponent getWebcamComponent(){

        WebcamComponent webcam = new WebcamComponent(camelContext);
        webcam.setV4l2WebcamLoadingCommand("sudo " + webcam.getV4l2WebcamLoadingCommand());
        return webcam;
    }

    private static final Logger LOG = LoggerFactory.getLogger(FaceDetectionRoute.class);
}