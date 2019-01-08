package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class BJSModel {
    private final static int MIN = 1, MAX = 9999;

    private String timeStamp;

    public BJSModel() {
        timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
    }

    public void t1(List<List<Transaction>> transactions) {
        final List<List<Object>> tests = new ArrayList<>();

        tests.add(Arrays.asList("Size", "Java 7 for", "Java 7 forEach", "Java 8 DoubleStream",
                "Java 8 DoubleStream parallel", "Java 8 Stream<Double>",
                "Java 8 Stream<Double> parallel"));

        transactions.forEach(t -> tests.add(Arrays.asList(
                    t.size(), BJSUtils.testBox(BJSUtils.t1_7_1(t)).getKey(),
                    BJSUtils.testBox(BJSUtils.t1_7_2(t)).getKey(),
                    BJSUtils.testBox(BJSUtils.t1_8_1_1(t)).getKey(),
                    BJSUtils.testBox(BJSUtils.t1_8_1_2(t)).getKey(),
                    BJSUtils.testBox(BJSUtils.t1_8_2_1(t)).getKey(),
                    BJSUtils.testBox(BJSUtils.t1_8_2_2(t)).getKey())
            )
        );


        BJSUtils.write("t1_" + timeStamp + ".csv", tests);
    }

    public void t3(List<int[]> arrays) {
        final List<List<Object>> tests = new ArrayList<>();

        tests.add(Arrays.asList("Size", "IntStream", "int[] array", "List<Integer>"));

        arrays.forEach(t -> tests.add(Arrays.asList(
                t.length, BJSUtils.testBox(BJSUtils.t3_IntStream(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t3(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t3(this.toList(t))).getKey()
        )));

        BJSUtils.write("t3_" + timeStamp + ".csv", tests);
    }

    public void t4(List<List<Transaction>> transactions) {
        final List<List<Object>> tests = new ArrayList<>();
        final List<double[]> values = new ArrayList<>();

        tests.add(Arrays.asList("Size", "BiFunction Sequential", "BiFunction Parallel", "Lambda Sequential",
                "Lambda Parallel"));

        transactions.forEach(t -> values.add(BJSUtils.t1_7_2(t).get()));

        values.forEach(t -> tests.add(Arrays.asList(
                t.length, BJSUtils.testBox(BJSUtils.t4_8_1_1(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t4_8_1_2(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t4_8_2_1(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t4_8_2_2(t)).getKey()
        )));

        BJSUtils.write("t4_" + timeStamp + ".csv", tests);
    }

    public void t5(List<List<Transaction>> transactions) {
        final List<List<Object>> tests = new ArrayList<>();

        tests.add(Arrays.asList("Size", "TreeSet", "List"));

        transactions.forEach(t -> tests.add(Arrays.asList(
                t.size(), BJSUtils.testBox(0,BJSUtils.t5_1(t, this.compareTransactionsByDate)).getKey(),
                BJSUtils.testBox(0,BJSUtils.t5_2(t, this.compareTransactionsByDate)).getKey())
        ));

        BJSUtils.write("t5_" + timeStamp + ".csv", tests);
    }

    public int[] generateArrayInt(final int size) {
        return generateArrayInt(MIN, MAX, size);
    }

    public int[] generateArrayInt(final int min, final int max, final int size) {
        return new Random().ints(size, min, max + 1).toArray();
    }

    private List<Integer> toList(final int[] array) {
        return Arrays.stream(array).boxed().collect(Collectors.toList());
    }

    public Comparator<Transaction> compareTransactionsByDate = Comparator.comparing(Transaction::getDate);
}

