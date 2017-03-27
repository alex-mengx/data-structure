import java.util.Optional;
import java.util.stream.IntStream;

public class BinaryTreeDepthFirst {

	public static <T extends Comparable<T>> void preOrder(BinaryTree<T>.Node tree) {
		System.out.print(tree.treasure + " ");
		Optional.ofNullable(tree.left).ifPresent(node -> preOrder(node));
		Optional.ofNullable(tree.right).ifPresent(node -> preOrder(node));
	}

	public static <T extends Comparable<T>> void inOrder(BinaryTree<T>.Node tree) {
		if (tree == null)
			return;
		inOrder(tree.left);
		System.out.print(tree.treasure + " ");
		inOrder(tree.right);
	}

	public static <T extends Comparable<T>> void postOrder(BinaryTree<T>.Node tree) {
		Optional.ofNullable(tree.left).ifPresent(node -> postOrder(node));
		Optional.ofNullable(tree.right).ifPresent(node -> postOrder(node));
		System.out.print(tree.treasure + " ");
	}

	public static void main(String[] args) {
		BinaryTree<Integer> intTree = new BinaryTree<>();
		IntStream.range(0, 20).forEach(i -> intTree.add((int) (Math.random() * 100)));
		System.out.println("pre-order: ");
		preOrder(intTree.root);
		System.out.println("\n");
		System.out.println("in-order: ");
		inOrder(intTree.root);
		System.out.println("\n");
		System.out.println("post-order");
		postOrder(intTree.root);
	}
}
