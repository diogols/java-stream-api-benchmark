import model.BJSUtils;
import model.Transaction;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

public class BJS {
    public static void main(String[] args) {
        List<Transaction> transactions = BJSUtils.load("transCaixa1M.txt", BJSUtils.toTransaction);

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

        SimpleEntry<Double, List<Double>> t10_7 = BJSUtils.testBox(0, BJSUtils.t10_7(transactions));
        SimpleEntry<Double, List<Double>> t10_8 = BJSUtils.testBox(0, BJSUtils.t10_8(transactions));

        System.out.println(t10_7.getKey() + " " + t10_7.getValue());
        System.out.println(t10_8.getKey() + " " + t10_8.getValue());

    }
}
