package dev.yyuh.femboyclient.client.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
    private final Map<Class<? extends Event>, List<EventSubscriber>> subscribers = new HashMap<>();

    public void subscribe(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1 && Event.class.isAssignableFrom(parameterTypes[0])) {
                    Class<? extends Event> eventClass = (Class<? extends Event>) parameterTypes[0];
                    EventSubscriber subscriber = new EventSubscriber(object, method, eventClass);

                    subscribers.computeIfAbsent(eventClass, k -> new ArrayList<>()).add(subscriber);
                }
            }
        }
    }

    public void unsubscribe(Object object) {
        for (List<EventSubscriber> subscriberList : subscribers.values()) {
            subscriberList.removeIf(subscriber -> subscriber.getTarget() == object);
        }
    }

    public void post(Event event) {
        List<EventSubscriber> subscriberList = subscribers.get(event.getClass());
        if (subscriberList != null) {
            for (EventSubscriber subscriber : subscriberList) {
                subscriber.callSubscriber(event);
            }
        }
    }
}