package guru.bonacci.oogway.sannyas.service.filters;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * A swear filter, also known as a profanity filter or language filter is a
 * software subsystem which modifies text to remove words deemed offensive by
 * the administrator or community of an online forum. Swear filters are common
 * in custom-programmed chat rooms and online video games.
 */
@RefreshScope
@Component
public class ProfanityFilter implements Predicate<String> {

	@Value("${filter.profanity.file.name:}")
	private String fileName;

	private final Logger logger = getLogger(this.getClass());

	/**
	 * Borrowed code
	 * 
	 * @author thank you Lyen
	 */
	private TreeNode root = new TreeNode();

	private boolean isSuspicionFound;
	private boolean merdaFound;

	@PostConstruct
	public void setup() {
		if (StringUtils.isNotBlank(fileName))
			buildDictionaryTree(fileName);
	}

	@Override
	public boolean test(String input) {
		init();
		// for each character in a bad word
		for (int i = 0; i < input.length(); i++) {
			searchAlongTree(input, i, root);
		}
		if (merdaFound) {
			logger.info("Blocking indecent quote: " + input);
		}
		return !merdaFound;
	}

	private void init() {
		isSuspicionFound = false;
		merdaFound = false;
	}

	private void searchAlongTree(String pUserInput, int characterIndex, TreeNode node) {
		if (characterIndex < pUserInput.length()) {
			// get the corresponding letter
			Character letter = pUserInput.charAt(characterIndex);
			if (node.containsChild(letter)) {
				// find a word whose first letter is equal to one of the bad
				// words' first letter
				if (isSuspicionFound == false) {
					isSuspicionFound = true;
				}
				// if this is the final letter of a bad word
				if (node.getChildByLetter(letter).isEnd()) {
					merdaFound = true;
				}
				node = node.getChildByLetter(letter);
				searchAlongTree(pUserInput, characterIndex + 1, node);
			} else {
				// initialize some parameters
				isSuspicionFound = false;
			}
		}
	}

	private void buildDictionaryTree(String fileName) {
		String line;
		BufferedReader in = null;
		try {
			Resource resource = new ClassPathResource(fileName);
			in = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			while ((line = in.readLine()) != null) {
				// for each bad word
				logger.info("Adding to profanity filter: '" + line + "'");
				addToTree(line, 0, root);
			}
		} catch (FileNotFoundException e) { // FileReader
			e.printStackTrace();
		} catch (IOException e) { // readLine
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void addToTree(String badWordLine, int characterIndex, TreeNode node) {
		if (characterIndex < badWordLine.length()) {
			Character c = badWordLine.charAt(characterIndex);
			if (!node.containsChild(c)) {
				node.addChild(c);
			}
			node = node.getChildByLetter(c);
			// check if this is the last letter
			if (characterIndex == (badWordLine.length() - 1)) {
				// mark this letter as the end of a bad word
				node.setEnd(true);
			} else {
				// add next letter
				addToTree(badWordLine, characterIndex + 1, node);
			}
		}
	}

	/**
	 * Borrowed code
	 * 
	 * @author thank you Lyen
	 */
	class TreeNode {

		private HashMap<Character, TreeNode> node;

		// Indicate that this letter is the end of a profanity word
		private boolean isEnd;

		public TreeNode() {
			isEnd = false;
			node = new HashMap<>();
		}

		public TreeNode(Character letter) {
			this();
		}

		public boolean isEnd() {
			return isEnd;
		}

		public void setEnd(boolean isEnd) {
			this.isEnd = isEnd;
		}

		/**
		 * @param letter:
		 *            child's letter
		 */
		public void addChild(Character letter) {
			TreeNode childNode = new TreeNode(letter);
			node.put(letter, childNode);
		}

		public TreeNode getChildByLetter(Character letter) {
			// Returns the value to which the specified key is mapped, or null
			// if this map contains no mapping for the key.
			return node.get(letter);
		}

		public boolean containsChild(Character letter) {
			return node.containsKey(letter);
		}
	}
}
