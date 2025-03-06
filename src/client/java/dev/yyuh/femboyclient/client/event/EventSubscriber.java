package dev.yyuh.femboyclient.client.event;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class EventSubscriber {
    private final Consumer<Event> subscriberCaller;
    private final Class<? extends Event> eventClass;
    private final Object target;
    private final Method method;

    public EventSubscriber(Object target, Method method, Class<? extends Event> eventClass) {
        this.target = target;
        this.method = method;
        this.eventClass = eventClass;

        this.subscriberCaller = event -> {
            try {
                method.invoke(target, event);
            } catch (Exception e) {
                throw new RuntimeException("Failed to invoke event subscriber", e);
            }
        };
    }

    public void callSubscriber(Event event) {
        if (eventClass.isInstance(event)) {
            subscriberCaller.accept(event);
        }
    }

    public Class<? extends Event> getEventClass() {
        return eventClass;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }
}