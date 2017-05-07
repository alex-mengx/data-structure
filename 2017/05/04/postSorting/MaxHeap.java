package test;

import java.lang.reflect.Array;

@SuppressWarnings("unchecked")
public class MaxHeap<E extends Comparable<E>> {
    private E[] heap;
    private int currentIndex;

    public MaxHeap(Class<E> c, final int size) {
        final E[] a = (E[]) Array.newInstance(c, size);
        heap = a;
        currentIndex = 1;
    }

    public void push(E e) {
        if (isFull())
            throw new ArrayIndexOutOfBoundsException("This Heap Is Full!");

        heap[currentIndex] = e;
        int i = currentIndex;
        while (i > 1) {
            if (heap[i].compareTo(heap[i / 2]) > 0)
                swap(i, i / 2);
            else
                break;
            i /= 2;
        }
        currentIndex++;
    }

    public E poll() {
        if (isEmpty())
            throw new IllegalStateException("This Heap Is Empty!");

        currentIndex--;
        E max = heap[1];
        heap[1] = heap[currentIndex];
        heap[currentIndex] = null;
        int i = 1;
        while (i <= currentIndex / 2) {
            int larger = 0;
            if (heap[i * 2] == null)
                break;
            else if (heap[i * 2 + 1] == null)
                larger = i * 2;
            else
                larger = heap[i * 2].compareTo(heap[i * 2 + 1]) > 0 ? i * 2 : i * 2 + 1;

            if (heap[i].compareTo(heap[larger]) < 0)
                swap(i, larger);
            else
                break;
            i = larger;
        }
        return max;
    }

    public boolean isEmpty() {
        return currentIndex == 1;
    }

    public boolean isFull() {
        return currentIndex == heap.length - 1;
    }

    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public static void main(String[] args) {
        MaxHeap<Integer> heap = new MaxHeap<>(Integer.class, 11);

        while (!heap.isFull())
            heap.push((int) (Math.random() * 100));
        while (!heap.isEmpty())
            System.out.print(heap.poll() + " ");

        System.out.println();

        while (!heap.isFull())
            heap.push((int) (Math.random() * 100));
        while (!heap.isEmpty())
            System.out.print(heap.poll() + " ");
    }
}
