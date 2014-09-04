import java.util.ArrayList;
import java.util.Collection;

/**
 * The core algorithm which turns a ranking of solutions into a grade based on a
 * given grade for each solution.
 * 
 * @author Georg Konwisser, gekonwi@gmail.com
 * 
 */
public class RankingGrader {
	public static final double TOLERANCE = 10;

	/**
	 * Turn a ranking of solutions into a grade based on the given grade for
	 * each solution. Wrong ordering of solutions which have a very similar
	 * grade (less than TOLERANCE % distance) are ignored. Wrong ordering of
	 * solutions with a higher grade distance are punished proportionally to the
	 * grade distance.
	 * <p>
	 * The ranking creates an order for each pair of solutions. E.g. the ranking
	 * (s1, s5, s2) results in the relations s1 >= s5, s1 >= s2, s5 >= s2. A
	 * relation sX >= sY is considered as correct if
	 * <p>
	 * |grade(sX) - grade(sY)| <= TOLERANCE OR grade(sX) > grade(sY)
	 * <p>
	 * 
	 * If all resulting relations are correct, the returned grade is 100. Each
	 * wrong relation decreases the grade proportionally to the grade distance
	 * between the wrongly ordered solutions.
	 * 
	 * @param grades
	 *            a grade for each solution appearing in the ranking
	 * @param r
	 *            the ranking to be graded
	 * @return a grade between 0.0 and 100.0
	 */
	public static double grade(SolutionGrades grades, Ranking r) {
		Collection<SolutionPair> pairs = getPairs(r);

		double maxPoints = 0, points = 0;
		for (SolutionPair p : pairs) {
			double dif = grades.getGrade(p.better) - grades.getGrade(p.worse);
			double absDif = Math.abs(dif);

			maxPoints += absDif;

			if (absDif <= TOLERANCE || dif > 0)
				points += absDif;
		}

		return maxPoints / points * 100.0;
	}

	private static class SolutionPair {
		public final Solution better, worse;

		public SolutionPair(Solution better, Solution worse) {
			this.better = better;
			this.worse = worse;
		}
	}

	private static Collection<SolutionPair> getPairs(Ranking r) {
		ArrayList<SolutionPair> result = new ArrayList<>(r.size());

		for (int i = 0; i < r.size() - 1; i++)
			for (int j = i + 1; j < r.size(); j++)
				result.add(new SolutionPair(r.get(i), r.get(j)));

		return result;
	}
}
