import model.BJSUtils;
import model.Transaction;

import java.time.Month;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class BJS {
    public static void main(String[] args) {
        List<Transaction> transactions = BJSUtils.load("transCaixa6M.txt", BJSUtils.toTransaction);

        // SimpleEntry<Double, Map<Month, Map<Integer, Map<Integer, List<Transaction>>>>> t6_8 = BJSUtils.testBox(BJSUtils.t6_8(list));
        // SimpleEntry<Double, Map<Month, Map<Integer, Map<Integer, List<Transaction>>>>> t6_7 = BJSUtils.testBox(BJSUtils.t6_7(list));

        // System.out.println(t6_8.getKey());
        // System.out.println(t6_7.getKey());

        // SimpleEntry<Double, Map<DayOfWeek, Map<Integer, List<Transaction>>>> t6_8 = BJSUtils.testBox(BJSUtils.t6_8_2(list));
        // SimpleEntry<Double, Map<DayOfWeek, Map<Integer, List<Transaction>>>> t6_7 = BJSUtils.testBox(BJSUtils.t6_7_2(list));

        // System.out.println(t6_8.getKey());
        // System.out.println(t6_7.getKey());

//        System.out.printf("%f\n",BJSUtils.t7_8_1(list).get());
//        System.out.printf("%f\n",BJSUtils.t7_8_2(list).get());
//        System.out.printf("%f\n",BJSUtils.t7_8_3(list).get());
//        System.out.printf("%f\n",BJSUtils.t7_7(list).get());

//        SimpleEntry<Double, String> t8_7 = BJSUtils.testBox(0, BJSUtils.t8_7(list));
//        SimpleEntry<Double, String> t8_8 = BJSUtils.testBox(0, BJSUtils.t8_8(list));
//
//        System.out.println(t8_7.getKey());
//        System.out.println(t8_8.getKey());
//
//        System.out.println(t8_7.getValue());
//        System.out.println(t8_8.getValue());

        // T9
//        SimpleEntry<Double, Double> t9_7 = BJSUtils.testBox(0, BJSUtils.t9_7(transactions, 51));
//        SimpleEntry<Double, Double> t9_8 = BJSUtils.testBox(0, BJSUtils.t9_8(transactions, 51));
//
//        System.out.println(t9_7.getKey() + " " + t9_7.getValue());
//        System.out.println(t9_8.getKey() + " " + t9_8.getValue());

        // T10

        //<Double, List<Double>> t10_7 = BJSUtils.testBox(0, BJSUtils.t10_7(transactions));
        //SimpleEntry<Double, List<Double>> t10_8 = BJSUtils.testBox(0, BJSUtils.t10_8(transactions));

        // System.out.println(t10_7.getKey() + " " + t10_7.getValue());
        // System.out.println(t10_8.getKey() + " " + t10_8.getValue());
        /*
        for (int i = 0; i < 12; i++) {
            if (t10_7.getValue().get(i).doubleValue() != t10_8.getValue().get(i).doubleValue()) {
                System.out.println(t10_7.getValue().get(i));
                System.out.println(t10_8.getValue().get(i));
                System.out.println();

            }
        }
        */

        // T5

//        SimpleEntry<Double, Set<Transaction>> t5_7 = BJSUtils.testBox(0, BJSUtils.t5_7(transactions, BJSUtils.compareTransactionsById));
//        SimpleEntry<Double, List<Transaction>> t5_8 = BJSUtils.testBox(0, BJSUtils.t5_8(transactions, BJSUtils.compareTransactionsById));
//
//        System.out.println(t5_7.getKey());
//        t5_7.getValue().stream().limit(10).forEach(System.out::println);
//        System.out.println(t5_8.getKey());
//        t5_8.getValue().stream().limit(10).forEach(System.out::println);



        // T12

        SimpleEntry<Double, Map<String, Map<Month, List<Transaction>>>> t12_map = BJSUtils.testBox(0, BJSUtils.t12_Map_1(transactions));
        SimpleEntry<Double, ConcurrentMap<String, ConcurrentMap<Month, List<Transaction>>>> t12_concurrent = BJSUtils.testBox(0, BJSUtils.t12_ConcurrentMap_1(transactions));

        System.out.println(t12_map.getKey());
        System.out.println(t12_concurrent.getKey());

        SimpleEntry<Double, Map<String, Double>> t12_map_total = BJSUtils.testBox(0, BJSUtils.t12_Map_2(t12_map.getValue()));
        SimpleEntry<Double, Map<String, Double>> t12_concurrent_total = BJSUtils.testBox(0, BJSUtils.t12_ConcurrentMap_2(t12_concurrent.getValue()));

        System.out.println(t12_map_total.getKey());
        System.out.println(t12_concurrent_total.getKey());

        for (String s : t12_map_total.getValue().keySet()) {
            if (t12_map_total.getValue().get(s).doubleValue() != t12_concurrent_total.getValue().get(s).doubleValue()) {
                System.out.println("FUCK");
            }
        }

        /*
        double sum7 = 0;
        for (Transaction t : transactions) {
            sum7 += t.getValue();
        }

        System.out.printf("%f\n", sum7);
        System.out.printf("%f\n", transactions.stream().map(Transaction::getValue).reduce(0.0, Double::sum));
        */

    }
}
