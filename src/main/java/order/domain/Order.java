package order.domain;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class Order {
    public enum State {
        NEW,
        PAID,
        SHIPPED,
        DELIVERED
    }

    private final String id;
    private final OrderItem[] items;
    private State state;

    public Order(OrderItem[] items, String id, State state) {
        this.id = id;
        this.items = Arrays.copyOf(items, items.length);
        this.state = State.NEW;
    }

    public String getId(){ return id; }
    public State getState(){ return state; }
    public void setState(State state) { this.state = state; }
    public OrderItem[] getItems() { return  Arrays.copyOf(items, items.length); }

    public Optional<Order> findById(String searchId){
        if(this.id.equals(searchId)) return Optional.of(this);
        else
            return Optional.empty();
    }
}
