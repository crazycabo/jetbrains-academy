import java.util.List;

class Counter {

    public static boolean checkTheSameNumberOfTimes(int elem, List<Integer> list1, List<Integer> list2) {
        return getCount(elem, list1) == getCount(elem, list2);
    }

    private static int getCount(int num, List<Integer> list) {
        int count = 0;

        for (int i : list) {
            if (i == num) {
                count += 1;
            }
        }

        return count;
    }
}