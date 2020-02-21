import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class PriortyQComp {

	public static void main(String[] args) {

		// By default PQ is MIN HEAP!!
//		Queue<Integer> pq = new PriorityQueue<>();
//		pq.add(100);
//		pq.add(50);
//		pq.add(75);
//		pq.add(0);
//		pq.add(25);
//		while (!pq.isEmpty()) {
//			System.out.println(pq.remove());
//		}

		// Custom Priority Queue with custom comparator

		Queue<Integer> pqCustom = new PriorityQueue<>(new MyComp());
		pqCustom.add(100);
		pqCustom.add(50);
		pqCustom.add(75);
		pqCustom.add(0);
		pqCustom.add(25);
		
		
		while (!pqCustom.isEmpty()) {
			System.out.println(pqCustom.remove());
		}
	}

	static class MyComp implements Comparator<Integer> {

		@Override
		public int compare(Integer arg0, Integer arg1) {
			if (arg0 < arg1) {
				System.out.println("arg0 distance: " + arg0 + " is less than arg1 distance: " + arg1);
				System.out.println("returning negative...");
				return -1;
			} else if (arg0 > arg1) {
				System.out.println("arg0 distance: " + arg0 + " is more than arg1 distance: " + arg1);
				System.out.println("returning positive...");
				return 1;
			}
			return 0;
		}

		// This is a MAX HEAP!!
		// Descending Order

//		@Override
//		public int compare(Integer arg0, Integer arg1) {
//			return arg1 - arg0;
//		}

		// this is the default implementation of comparator in PQ
		// which is a MIN HEAP!!
		// Ascending Order

//		@Override
//		public int compare(Integer arg0, Integer arg1) {
//			return arg0 - arg1;
//		}

	}
}
