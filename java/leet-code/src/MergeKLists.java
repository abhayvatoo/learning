
public class MergeKLists {

	public class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	public ListNode mergeKLists(ListNode[] lists) {
		int length = lists.length;
		if (lists == null || length == 0) {
			return null;
		} else if (length == 1) {
			return lists[0];
		} else if (length == 2) {
			return mergeTwoLists(lists[0], lists[1]);
		} else {
			ListNode retVal = mergeTwoLists(lists[0], lists[1]);
			for (int i = 2; i < length; i++) {
				retVal = mergeTwoLists(retVal, lists[i]);
			}
			return retVal;
		}
	}

	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		// maintain an unchanging reference to node ahead of the return node.
		ListNode prehead = new ListNode(-1);

		ListNode prev = prehead;
		while (l1 != null && l2 != null) {
			if (l1.val <= l2.val) {
				prev.next = l1;
				l1 = l1.next;
			} else {
				prev.next = l2;
				l2 = l2.next;
			}
			prev = prev.next;
		}

		// exactly one of l1 and l2 can be non-null at this point, so connect
		// the non-null list to the end of the merged list.
		prev.next = l1 == null ? l2 : l1;

		return prehead.next;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
