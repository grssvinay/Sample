import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class MaxContribution {

	static long mcnt = 0;
	static long cnt = 0;

	public MaxContribution() {

	}

	public static void main(String[] args) {
		int frontEndDev[] = { 5, 7, 12, 6, 8, 4, 5, 13, 27 };
		int backEndDev[] = { 1, 3, 11, 2, 9, 3, 12, 13, 67 };
		int fontEndDevsCount = 5;

		frontEndDev = new int[] { 6, 12, 19, 71, 42, 51, 12, 19, 15, 16, 41, 32, 9 };
		backEndDev = new int[] { 7, 15, 31, 46, 19, 72, 5, 61, 35, 17, 18, 42, 15 };
		fontEndDevsCount = 7;

		frontEndDev = new int[] { 6, 12, 19, 71, 42, 51 };
		backEndDev = new int[] { 7, 15, 31, 46, 19, 72 };
		fontEndDevsCount = 5;

		frontEndDev = new int[] { 5, 7, 12, 6 };
		backEndDev = new int[] { 1, 3, 18, 2 };
		fontEndDevsCount = 3;

		frontEndDev = new int[] { 5, 7, 12, 6 };
		backEndDev = new int[] { 1, 3, 11, 2 };
		fontEndDevsCount = 3;

		frontEndDev = new int[] { 6, 12, 19, 71, 42, 51, 12, 19, 15, 16, 41, 32, 9, 11, 12, 13, 19, 21, 71, 16, 78, 84,
				51, 69 };
		backEndDev = new int[] { 7, 15, 31, 46, 19, 72, 5, 61, 35, 17, 18, 42, 15, 12, 51, 67, 39, 78, 85, 12, 31, 67,
				28, 34 };
		fontEndDevsCount = 20;

		frontEndDev = new int[] { 6, 12, 19, 71, 42, 51, 12, 19, 15, 16, 41, 32, 9, 11, 12, 13, 19, 21, 71, 16, 78, 84,
				51, 69, 7, 15, 31, 46, 19, 72, 5, 61, 35, 17, 18, 42, 15, 12, 51, 67, 39, 78, 85, 12, 31, 67, 28, 34 };
		backEndDev = new int[] { 7, 15, 31, 46, 19, 72, 5, 61, 35, 17, 18, 42, 15, 12, 51, 67, 39, 78, 85, 12, 31, 67,
				28, 34, 6, 12, 19, 71, 42, 51, 12, 19, 15, 16, 41, 32, 9, 11, 12, 13, 19, 21, 71, 16, 78, 84, 51, 69 };
		fontEndDevsCount = 3;

		Date start = null;
		Date end = null;

		Map<String, Object> response = new HashMap<>();

		int maxContribution = 0;
		if (frontEndDev.length == fontEndDevsCount)
			maxContribution = Arrays.stream(frontEndDev).sum();
		else if (fontEndDevsCount == 0)
			maxContribution = Arrays.stream(backEndDev).sum();
		else {
			start = new Date();
			maxContribution = new MaxContribution().maxContributionsDiff(frontEndDev, backEndDev, fontEndDevsCount);
			end = new Date();
			Map<String, Long> diff = new HashMap<>();
			diff.put("maxContribution", (long) maxContribution);
			diff.put("time", end.getTime() - start.getTime());
			diff.put("iterationCount", 0L);

			start = new Date();
			maxContribution = new MaxContribution().memMaxContribution(frontEndDev, backEndDev, fontEndDevsCount,
					frontEndDev.length, new HashMap<Integer, Integer>());
			end = new Date();
			Map<String, Long> memoized = new HashMap<>();
			memoized.put("maxContribution", (long) maxContribution);
			memoized.put("time", end.getTime() - start.getTime());
			memoized.put("iterationCount", mcnt);

			start = new Date();
			maxContribution = new MaxContribution().maxContribution(frontEndDev, backEndDev, fontEndDevsCount,
					frontEndDev.length);
			end = new Date();
			Map<String, Long> recursive = new HashMap<>();
			recursive.put("maxContribution", (long) maxContribution);
			recursive.put("time", end.getTime() - start.getTime());
			recursive.put("iterationCount", cnt);

			response.put("DIFF", diff);
			response.put("MEMOIZED", memoized);
			response.put("RECURSIVE", recursive);
		}

		if (start == null) {
			Map<String, Long> resp = new HashMap<>();
			resp.put("maxContribution", (long) maxContribution);
			resp.put("time", 0L);
			resp.put("iterationCount", 0L);
			response.put("STRAIGHT", resp);
		}

		System.out.println("RESPONSE :: " + response);

	}

	private int memMaxContribution(int frontEndDev[], int backEndDev[], int frontEndDecsCount, int ind,
			Map<Integer, Integer> cache) {
		mcnt += 1;
		if (ind > 0)
			if (frontEndDecsCount > 0) {
				int key = frontEndDecsCount * 19 + ind * 17;
				Integer val = cache.get(key);
				if (val == null) {
					val = Math.max(
							frontEndDev[ind - 1] + memMaxContribution(frontEndDev, backEndDev, frontEndDecsCount - 1,
									ind - 1, cache),
							backEndDev[ind - 1]
									+ memMaxContribution(frontEndDev, backEndDev, frontEndDecsCount, ind - 1, cache));
					cache.put(key, val);
				}
				return val;
			} else
				return IntStream.range(0, ind).map(i -> backEndDev[i]).sum();
		else
			return 0;
	}

	private int maxContribution(int frontEndDev[], int backEndDev[], int fontEndDevsCount, int ind) {
		cnt += 1;
		if (ind > 0)
			if (fontEndDevsCount > 0) {
				return Math.max(
						frontEndDev[ind - 1] + maxContribution(frontEndDev, backEndDev, fontEndDevsCount - 1, ind - 1),
						backEndDev[ind - 1] + maxContribution(frontEndDev, backEndDev, fontEndDevsCount, ind - 1));
			} else
				return IntStream.range(0, ind).map(i -> backEndDev[i]).sum();
		else
			return 0;
	}

	private class Tuple {
		public int getFrontEndContributions() {
			return frontEndContributions;
		}

		public int getBackEndContributions() {
			return backEndContributions;
		}

		int frontEndContributions;
		int backEndContributions;

		Tuple(int fc, int bc) {
			this.frontEndContributions = fc;
			this.backEndContributions = bc;
		}
	}

	int maxContributionsDiff(int[] frontEndContributions, int[] backEndContributions, int noOfFrontEnd) {
		List<Tuple> developers = new ArrayList<>();
		for (int i = 0; i < frontEndContributions.length; i++)
			developers.add(new Tuple(frontEndContributions[i], backEndContributions[i]));
		developers.sort(Comparator.comparingInt(x -> x.getBackEndContributions() - x.getFrontEndContributions()));
		int maxContributions = 0;
		for (int i = 0; i < noOfFrontEnd; i++)
			maxContributions += developers.get(i).getFrontEndContributions();
		for (int i = noOfFrontEnd; i < developers.size(); i++)
			maxContributions += developers.get(i).getBackEndContributions();
		return maxContributions;
	}

}
