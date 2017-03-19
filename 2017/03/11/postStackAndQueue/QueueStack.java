package test;

import java.util.stream.IntStream;

public class QueueStack {

	class Stack<E> {
		LinkedList<E> stack;
		int currentAmount;

		public Stack() {
			this.stack = new LinkedList<>();
			this.currentAmount = 0;
		}

		public void push(E e) {
			this.stack.add(e);
			this.currentAmount++;
		}

		public E pop() {
			if (this.currentAmount > 0) {
				this.currentAmount--;
				return this.stack.removeFirst();
			} else
				return null;
		}

		public E peek() {
			if (isEmpty())
				return null;

			E e = pop();
			push(e);
			;
			return e;
		}

		public int size() {
			return currentAmount;
		}

		public boolean isEmpty() {
			return currentAmount == 0;
		}
	}

	class Queue<E> {
		Node top;
		Node bottom;
		int size;

		class Node {
			E e;
			Node previous;
			Node next;

			public Node(E e, Node previous, Node next) {
				this.e = e;
				this.previous = previous;
				this.next = next;
			}
		}

		public Queue() {
			this.top = null;
			this.bottom = null;
			this.size = 0;
		}

		public void push(E e) {
			this.top = new Node(e, null, top);
			if (size++ == 0) {
				this.bottom = top;
			} else {
				this.top.next.previous = top;
			}
		}

		public E pop() {
			if (size == 0)
				return null;
			E e = bottom.e;
			this.bottom = bottom.previous;
			if (this.bottom != null)
				this.bottom.next = null;
			size--;
			return e;
		}

		public E peek() {
			if (size == 0)
				return null;
			return bottom.e;
		}

		public int size() {
			return size;
		}

		public boolean isEmpty() {
			return size == 0;
		}
	}

	public static void main(String[] args) {
		Queue<Integer> IS = new QueueStack().new Queue<>();

		IntStream.range(0, 10).forEach(i -> IS.push(i));

		while (!IS.isEmpty()) {
			System.out.print(IS.pop() + " ");
		}

		System.out.println();

		System.out
				.println(String.format("now there are %s numbers in stack, the top one is: %s", IS.size(), IS.peek()));
		IS.push(Integer.valueOf((int) (Math.random() * 100)));
		System.out
				.println(String.format("now there are %s numbers in stack, the top one is: %s", IS.size(), IS.peek()));
	}
}
