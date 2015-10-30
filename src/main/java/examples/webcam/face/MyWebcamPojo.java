package examples.webcam.face;

public class MyWebcamPojo {
    
    private String message;
    private String image;

    public MyWebcamPojo(String base64Image, String message) {
        this.message = message;
        this.image = base64Image;
    }
    
    public MyWebcamPojo(String message) {

        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public String getMessage() {
        return message;
    }
}
