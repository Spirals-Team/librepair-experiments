package tillerino.tillerinobot.diff;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Collections;

import org.junit.Test;
import org.tillerino.osuApiModel.OsuApiBeatmap;

public class KoohiiTest {
	@Test
	public void testSimple() throws Exception {
		try (InputStream is = ClassLoader.getSystemResourceAsStream(
				"Fujijo Seitokai Shikkou-bu - Best FriendS -TV Size- (Flask) [Fycho's Insane].osu")) {
			DifficultyProperties diff = new Koohii().calculate(is, Collections.emptyList());
			assertEquals(2.0308, diff.getSpeed(), 1E-4);
			assertEquals(2.5894, diff.getAim(), 1E-4);
			assertEquals(313, diff.getAllObjectsCount());
			assertEquals(229, diff.getCircleCount());
			assertEquals(404, diff.getMaxCombo());
		}
	}

	@Test
	public void testIssue137() throws Exception {
		try (InputStream is = ClassLoader.getSystemResourceAsStream(
				"Ayase Rie - Yuima-ruWorld TVver. (Fycho) [Extra].osu")) {
			DifficultyProperties diff = new Koohii().calculate(is, Collections.emptyList());
			OsuApiBeatmap beatmap = new OsuApiBeatmap();
			beatmap.setOverallDifficulty(8.7);
			beatmap.setApproachRate(9);
			beatmap.setMaxCombo(239);
			CBeatmapImpl impl = new CBeatmapImpl(beatmap, diff.getSpeed(), diff.getAim(), diff.getCircleCount(),
					diff.getAllObjectsCount(), true, false, true);
			CStandardScore score = new CStandardScore(239, 165, 13, 0, 0, 0);
			assertEquals(138.287, score.getPP(impl), 0d);
		}
	}
}
