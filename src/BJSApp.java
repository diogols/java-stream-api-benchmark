import controller.BJSController;
import model.BJSUtils;
import view.BJSView;

public class BJSApp {

    public static void main(String[] args) {
        BJSView view = new BJSView();
        BJSUtils model = new BJSUtils();
        BJSController controller = new BJSController();

        controller.setView(view);
        controller.setModel(model);
        controller.startFlow();




        //List<Transaction> transactions = BJSUtils.load("transCaixa6M.txt", BJSUtils.parseTransaction);

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
        //int[] random = BJSUtils.generateArrayInt(1000000);

        //SimpleEntry<Double, IntStream> intStream = BJSUtils.testBox(0, BJSUtils.t3(Arrays.stream(random)));
        //SimpleEntry<Double, int[]> intArray = BJSUtils.testBox(0, BJSUtils.t3(random));
        //SimpleEntry<Double, List<Integer>> listInteger = BJSUtils.testBox(0, BJSUtils.t3(Arrays.stream(random).boxed().collect(toList())));

        //System.out.println(intStream.getKey() + " " + isDistinct(intStream.getValue().boxed().collect(toList())));
        //System.out.println(intArray.getKey() + " " + isDistinct(Arrays.stream(intArray.getValue()).boxed().collect(toList())));
        //System.out.println(listInteger.getKey() + " " + isDistinct(listInteger.getValue()));

        //T4

        /*double[] values = BJSUtils.t1_7_1(transactions).get();

        SimpleEntry<Double, Double> t4_8_1_1 = BJSUtils.testBox(BJSUtils.t4_8_1_1(values));
        SimpleEntry<Double, Double> t4_8_1_2 = BJSUtils.testBox(BJSUtils.t4_8_1_2(values));
        SimpleEntry<Double, Double> t4_8_2_1 = BJSUtils.testBox(BJSUtils.t4_8_2_1(values));
        SimpleEntry<Double, Double> t4_8_2_2 = BJSUtils.testBox(BJSUtils.t4_8_2_2(values));

        System.out.println(t4_8_1_1.getKey() + " " + t4_8_1_1.getValue());
        System.out.println(t4_8_1_2.getKey() + " " + t4_8_1_2.getValue());
        System.out.println(t4_8_2_1.getKey() + " " + t4_8_2_1.getValue());
        System.out.println(t4_8_2_2.getKey() + " " + t4_8_2_2.getValue());*/

    }
}
