package www.wanandroid.com.wanandroid.event;

public class RefreshEvent {
    private boolean isRefreshing;

    public RefreshEvent(boolean isRefreshing) {
        this.isRefreshing=isRefreshing;
    }

    public void setRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }
}
