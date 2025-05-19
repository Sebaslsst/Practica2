package practica.com.base.controller.datastruct.queue;

import practica.com.base.controller.datastruct.list.*;;

public class QuequeImplementation<E> extends LinkedList<E> {
    private Integer top;

    public Integer getTop() {
        return this.top;
    }

    public QuequeImplementation(Integer top){
        this.top = top;
    }

    protected Boolean isFullQueque() {
        return this.top >= getLength();
    }

    protected void queue(E info) throws Exception {
        if(!isFullQueque()) {
            add(info);
        } else {
            throw new ArrayIndexOutOfBoundsException("Queque full");
        }
    }
    protected E dequeue() throws Exception {       
        return deleteFirst();
        
    }
}