package demo.utils.event;

/**
 * BaseEvent
 *
 * @author Wenzhou
 * @since 2023/6/15 17:06
 */
public abstract class BaseEvent implements Event {
    private Object sender;
    boolean asyncEvent;
    transient EventBusService bus;

    public BaseEvent() {
    }

    public Object getSender() {
        return this.sender;
    }

    public void setSender(Object sender) {
        this.sender = sender;
    }

    public boolean isAsyncEvent() {
        return this.asyncEvent;
    }

    public EventBusService getBus() {
        return this.bus;

    }
}
