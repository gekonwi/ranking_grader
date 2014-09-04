import java.util.HashMap;
import java.util.Map;

/**
 * A list of solutions with a grade between 0.0 and 100.0 assigned to each.
 * 
 * @author Georg Konwisser, gekonwi@gmail.com
 * 
 */
public class SolutionGrades {
	private final Map<Solution, Double> grades = new HashMap<>();

	public double getGrade(Solution s) {
		if (!grades.containsKey(s))
			throw new IllegalArgumentException(
					"There is no grade stored for this solution.");

		return grades.get(s);
	}

	public void setGrade(Solution s, double g) {
		grades.put(s, g);
	}
}
