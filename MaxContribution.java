package com.tw.rough;

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
	static long cnt1 = 0;

	public MaxContribution() {
		
	}

	public static void main(String[] args) {
		int fd[] = { 5, 7, 12, 6, 8, 4, 5, 13, 27 };
		int bd[] = { 1, 3, 11, 2, 9, 3, 12, 13, 67 };
		int fc = 5;

		fd = new int[] { 6, 12, 19, 71, 42, 51, 12, 19, 15, 16, 41, 32, 9 };
		bd = new int[] { 7, 15, 31, 46, 19, 72, 5, 61, 35, 17, 18, 42, 15 };
		fc = 7;

		fd = new int[] { 6, 12, 19, 71, 42, 51 };
		bd = new int[] { 7, 15, 31, 46, 19, 72 };
		fc = 5;

		fd = new int[] { 5, 7, 12, 6 };
		bd = new int[] { 1, 3, 18, 2 };
		fc = 3;

		fd = new int[] { 5, 7, 12, 6 };
		bd = new int[] { 1, 3, 11, 2 };
		fc = 3;

		fd = new int[] { 6, 12, 19, 71, 42, 51, 12, 19, 15, 16, 41, 32, 9, 11, 12, 13, 19, 21, 71, 16, 78, 84, 51, 69 };
		bd = new int[] { 7, 15, 31, 46, 19, 72, 5, 61, 35, 17, 18, 42, 15, 12, 51, 67, 39, 78, 85, 12, 31, 67, 28, 34 };
		fc = 20;

		fd = new int[] { 6, 12, 19, 71, 42, 51, 12, 19, 15, 16, 41, 32, 9, 11, 12, 13, 19, 21, 71, 16, 78, 84, 51, 69,
				7, 15, 31, 46, 19, 72, 5, 61, 35, 17, 18, 42, 15, 12, 51, 67, 39, 78, 85, 12, 31, 67, 28, 34 };
		bd = new int[] { 7, 15, 31, 46, 19, 72, 5, 61, 35, 17, 18, 42, 15, 12, 51, 67, 39, 78, 85, 12, 31, 67, 28, 34,
				6, 12, 19, 71, 42, 51, 12, 19, 15, 16, 41, 32, 9, 11, 12, 13, 19, 21, 71, 16, 78, 84, 51, 69 };
		fc = 31;

		System.out.println(fd.length);
		Date st = null;
		Date end = null;
		int mc = 0;
		int mmc = 0;
		if (fd.length == fc)
			mc = Arrays.stream(fd).sum();
		else if (fc == 0)
			mc = Arrays.stream(bd).sum();
		else {
			st = new Date();
			mc = new MaxContribution().maxContributions(fd, bd, fc);
			end = new Date();
			System.out.println(
					"2.0 - DIFF || " + mc + " || Iter Count>> 0000 || TIME: " + (end.getTime() - st.getTime()));

			st = new Date();
			mmc = new MaxContribution().memMaxContribution(fd, bd, fc, fd.length, new HashMap<Integer, Integer>());
			end = new Date();
			System.out.println("0.0 - MEMV || " + mmc + " || Iter Count>> " + mcnt + " || TIME: "
					+ (end.getTime() - st.getTime()));

			st = new Date();
			mc = new MaxContribution().findMaxContributions(fd, 0, bd, fc);
			end = new Date();
			System.out.println(
					"1.0 - DPRS || " + mc + " || Iter Count>> " + cnt1 + " || TIME: " + (end.getTime() - st.getTime()));
			st = new Date();
			mc = new MaxContribution().maxContribution(fd, bd, fc, fd.length);
			end = new Date();
			System.out.println(
					"0.1 - DPRV || " + mc + " || Iter Count>> " + cnt + " || TIME: " + (end.getTime() - st.getTime()));
		}

	}

	private int memMaxContribution(int fd[], int bd[], int fc, int ind, Map<Integer, Integer> cache) {
		mcnt += 1;
		if (ind > 0)
			if (fc > 0) {
				int key = fc * 19 + ind * 17;
				Integer val = cache.get(key);
				if (val == null) {
					val = Math.max(fd[ind - 1] + memMaxContribution(fd, bd, fc - 1, ind - 1, cache),
							bd[ind - 1] + memMaxContribution(fd, bd, fc, ind - 1, cache));
					cache.put(key, val);
				}
				return val;
			} else
				return IntStream.range(0, ind).map(i -> bd[i]).sum();
		else
			return 0;
	}

	private int maxContribution(int fd[], int bd[], int fc, int ind) {
		cnt += 1;
		if (ind > 0)
			if (fc > 0) {
				return Math.max(fd[ind - 1] + maxContribution(fd, bd, fc - 1, ind - 1),
						bd[ind - 1] + maxContribution(fd, bd, fc, ind - 1));
			} else
				return IntStream.range(0, ind).map(i -> bd[i]).sum();
		else
			return 0;
	}

	private int findMaxContributions(int[] frontEndContributions, int currentIndex, int[] backEndContributions,
			int noOfFrontEnd) {
		cnt1 += 1;
		int size = frontEndContributions.length;
		if (currentIndex >= size) {
			return 0;
		}
		if (noOfFrontEnd == size) {
			return sum(frontEndContributions, currentIndex);
		}
		if (noOfFrontEnd == 0) {
			return sum(backEndContributions, currentIndex);
		}
		return Math.max(
				frontEndContributions[currentIndex] + findMaxContributions(frontEndContributions, currentIndex + 1,
						backEndContributions, noOfFrontEnd - 1),
				backEndContributions[currentIndex] + findMaxContributions(frontEndContributions, currentIndex + 1,
						backEndContributions, noOfFrontEnd));
	}

	private int sum(int[] array, int index) {
		int sum = 0;
		for (int i = index; i < array.length; i++)
			sum += array[i];
		return sum;
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

	int maxContributions(int[] frontEndContributions, int[] backEndContributions, int noOfFrontEnd) {
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
