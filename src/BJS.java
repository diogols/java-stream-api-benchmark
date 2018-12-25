import model.BJSUtils;
import model.Transaction;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

public class BJS {
    public static void main(String[] args) {
        List<Transaction> list = BJSUtils.load("transCaixa6M.txt", BJSUtils.toTransaction);

        // SimpleEntry<Double, Map<Month, Map<Integer, Map<Integer, List<Transaction>>>>> t6_8 = BJSUtils.testBox(BJSUtils.t6_8(list));
        // SimpleEntry<Double, Map<Month, Map<Integer, Map<Integer, List<Transaction>>>>> t6_7 = BJSUtils.testBox(BJSUtils.t6_7(list));

        // System.out.println(t6_8.getKey());
        // System.out.println(t6_7.getKey());

        // SimpleEntry<Double, Map<DayOfWeek, Map<Integer, List<Transaction>>>> t6_8 = BJSUtils.testBox(BJSUtils.t6_8_2(list));
        // SimpleEntry<Double, Map<DayOfWeek, Map<Integer, List<Transaction>>>> t6_7 = BJSUtils.testBox(BJSUtils.t6_7_2(list));

        // System.out.println(t6_8.getKey());
        // System.out.println(t6_7.getKey());
    }
}