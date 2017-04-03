package test;

import java.util.Optional;
import java.util.stream.IntStream;

import test.QueueStack.Queue;

public class BinaryTreeTraversal {

	public static <T extends Comparable<T>> void traverseByLevel(BinaryTree<T>.Node tree) {
		Queue<BinaryTree<T>.Node> currentLevel = new QueueStack().new Queue<>();
		currentLevel.push(tree);
		Optional.ofNullable(tree).ifPresent(node -> traverseByLevel(currentLevel));
	}

	public static <T extends Comparable<T>> void traverseByLevel(QueueStack.Queue<BinaryTree<T>.Node> queue) {
		Queue<BinaryTree<T>.Node> nextLevel = new QueueStack().new Queue<>();

		while (!queue.isEmpty()) {
			BinaryTree<T>.Node node = queue.pop();
			System.out.print(node.treasure + " ");
			Optional.ofNullable(node.left).ifPresent(left -> nextLevel.push(left));
			Optional.ofNullable(node.right).ifPresent(right -> nextLevel.push(right));
		}
		System.out.println();
		if (!nextLevel.isEmpty())
			traverseByLevel(nextLevel);
	}

	public static <T extends Comparable<T>> void inOrderWithStack(BinaryTree<T>.Node root) {
		QueueStack.Stack<BinaryTree<T>.Node> stack = new QueueStack().new Stack<>();
		BinaryTree<T>.Node current = root;
		stack.push(root);

		while (!stack.isEmpty()) {
			while (current != null) {
				stack.push(current);
				current = current.left;
			}
			current = stack.pop();
			if (current == null)
				break;
			System.out.print(current.treasure + " ");
			current = current.right;
		}
	}
	
	public static <T extends Comparable<T>> void breadthFirst(BinaryTree<T>.Node tree)  {
		if (tree == null)
			return;
		QueueStack.Queue<BinaryTree<T>.Node> queue = new QueueStack().new Queue<>();
		queue.push(tree);
		while (!queue.isEmpty()){
			BinaryTree<T>.Node current = queue.pop();
			System.out.print(current.treasure + " ");
			Optional.ofNullable(current.left).ifPresent(n -> queue.push(n));
			Optional.ofNullable(current.right).ifPresent(n -> queue.push(n));
		}
	}
	
	public static <T extends Comparable<T>> void inOrder(BinaryTree<T>.Node tree) {
		if (tree == null)
			return;
		inOrder(tree.left);
		System.out.print(tree.treasure + " ");
		inOrder(tree.right);
	}

	public static void main(String[] args) {
		BinaryTree<Integer> intTree = new BinaryTree<>();
		IntStream.range(0, 20).forEach(i -> intTree.add((int) (Math.random() * 100)));
		System.out.println("In-order:");
		inOrder(intTree.root);
		System.out.println("\n");
		System.out.println("In-order with stack:");
		inOrderWithStack(intTree.root);
		System.out.println("\n");
		System.out.println("By level:");
		traverseByLevel(intTree.root);
		System.out.println("\n");
		breadthFirst(intTree.root);
	}
}
