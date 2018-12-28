import model.BJSUtils;
import model.Transaction;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class BJS {
    public static void main(String[] args) {
        List<Transaction> transactions = BJSUtils.load("transCaixa1M.txt", BJSUtils.parseTransaction);

        // DOUBLE PRECISION BENCHMARK
        // https://rmannibucau.metawerx.net/post/java-stream-float-widening
        /*
        double sumPrimitive = 0;
        for (Transaction t : transactions) {
            sumPrimitive += t.getValue();
        }

        Double sumDouble = new Double(0);
        for (Transaction t : transactions) {
            sumDouble += new Double(t.getValue());
        }

        double sumMapReduce = transactions.stream().map(Transaction::getValue).reduce(0.0, Double::sum);

        double sumMapToDoubleSum = transactions.stream().mapToDouble(Transaction::getValue).sum();

        double sumSummingDouble = transactions.stream().collect(Collectors.summingDouble(Transaction::getValue));

        System.out.printf("double +        = %f\n", sumPrimitive);
        System.out.printf("Double +        = %f\n", sumDouble);
        System.out.printf("map reduce      = %f\n", sumMapReduce);
        System.out.printf("mapToDouble sum = %f\n", sumMapToDoubleSum);
        System.out.printf("summingDouble   = %f\n", sumSummingDouble);
        */

        //T3
        SimpleEntry<Double, IntStream> intStream = BJSUtils.testBox(0, BJSUtils.t3(BJSUtils.getIntStream(8000000)));
        SimpleEntry<Double, int[]> intArray = BJSUtils.testBox(0, BJSUtils.t3(BJSUtils.getIntArray(8000000)));
        SimpleEntry<Double, List<Integer>> listInteger = BJSUtils.testBox(0, BJSUtils.t3(BJSUtils.getListInteger(8000000)));

        System.out.println(intStream.getKey() + " " + isDistinct(intStream.getValue().boxed().collect(toList())));
        System.out.println(intArray.getKey() + " " + isDistinct( Arrays.stream(intArray.getValue()).boxed().collect(toList())));
        System.out.println(listInteger.getKey() + " " + isDistinct(listInteger.getValue()));

    }

    private static boolean isDistinct(List<Integer> integers) {
        return integers.stream().distinct().collect(toList()).size() == integers.size();
    }
}
