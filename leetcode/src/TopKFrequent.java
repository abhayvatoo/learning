import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class TopKFrequent {

	static class NumStat {
		int num;
		int count;

		public NumStat(int num, int count) {
			this.num = num;
			this.count = count;
		}
		
	}

	static class MyComparator implements Comparator<NumStat> {
		public int compare(NumStat s1, NumStat s2) {
			return s2.count - s1.count;
		}
	}

	public static List<Integer> topKFrequent(int[] nums, int k) {
		List<Integer> ans = new ArrayList<>();

		Map<Integer, NumStat> stats = new HashMap<>();
		for (Integer num : nums) {
			NumStat stat;
			if (stats.containsKey(num)) {
				stat = stats.get(num);
				stat.count += 1;
				stats.replace(num, stat);
			} else {
				stat = new NumStat(num, 1);
				stats.put(num, stat);
			}
		}

		Queue<NumStat> pQueue = new PriorityQueue<>(new MyComparator());

		for (NumStat stat : stats.values()) {
			pQueue.add(stat);
		}

		for (int i = 0; i < k; i++) {
			ans.add(pQueue.remove().num);
		}

		return ans;
	}

	public static void main(String[] args) {
		int[] nums = { 1, 1, 1, 2, 2, 3 };
		int k = 2;
		topKFrequent(nums, k);

	}
}
