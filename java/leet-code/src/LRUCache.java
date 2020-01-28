import java.util.HashMap;
import java.util.Map;

public class LRUCache {

	public class Value {
		int value;
		int key;
		Value next;
		Value previous;

		public Value(int key, int value) {
			this.key = key;
			this.value = value;
		}
	}

	int maxCapacity;
	int currCapacity;
	Map<Integer, Value> cache = new HashMap<>();
	Value dummyHead = new Value(-1, -1);
	Value dummyTail = new Value(-1, -1);

	public LRUCache(int maxCapacity) {
		this.maxCapacity = maxCapacity;
		dummyTail.previous = dummyHead;
		dummyHead.next = dummyTail;
	}

	public int get(int key) {

		if (cache.containsKey(key)) {
			Value retVal = cache.get(key);

			// remove
			Value retValNext = retVal.next;
			Value retValPrevious = retVal.previous;

			retValPrevious.next = retValNext;
			retValNext.previous = retValPrevious;

			currCapacity--;
			cache.remove(key);

			// add to front
			put(key, retVal.value);

			return retVal.value;
		}
		return -1;
	}

	public void put(int key, int value) {

		// key already exists
		if (cache.containsKey(key)) {
			Value update = cache.get(key);
			update.value = value;

			// remove
			Value newNext = update.next;
			Value newPre = update.previous;
			newPre.next = newNext;
			newNext.previous = newPre;

			// move to front
			Value oldFirst = dummyHead.next;
			dummyHead.next = update;
			update.previous = dummyHead;
			update.next = oldFirst;
			oldFirst.previous = update;

			cache.replace(key, update);
			return;
		}

		Value newVal = new Value(key, value);
		if (currCapacity < maxCapacity) {
			// always add to front
			Value oldFirst = dummyHead.next;

			// connection with dummyHead
			dummyHead.next = newVal;
			newVal.previous = dummyHead;

			// connection with oldFirst
			newVal.next = oldFirst;
			oldFirst.previous = newVal;

			cache.put(key, newVal);
			currCapacity++;
		} else {
			// evict from back
			Value old = dummyTail.previous;

			old.previous.next = dummyTail;
			dummyTail.previous = old.previous;
			cache.remove(old.key);

			// add to front
			Value oldVal = dummyHead.next;

			dummyHead.next = newVal;

			newVal.next = oldVal;
			newVal.previous = dummyHead;

			oldVal.previous = newVal;

			cache.put(key, newVal);

		}
	}

	public static void main(String args[]) {
		LRUCache lru = new LRUCache(2);

		lru.put(2, 1);
		printCache(lru);
		lru.put(1, 1);
		printCache(lru);
		lru.put(2, 3);
		printCache(lru);
		lru.put(4, 1);
		printCache(lru);
		lru.get(1);
		printCache(lru);
		lru.get(2);
		printCache(lru);
	}

	private static void printCache(LRUCache lru) {
		Value firstVal = lru.dummyHead.next;

		while (firstVal != lru.dummyTail) {
			System.out.print("[" + firstVal.key + " : " + firstVal.value + "]");
			firstVal = firstVal.next;
		}
		System.out.println();

	}

}
