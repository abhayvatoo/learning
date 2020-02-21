
// IMPORT LIBRARY PACKAGES NEEDED BY YOUR PROGRAM
// SOME CLASSES WITHIN A PACKAGE MAY BE RESTRICTED
// DEFINE ANY CLASS AND METHOD NEEDED
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// CLASS BEGINS, THIS CLASS IS REQUIRED
class AmamzonTest {
	// METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
	public ArrayList<String> topNCompetitors(int numCompetitors, int topNCompetitors, List<String> competitors,
			int numReviews, List<String> reviews) {
		ArrayList<String> res = new ArrayList<>();
		Set<String> setCompetitors = new HashSet<>();
		Map<String, Integer> mapWords = new HashMap<>();

		for (int i = 0; i < numCompetitors; i++)
			setCompetitors.add(competitors.get(i));

		for (int i = 0; i < numReviews; i++) {
			String q = reviews.get(i);

			q = q.replaceAll("[\\!?,;.]", "").toLowerCase();

			String[] words = q.split(" ");

			for (int w = 0; w < words.length; w++) {
				String word = words[w];
				if (setCompetitors.contains(word)) {
					if (mapWords.containsKey(word)) {
						int cC = mapWords.get(word);
						cC++;
						mapWords.replace(word, cC);
					} else {
						mapWords.put(word, 1);
					}
				}
			}
		}

		List<String> comp = new ArrayList<String>(mapWords.keySet());
		Collections.sort(comp, (w1, w2) -> mapWords.get(w1).equals(mapWords.get(w2)) ? w1.compareTo(w2)
				: mapWords.get(w2) - mapWords.get(w1));

		if (topNCompetitors > comp.size())
			for (int i = 0; i < numCompetitors && !comp.isEmpty(); i++)
				res.add(comp.get(i));
		else
			res.addAll(comp.subList(0, topNCompetitors));

		return res;
	}

	// METHOD SIGNATURE ENDS
}