package NoThymeleaf.VideoCallSinThymeleaf.messasingstompwebsocket;

public class HelloMessage {

    private String text;

    public HelloMessage() {
    }

    public HelloMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
