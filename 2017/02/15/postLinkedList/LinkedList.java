package test;

import java.util.stream.IntStream;

public class LinkedList<E> {

	Node root;

	class Node {
		E treasure;

		Node next;

		public Node(E treasure, Node next) {
			this.treasure = treasure;
			this.next = next;
		}
	}

	public void add(E e) {
		if (!search(e)) {
			if (root == null) {
				root = new Node(e, null);
			} else {
				root = new Node(e, root);
			}
		}
	}

	public E removeFirst() {
		if (root == null) {
			return null;
		} else {
			E e = root.treasure;
			root = root.next;

			return e;
		}
	}

	public boolean search(E e) {
		Node current = root;
		while (current != null) {
			if (current.treasure.equals(e))
				return true;

			current = current.next;
		}

		return false;
	}

	public boolean remove(E e) {
		if (root == null) {
			return false;
		}
		if (root.treasure.equals(e)) {
			root = root.next;
			return true;
		}

		Node current = root.next;
		Node previous = root;

		while (current != null) {
			if (current.treasure.equals(e)) {
				previous.next = current.next;
				return true;
			}
			previous = current;
			current = current.next;
		}

		return false;
	}

	public boolean edit(E target, E updateTo) {
		if (remove(target)) {
			add(updateTo);

			return true;
		} else
			return false;
	}

	public static void main(String[] args) {
		intTest();
	}

	public static void intTest() {
		LinkedList<Integer> l = new LinkedList<>();
		IntStream.range(0, 10).forEach(i -> l.add(i));
		System.out.println(l);
		System.out.println("5 in the list is " + l.search(5));
		System.out.println("10 in the list is " + l.search(10));
		System.out.println("remove first, it is " + l.removeFirst());
		System.out.println("remove first, it is " + l.removeFirst());
		System.out.println("remove first, it is " + l.removeFirst());
		System.out.println(l);
		System.out.println("remove 11 is " + l.remove(11));
		System.out.println("remove 6 is " + l.remove(6));
		System.out.println("remove 0 is " + l.remove(0));
		System.out.println(l);
		IntStream.range(0, 10).forEach(i -> l.add(i));
		System.out.println(l);
		System.out.println("Modify number 4 to 99 is " + l.edit(4, 99));
		System.out.println(l);
	}

	@Override
	public String toString() {
		Node current = root;
		StringBuilder listToString = new StringBuilder();
		listToString.append("====" + '\n');
		while (current != null) {
			listToString.append(current.treasure);
			listToString.append(" ");
			current = current.next;
		}
		listToString.append('\n' + "====");

		return listToString.toString();
	}
}
