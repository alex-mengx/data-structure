package test;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.IntStream;

public class BinaryTree<T extends Comparable<T>> {

	Node root;

	class Node {

		T treasure;
		Node left;
		Node right;

		public Node(final T treasure, final Node left, final Node right) {
			this.treasure = treasure;
			this.left = left;
			this.right = right;
		}

		public boolean equals(Node node) {
			return treasure.equals(node.treasure);
		}

		@Override
		public String toString() {
			return Optional.ofNullable(left).map(node -> node.toString()).orElse(" ") + treasure.toString()
					+ Optional.ofNullable(right).map(node -> node.toString()).orElse(" ");
		}
	}

	public void add(final T t) {
		if (t == null)
			return;
		if (root == null)
			root = new Node(t, null, null);
		else
			add(t, root);
	}

	private void add(final T t, final Node root) {
		if (root.treasure.equals(t))
			return;
		else if (root.treasure.compareTo(t) > 0) {
			if (root.left == null)
				root.left = new Node(t, null, null);
			else
				add(t, root.left);
		} else {
			if (root.right == null)
				root.right = new Node(t, null, null);
			else
				add(t, root.right);
		}
	}

	public boolean contains(final T t) {
		if (t == null || root == null)
			return false;
		else
			return contains(t, root);
	}

	private boolean contains(final T t, final Node root) {
		if (root == null)
			return false;
		if (root.treasure.equals(t))
			return true;

		if (root.treasure.compareTo(t) > 0)
			return contains(t, root.left);
		else
			return contains(t, root.right);
	}

	public void remove(final T t) {
		root = remove(t, root);
	}

	private Node remove(final T t, final Node root) {
		if (root == null)
			return null;
		else if (root.treasure.compareTo(t) > 0)
			root.left = remove(t, root.left);
		else if (root.treasure.compareTo(t) < 0)
			root.right = remove(t, root.right);
		else if (root.left != null && root.right != null){
			root.treasure = findMax(root.left);
			root.left = remove(root.treasure, root.left);
		} else return root.left == null ? root.right : root.left;
		return root;
	}

	private T findMax(Node root) {
		if (root.right != null)
			return findMax(root.right);
		return root.treasure;
	}
	
	public void modify(T oldT, T newT){
		if (oldT == null || root == null || oldT.equals(newT))
			return;
		if(newT == null)
			remove(oldT);
		else if (contains(oldT)){
			remove(oldT);
			add(newT);
		}
	}

	@Override
	public String toString() {
		return Optional.ofNullable(root).map(root -> root.toString()).orElse(" ");
	}

	public void printTree(final Queue<Node> level) {
		if (level.isEmpty())
			return;
		final Queue<Node> newLevel = new LinkedList<>();
		for (final Node node : level) {
			if (node == null) {
				continue;
			}
			System.out.print(node.treasure + " ");
			newLevel.add(node.left);
			newLevel.add(node.right);
		}
		System.out.println();
		printTree(newLevel);
	}

	public static void main(final String[] args) {
		final BinaryTree<Integer> tree = new BinaryTree<>();
		IntStream.range(0, 20).forEach(i -> tree.add((int)(Math.random()*20)));

		final Queue<BinaryTree<Integer>.Node> q = new LinkedList<>();
		q.add(tree.root);
		tree.printTree(q);
		
		IntStream.range(0, 20).forEach(i -> System.out.print(i + ": " + tree.contains(i) + " | "));
		
		System.out.println();
		System.out.print("4: " + tree.contains(4) + " - ");
		tree.remove(4);
		System.out.print(tree.contains(4));
		System.out.println();
		System.out.println();
		tree.printTree(q);
		
		tree.add(15);
		System.out.println(tree.toString());
		tree.modify(15, 99);
		System.out.println(tree.toString());
	}
}
