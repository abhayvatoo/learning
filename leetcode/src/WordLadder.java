import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordLadder {

	public static void main(String[] args) {

		String beginWord = "hit";
		String endWord = "cog";
		List<String> wordList = new ArrayList<>(Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"));

		System.out.println(ladderLength(beginWord, endWord, wordList));

	}

	static int ladderLength(String beginWord, String endWord, List<String> wordList) {

		// Since all words are of same length.
		int L = beginWord.length();

		// Dictionary to hold combination of words that can be formed,
		// from any given word. By changing one letter at a time.
		Map<String, List<String>> allComboDict = new HashMap<>();

		wordList.forEach(word -> {
			for (int i = 0; i < L; i++) {
				// Key is the generic word
				// Value is a list of words which have the same intermediate generic word.
				String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);
				List<String> transformations = allComboDict.getOrDefault(newWord, new ArrayList<>());
				transformations.add(word);
				allComboDict.put(newWord, transformations);
			}
		});

		
		

		return 0;
	}

}
