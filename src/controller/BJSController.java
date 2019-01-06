package controller;

import model.BJSUtils;
import model.Transaction;
import view.BJSView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BJSController {
    private BJSView view;
    private BJSUtils model;
    private List<Transaction> transactions1M;
    private List<Transaction> transactions2M;
    private List<Transaction> transactions4M;
    private List<Transaction> transactions6M;

    public void setView(final BJSView view) {
        this.view = view;
    }

    public void setModel(final BJSUtils model) {
        this.model = model;
    }

    public void startFlow() {
        String option;
        boolean displayMenu = true;

        do {
            if (displayMenu) {
                view.displayMenu(0);
            }

            displayMenu = true;
            view.displayMessage("Insert option: ");
            option = Input.readString();

            switch (option) {
                case "1":
                    runAllTests();
                    break;
                case "2":
                    break;
                case "0":
                    break;
                default:
                    view.displayMessage("Invalid option!\n");
                    displayMenu = false;
            }
        } while (!option.equals("0"));
    }

    private void runAllTests() {
        /*transactions1M = BJSUtils.load("transCaixa1M.txt", BJSUtils.parseTransaction);
        transactions2M = BJSUtils.load("transCaixa2M.txt", BJSUtils.parseTransaction);
        transactions4M = BJSUtils.load("transCaixa4M.txt", BJSUtils.parseTransaction);
        transactions6M = BJSUtils.load("transCaixa6M.txt", BJSUtils.parseTransaction);*/

        //this.t1();
        this.t3();
    }

    private void t1() {
        BJSUtils.writeCSV("t1.csv",
                Arrays.asList("Java 7 for", "Java 7 forEach", "Java 8 DoubleStream", "Java 8 DoubleStream parallel",
                        "Java 8 Stream<Double>", "Java 8 Stream<Double> parallel"),
                Arrays.asList(
                        Arrays.asList(BJSUtils.testBox(BJSUtils.t1_7_1(transactions1M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_7_2(transactions1M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_1_1(transactions1M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_1_2(transactions1M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_2_1(transactions1M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_2_2(transactions1M)).getKey()),
                        Arrays.asList(BJSUtils.testBox(BJSUtils.t1_7_1(transactions2M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_7_2(transactions2M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_1_1(transactions2M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_1_2(transactions2M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_2_1(transactions2M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_2_2(transactions2M)).getKey()),
                        Arrays.asList(BJSUtils.testBox(BJSUtils.t1_7_1(transactions4M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_7_2(transactions4M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_1_1(transactions4M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_1_2(transactions4M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_2_1(transactions4M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_2_2(transactions4M)).getKey()),
                        Arrays.asList(BJSUtils.testBox(BJSUtils.t1_7_1(transactions6M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_7_2(transactions6M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_1_1(transactions6M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_1_2(transactions6M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_2_1(transactions6M)).getKey(),
                                BJSUtils.testBox(BJSUtils.t1_8_2_2(transactions6M)).getKey())
                ));
    }

    private void t2() {

    }

    private void t3() {
        int[] random1 = BJSUtils.generateArrayInt(1000000);
        int[] random2 = BJSUtils.generateArrayInt(2000000);
        int[] random3 = BJSUtils.generateArrayInt(3000000);
        int[] random4 = BJSUtils.generateArrayInt(4000000);
        int[] random5 = BJSUtils.generateArrayInt(5000000);
        int[] random6 = BJSUtils.generateArrayInt(6000000);
        int[] random7 = BJSUtils.generateArrayInt(7000000);
        int[] random8 = BJSUtils.generateArrayInt(8000000);

        BJSUtils.writeCSV("t3.csv",
                Arrays.asList("IntStream", "int[] array", "List<Integer>"),
                Arrays.asList(
                        Arrays.asList(BJSUtils.testBox(BJSUtils.t3_IntStream(random1)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(random1)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(Arrays.stream(random1).boxed()
                                        .collect(Collectors.toList()))).getKey()),
                        Arrays.asList(BJSUtils.testBox(BJSUtils.t3_IntStream(random2)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(random2)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(Arrays.stream(random2).boxed()
                                        .collect(Collectors.toList()))).getKey()),
                        Arrays.asList(BJSUtils.testBox(BJSUtils.t3_IntStream(random3)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(random3)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(Arrays.stream(random3).boxed()
                                        .collect(Collectors.toList()))).getKey()),
                        Arrays.asList(BJSUtils.testBox(BJSUtils.t3_IntStream(random4)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(random4)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(Arrays.stream(random4).boxed()
                                        .collect(Collectors.toList()))).getKey()),
                        Arrays.asList(BJSUtils.testBox(BJSUtils.t3_IntStream(random5)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(random5)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(Arrays.stream(random5).boxed()
                                        .collect(Collectors.toList()))).getKey()),
                        Arrays.asList(BJSUtils.testBox(BJSUtils.t3_IntStream(random6)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(random6)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(Arrays.stream(random6).boxed()
                                        .collect(Collectors.toList()))).getKey()),
                        Arrays.asList(BJSUtils.testBox(BJSUtils.t3_IntStream(random7)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(random7)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(Arrays.stream(random7).boxed()
                                        .collect(Collectors.toList()))).getKey()),
                        Arrays.asList(BJSUtils.testBox(BJSUtils.t3_IntStream(random8)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(random8)).getKey(),
                                BJSUtils.testBox(BJSUtils.t3(Arrays.stream(random8).boxed()
                                        .collect(Collectors.toList()))).getKey())
                ));
    }

    private void t4() {

    }

    private void t5() {

    }

    private void t6() {

    }

    private void t7() {

    }

    private void t8() {

    }

    private void t9() {

    }

    private void t10() {

    }
}