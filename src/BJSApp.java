import controller.BJSController;
import model.BJSModel;
import model.BJSUtils;
import model.Transaction;
import view.BJSView;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class BJSApp {

    private static void tests() {
        final BJSModel bjsModel = new BJSModel();
        final List<Transaction> transactions = BJSUtils.load("transCaixa1M.txt", BJSUtils.parseTransaction);
        final int[] array = bjsModel.generateArrayInt(1000000);

        System.out.println("Testing T1");

        final double[] t1_7_1 = BJSUtils.t1_7_1(transactions).get();
        final double[] t1_7_2 = BJSUtils.t1_7_2(transactions).get();
        final DoubleStream t1_8_1_1 = BJSUtils.t1_8_1_1(transactions).get();
        final DoubleStream t1_8_1_2 = BJSUtils.t1_8_1_2(transactions).get();
        final Stream<Double> t1_8_2_1 = BJSUtils.t1_8_2_1(transactions).get();
        final Stream<Double> t1_8_2_2 = BJSUtils.t1_8_2_2(transactions).get();

        System.out.println("Sizes");
        System.out.println(t1_7_1.length);
        System.out.println(t1_7_2.length);
        System.out.println(t1_8_1_1.count());
        System.out.println(t1_8_1_2.count());
        System.out.println(t1_8_2_1.count());
        System.out.println(t1_8_2_2.count());
        System.out.println("Sum");

        double sum1 = 0.0;
        double sum2 = 0.0;

        for (double d : t1_7_1) sum1 += d;
        for (double d : t1_7_2) sum2 += d;

        System.out.println(sum1);
        System.out.println(sum2);
        System.out.println(BJSUtils.t1_8_1_1(transactions).get().sum());
        System.out.println(BJSUtils.t1_8_1_2(transactions).get().sum());
        System.out.println(BJSUtils.t1_8_2_1(transactions).get().mapToDouble(d -> d).sum());
        System.out.println(BJSUtils.t1_8_2_2(transactions).get().mapToDouble(d -> d).sum());

        System.out.println("Testing T3");
        System.out.println("Sizes");
        System.out.println(BJSUtils.t3_IntStream(array).get().count());
        System.out.println(BJSUtils.t3(array).get().length);
        System.out.println(BJSUtils.t3(Arrays.stream(array).boxed().collect(Collectors.toList())).get().size());

        System.out.println("Testing T5");
        System.out.println("Sizes");
        System.out.println(BJSUtils.t5_1(transactions, bjsModel.compareTransactionsByDate).get().size());
        System.out.println(BJSUtils.t5_2(transactions, bjsModel.compareTransactionsByDate).get().size());

        System.out.println("Testing T6");
        System.out.println("Testing First Transaction of Month 1 Day 1 and Hour 1");
        System.out.println(BJSUtils.t6_7_1(transactions).get().get(Month.of(1)).get(1).get(1).get(0));
        System.out.println(BJSUtils.t6_8_1(transactions).get().get(Month.of(1)).get(1).get(1).get(0));
        System.out.println("Testing First Transaction of Monday and Hour 1");
        System.out.println(BJSUtils.t6_7_2(transactions).get().get(DayOfWeek.MONDAY).get(1).get(0));
        System.out.println(BJSUtils.t6_8_2(transactions).get().get(DayOfWeek.MONDAY).get(1).get(0));

        System.out.println("Testing T7");
        System.out.println(BJSUtils.t7_7(transactions).get());
        System.out.println(BJSUtils.t7_8_1(transactions).get());
        System.out.println(BJSUtils.t7_8_2(transactions).get());
        System.out.println(BJSUtils.t7_8_3(transactions).get());

        System.out.println("Testing T8");
        System.out.println(BJSUtils.t8_7(transactions).get());
        System.out.println(BJSUtils.t8_8(transactions).get());

        System.out.println("Testing T9");
        System.out.println("Testing for week 9");
        System.out.println(BJSUtils.t9_7(transactions, 9).get());
        System.out.println(BJSUtils.t9_8(transactions, 9).get());

        System.out.println("Testing T10");
        System.out.println("Sizes");
        System.out.println(BJSUtils.t10_7(transactions).get().size());
        System.out.println(BJSUtils.t10_8(transactions).get().size());
        System.out.println("Sum");
        System.out.println(BJSUtils.t10_7(transactions).get().stream().mapToDouble(d -> d).sum());
        System.out.println(BJSUtils.t10_8(transactions).get().stream().mapToDouble(d -> d).sum());

        System.out.println("Testing T12");
        System.out.println("Testing transaction 0 for counterID 2 and Month 1");
        System.out.println(BJSUtils.t12_Map_1(transactions).get().get("2").get(Month.of(1)).get(0));
        System.out.println(BJSUtils.t12_ConcurrentMap_1(transactions).get().get("2").get(Month.of(1)).get(0));
        System.out.println("Testing sum for counterID 2");
        System.out.println(BJSUtils.t12_Map_2(BJSUtils.t12_Map_1(transactions).get()).get().get("2"));
        System.out.println(BJSUtils.t12_ConcurrentMap_2(BJSUtils.t12_ConcurrentMap_1(transactions).get()).get().get("2"));
    }

    public static void main(String[] args) {
        tests();

        BJSView view = new BJSView();
        BJSModel model = new BJSModel();
        BJSController controller = new BJSController();

        controller.setView(view);
        controller.setModel(model);
        //controller.startFlow();
    }
}
