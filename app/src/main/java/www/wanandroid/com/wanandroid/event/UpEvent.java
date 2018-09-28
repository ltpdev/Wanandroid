package www.wanandroid.com.wanandroid.event;

public class UpEvent {
    private boolean isUp;

    public UpEvent(boolean isUp) {
        this.isUp = isUp;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }
}
