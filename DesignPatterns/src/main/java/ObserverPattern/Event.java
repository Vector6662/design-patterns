package ObserverPattern;

public class Event<T> {
    private String event;
    private T obj;

    public Event(String event, T obj) {
        this.event = event;
        this.obj = obj;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
