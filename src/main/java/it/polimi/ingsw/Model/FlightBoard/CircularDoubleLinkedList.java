package it.polimi.ingsw.Model.FlightBoard;

import java.io.Serializable;

public class CircularDoubleLinkedList implements Serializable {

    private Node head = null;

    private Node tail = null;

    public CircularDoubleLinkedList(int size) {
        for (int i = 0; i < size; i++) {
            add(0, i);
        }
    }

    public void add(int value, int index) {
        Node newNode = new Node(value, index);
        if (head == null) {
            head = newNode;
            tail = newNode;
            head.next = tail;
            tail.prev = head;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            head.prev = newNode;
            tail = newNode;
        }
    }

    public void insertValue(int id, int index) {
        if (head == null)
            return;
        Node pointed = head;
        for (int i = 0; i < index; i++) {
            pointed = pointed.next;
        }
        pointed.value = id;
    }

    public int getValue(int index) {
        if (head == null)
            return -1;
        Node pointed = head;
        for (int i = 0; i < index; i++) {
            pointed = pointed.next;
        }
        return pointed.value;
    }

    public int getIndex(int value) {
        if (head == null)
            return -1;
        Node pointed = head;
        while (pointed.value != value) {
            pointed = pointed.next;
        }
        return pointed.index;
    }

    public boolean moveForward(int value) {
        Node pointed = head;
        boolean newLap = false;
        do {
            if (pointed.value == value) {
                if (pointed.next.value == 0) {
                    pointed.value = 0;
                    pointed.next.value = value;
                    if (pointed.next == head) {
                        newLap = true;
                    }
                    return newLap;
                } else if (pointed.value == value && pointed.next.value != 0) {
                    pointed.value = 0;
                    pointed = pointed.next;
                    if (pointed == head) {
                        newLap = true;
                    }
                    while (pointed.value != 0) {
                        pointed = pointed.next;
                        if (pointed == head) {
                            newLap = true;
                            break;
                        }
                    }
                    pointed.value = value;
                    return newLap;
                }
            }
            pointed = pointed.next;
        } while (pointed != head);
        return false;
    }

    public boolean moveBackward(int value) {
        Node pointed = head;
        boolean newLap = false;
        do {
            if (pointed.value == value) {
                if (pointed.prev.value == 0) {
                    pointed.value = 0;
                    pointed.prev.value = value;
                    if (pointed.prev == head) {
                        newLap = true;
                    }
                    return newLap;
                } else if (pointed.value == value && pointed.prev.value != 0) {
                    pointed.value = 0;
                    pointed = pointed.prev;
                    if (pointed == head) {
                        newLap = true;
                    }
                    while (pointed.value != 0) {
                        if (pointed == head) {
                            newLap = true;
                        }
                        pointed = pointed.prev;
                        if (pointed == head) {
                            newLap = true;
                        }
                    }
                    pointed.value = value;
                    return newLap;
                }
            }
            pointed = pointed.next;
        } while (pointed != head);
        return false;
    }

    public int getLength() {
        Node pointed = head;
        int length = 0;
        do {
            pointed = pointed.next;
            length++;
        } while (pointed != head);
        return length;
    }

    private static class Node implements Serializable {

        int value;

        int index;

        Node next;

        Node prev;

        Node(int value, int index) {
            this.value = value;
            this.index = index;
            this.next = null;
            this.prev = null;
        }
    }
}




