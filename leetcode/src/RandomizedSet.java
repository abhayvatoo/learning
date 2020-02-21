import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomizedSet {

	Map<Integer, Integer> dataMap;
	List<Integer> dataList;

	public RandomizedSet() {
		dataMap = new HashMap<>();
		dataList = new ArrayList<>();
	}

	/**
	 * Inserts a value to the set. Returns true if the set did not already contain
	 * the specified element.
	 */
	public boolean insert(int val) {
		if (dataMap.containsKey(val))
			return false;

		dataList.add(dataList.size(), val);
		dataMap.put(val, dataList.size());
		return true;
	}

	/**
	 * Removes a value from the set. Returns true if the set contained the specified
	 * element.
	 */
	public boolean remove(int val) {
		if (dataMap.containsKey(val)) {
			// move the last element to the place idx of the element to delete
			int lastElement = dataList.get(dataList.size() - 1);
			int idx = dataMap.get(val);
			dataList.set(idx, lastElement);
			dataMap.put(lastElement, idx);
			// delete the last element
			dataList.remove(dataList.size() - 1);
			dataMap.remove(val);
			return true;
		}
		return false;
	}

	/** Get a random element from the set. */
	public int getRandom() {
		Random rand = new Random();
		int randomIndex = rand.nextInt(dataList.size());
		return dataList.get(randomIndex);
	}

	public static void main(String[] args) {
//		RandomizedSet obj = new RandomizedSet();
//		boolean param_1 = obj.insert(1);
//		boolean param_2 = obj.remove(1);
//		int param_3 = obj.getRandom();

	}

}
