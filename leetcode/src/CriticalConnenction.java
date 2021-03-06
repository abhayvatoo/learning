import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CriticalConnenction {
	public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {

		List<List<Integer>> result = new ArrayList<>();
		if (connections.size() == 0)
			return result;
		List<List<Integer>> graph = constructGraph(n, connections); // Create graph as adjacency list

		int[] discovery = new int[n]; // To keep track of when the node is discovered
		int[] lowLink = new int[n]; // Keeps track of lowest node (based on identified)

		Arrays.fill(discovery, -1);
		Integer discoveryTime = 0; // To avoid the global variable

		for (int i = 0; i < n; i++)
			if (discovery[i] == -1)
				dfs(graph, i, i, lowLink, discovery, result, discoveryTime);

		return result;
	}

	private void dfs(List<List<Integer>> graph, int from, int parent, int[] lowLink, int[] discovery,
			List<List<Integer>> result, Integer discoveryTime) {
		discovery[from] = lowLink[from] = ++discoveryTime; // discovery

		for (int to : graph.get(from)) {
			if (to == parent)
				continue; // due to undirected graph

			if (discovery[to] == -1) { // if the to node is undiscovered
				dfs(graph, to, from, lowLink, discovery, result, discoveryTime);
				lowLink[from] = Math.min(lowLink[from], lowLink[to]); // crucial - on backtracking, set the low link
																		// value

				if (lowLink[to] > discovery[from])
					result.add(Arrays.asList(from, to));
			} else
				lowLink[from] = Math.min(lowLink[from], discovery[to]); // Low link is influenced by the discovery time
		}
	}

	private List<List<Integer>> constructGraph(int n, List<List<Integer>> connections) {

		List<List<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++)
			graph.add(new ArrayList<>());

		for (List<Integer> edge : connections) {
			graph.get(edge.get(0)).add(edge.get(1));
			graph.get(edge.get(1)).add(edge.get(0));
		}
		return graph;
	}

}
