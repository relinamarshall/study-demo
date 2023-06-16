package demo.utils.event;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.collect.HashMultimap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * DefaultEventBusService
 *
 * @author Wenzhou
 * @since 2023/6/15 17:13
 */
public class DefaultEventBusService implements EventBusService {
    private static final Logger log = LoggerFactory.getLogger(DefaultEventBusService.class);
    private final int maxAsyncThreads;
    private Executor executor;
    private HashMultimap<Type, EventListener> listeners;

    public DefaultEventBusService(int maxAsyncThreads, Executor executor, HashMultimap<Type, EventListener> listeners) {
        this.maxAsyncThreads = maxAsyncThreads;
        this.listeners = HashMultimap.create();
        this.init();
    }

    private void init() {
        ThreadPoolExecutor tExecutor = new ThreadPoolExecutor(this.maxAsyncThreads, this.maxAsyncThreads,
                60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        tExecutor.allowCoreThreadTimeOut(true);
        this.executor = TtlExecutors.getTtlExecutorService(tExecutor);
    }

    protected void onAsyncEvent(BaseEvent evt) {
        Set<EventListener> listenerSet = this.findListeners(evt.getClass(), new HashSet<>());
        for (EventListener eventListener : listenerSet) {
            try {
                eventListener.onEvent(evt);
            } catch (Exception e) {
                log.error("failed to handle event:{}", evt, e);
            }
        }
    }

    @Override
    public <T extends Event> void subscribe(Class<T> evtType, EventListener<T> listener) {
        this.listeners.put(evtType, listener);
    }

    @Override
    public <T extends Event> void removeListener(Class<T> evtType, EventListener<T> listener) {
        this.listeners.remove(evtType, listener);
    }

    @Override
    public <T extends BaseEvent> void post(T event) {
        event.asyncEvent = true;
        event.bus = this;
        this.executor.execute(new DefaultEventBusService.AsyncEventTask(this, event));
    }

    @Override
    public <T extends BaseEvent> void send(T event) {
        try {
            event.asyncEvent = false;
            event.bus = this;
            this.onSyncEvent(event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected  <T extends BaseEvent> void onSyncEvent(T evt) throws Exception {
        Set<EventListener> listenerSet = this.findListeners(evt.getClass(), new HashSet<>());
        for (EventListener eventListener : listenerSet) {
            eventListener.onEvent(evt);
        }

    }

    private Set<EventListener> findListeners(Class<? extends BaseEvent> type, Set<EventListener> listeners) {
        Set<EventListener> foundSet = this.listeners.get(type);
        listeners.addAll(foundSet);
        Type[] evtInterfaces = type.getGenericInterfaces();
        for (Type evtInterface : evtInterfaces) {
            Set<EventListener> set = this.listeners.get(evtInterface);
            listeners.addAll(set);
        }

        for (Class superClass = type.getSuperclass(); !Object.class.equals(superClass);
             superClass = superClass.getSuperclass()) {
            this.findListeners(superClass, listeners);
        }
        return listeners;
    }


    private static class AsyncEventTask implements Runnable {
        private final DefaultEventBusService eventBusService;
        private BaseEvent evt;

        public AsyncEventTask(DefaultEventBusService eventBusService, BaseEvent evt) {
            this.eventBusService = eventBusService;
            this.evt = evt;
        }

        public void run() {
            this.eventBusService.onAsyncEvent(this.evt);
        }
    }
}
