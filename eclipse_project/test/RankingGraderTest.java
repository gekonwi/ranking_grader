import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class RankingGraderTest {

	private ArrayList<Solution> solutions;
	private SolutionGrades grades;

	private static double DOUBLE_TOL = 0.001;

	@Before
	public void before() {
		solutions = new ArrayList<>(7);
		grades = new SolutionGrades();
		for (int i = 0; i < 7; i++) {
			solutions.add(new Solution());
			grades.setGrade(solutions.get(i), 100 - i * 10);
		}
	}

	@Test
	public void testPerfectRanking() {
		Ranking r = new Ranking();
		r.addAll(solutions);
		assertEquals(100.0, RankingGrader.grade(grades, r), DOUBLE_TOL);
	}

	@Test
	public void testSimilarlyGradedSwitched1() {
		Ranking r = createRanking(new int[] { 1, 0, 3, 2, 5, 4, 6 });
		assertEquals(100.0, RankingGrader.grade(grades, r), DOUBLE_TOL);
	}

	@Test
	public void testSimilarlyGradedSwitched2() {
		Ranking r = createRanking(new int[] { 0, 2, 1, 3, 4, 6, 5 });
		assertEquals(100.0, RankingGrader.grade(grades, r), DOUBLE_TOL);
	}

	@Test
	public void testOppositeOrder() {
		Ranking r = createRanking(new int[] { 6, 5, 4, 3, 2, 1, 0 });
		assertTrue(RankingGrader.grade(grades, r) < 15.0);
	}

	@Test
	public void testPunishmentProportionalToGradeDistance() {
		Ranking betterRank = createRanking(new int[] { 2, 1, 0, 3, 4, 5, 6 });
		Ranking worseRank = createRanking(new int[] { 3, 1, 2, 0, 4, 5, 6 });

		final double betterGrade = RankingGrader.grade(grades, betterRank);
		final double worseGrade = RankingGrader.grade(grades, worseRank);
		assertTrue(betterGrade > worseGrade);
	}

	private Ranking createRanking(int[] order) {
		Ranking r = new Ranking();
		for (int i : order)
			r.add(solutions.get(i));
		return r;
	}
}
