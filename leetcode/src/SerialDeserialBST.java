import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

public class SerialDeserialBST {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	private void serialize(TreeNode root, StringBuilder builder) {
		if (root == null) {
			return;
		}

		builder.append(root.val).append(',');
		if (root.left != null) {
			serialize(root.left, builder);
		} else {
			builder.append('*').append(',');
		}

		if (root.right != null) {
			serialize(root.right, builder);
		} else {
			builder.append('*').append(',');
		}
	}

	// Encodes a tree to a single string.
	public String serialize(TreeNode root) {
		StringBuilder stringBuilder = new StringBuilder();
		serialize(root, stringBuilder);
		return stringBuilder.toString();
	}

	private void deserialize(Deque<String> queue, TreeNode p) {
		while (!queue.isEmpty()) {
			String str = queue.remove();
			if (!str.equals("*")) {
				TreeNode node = new TreeNode(Integer.parseInt(str));
				deserialize(queue, node);

				p.left = node;
			} else {
				p.left = null;
			}

			str = queue.remove();
			if (!str.equals("*")) {
				TreeNode node = new TreeNode(Integer.parseInt(str));
				deserialize(queue, node);

				p.right = node;
			} else {
				p.right = null;
			}

			break; // break from this subtree
		}
	}

	// Decodes your encoded data to tree.
	public TreeNode deserialize(String data) {
		if (data == null || data.isEmpty()) return null;

		String[] strs = data.split(",");
		LinkedList<String> queue = new LinkedList<>();
		Collections.addAll(queue, strs);

		String s = queue.remove();
		TreeNode head = new TreeNode(Integer.parseInt(s));
		deserialize(queue, head);

		return head;
	}
}