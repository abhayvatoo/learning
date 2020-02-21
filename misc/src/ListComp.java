import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListComp {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(100);
		list.add(50);
		list.add(75);
		list.add(0);
		list.add(25);

		list.sort(new MyComp());

		System.out.println(list);

	}

	static class MyComp implements Comparator<Integer> {

//		 Ascending Order - DEFAULT
		@Override
		public int compare(Integer arg0, Integer arg1) {
			return arg0 - arg1;
		}

//		 Descending Order
//		@Override
//		public int compare(Integer arg0, Integer arg1) {
//			return arg1 - arg0;
//		}

	}

}
