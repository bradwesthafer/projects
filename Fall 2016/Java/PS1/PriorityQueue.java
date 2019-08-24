import java.util.ArrayList;
import java.util.Collections;

public class PriorityQueue<T> {
    private ArrayList<PriorityItem> q = new ArrayList<PriorityItem>();

    void enqueue(T item, int priority) {
        q.add(new PriorityItem(item, priority));
    }

    T dequeue() {
        int itemToDequeue = 0;
        for (int i = 1; i < q.size(); i++) {
            if (q.get(i).getPriority() > q.get(itemToDequeue).getPriority()) {
                itemToDequeue = i;
            }
        }
        return (T)q.remove(itemToDequeue).getItem();
    }
}

class PriorityItem<T> {
    private T item;
    private int priority;

    PriorityItem(T item, int priority) {
        this.priority = priority;
        this.item = item;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}

class main {
    public static void main(String[] args) {
        PriorityQueue q = new PriorityQueue();
        System.out.println("Testing PriorityQueue");
        System.out.println("First, enqueue 2 with priority of 15.");
        q.enqueue(2, 15);
        System.out.println("Next, enqueue 4 with priority of 10");
        q.enqueue(4, 10);
        System.out.println("Next, enqueue 3 with priority of 15");
        q.enqueue(3, 15);
        System.out.println("Next, enqueue 5 with priority of 2");
        q.enqueue(5, 2);
        System.out.println("Finally, enqueue 1 with priority of 21");
        q.enqueue(1, 21);
        System.out.println("Now dequeue and print each number on a separate line");
        for (int i = 0; i < 5; i++) {
            System.out.println(q.dequeue());
        }
    }
}