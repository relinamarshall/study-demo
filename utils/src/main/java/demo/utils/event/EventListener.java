package demo.utils.event;

/**
 * EventListener
 *
 * @author Wenzhou
 * @since 2023/6/15 17:09
 */
public interface EventListener <T extends Event>{
    void onEvent(T t) throws Exception;
}
