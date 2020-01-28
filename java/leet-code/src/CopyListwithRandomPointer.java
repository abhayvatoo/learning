import java.util.HashMap;

public class CopyListwithRandomPointer {

	// Definition for a Node.
	class Node {
		int val;
		Node next;
		Node random;

		public Node(int val) {
			this.val = val;
			this.next = null;
			this.random = null;
		}
	}

	public Node copyRandomList(Node head) {
		if (head == null) {
			return null;
		}

		HashMap<Node, Node> clonedNodes = new HashMap<>();

		return copyRandomNode(head, clonedNodes);
	}

	private Node copyRandomNode(Node node, HashMap<Node, Node> clonedNodes) {
		if (node == null) {
			return null;
		}

		if (clonedNodes.containsKey(node)) {
			return clonedNodes.get(node);
		}

		Node clonedNode = new Node(node.val);
		clonedNodes.put(node, clonedNode);

		clonedNode.next = copyRandomNode(node.next, clonedNodes);
		clonedNode.random = copyRandomNode(node.random, clonedNodes);

		return clonedNode;
	}

}
