package demo.utils.event;

/**
 * BaseModelEvent
 *
 * @author Wenzhou
 * @since 2023/6/16 10:01
 */
public abstract class BaseModelEvent extends BaseEvent {
    private final String[] keys;

    protected BaseModelEvent(String... keys) {
        this.keys = keys;
    }

    public String[] getKeys() {
        return keys;
    }
}
