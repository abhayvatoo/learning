import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CriticalConnenction {
	public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
		List<List<Integer>> critical = new ArrayList<List<Integer>>();

		Map<Integer, Integer> serverCount = new HashMap<Integer, Integer>();

		for (List<Integer> connection : connections) {
			for (Integer server : connection) {
				if (serverCount.containsKey(server)) {
					int oldCount = serverCount.get(server);
					int newCount = oldCount + 1;
					serverCount.replace(server, newCount);
				} else {
					serverCount.put(server, 1);
				}
			}
		}

		for (List<Integer> connection : connections) {
			if (serverCount.get(connection.get(0)) == 1 || serverCount.get(connection.get(1)) == 1
					|| serverCount.get(connection.get(0)) % 2 != 0 && serverCount.get(connection.get(1)) % 2 != 0) {
				critical.add(connection);
			}
		}

		return critical;

	}
}
