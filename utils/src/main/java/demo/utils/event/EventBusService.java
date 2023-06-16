package demo.utils.event;

/**
 * EventBusService
 *
 * @author Wenzhou
 * @since 2023/6/15 17:08
 */
public interface EventBusService {
    <T extends Event> void subscribe(Class<T> tClass, EventListener<T> listener);

    <T extends Event> void removeListener(Class<T> tClass, EventListener<T> listener);

    <T extends BaseEvent> void post(T t);

    <T extends BaseEvent> void send(T t);
}
