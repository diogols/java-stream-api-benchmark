package controller;

import model.BJSModel;
import model.BJSUtils;
import model.Transaction;
import view.BJSView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class BJSController {
    private BJSView view;
    private BJSModel model;
    private List<List<Transaction>> transactions;

    public void setView(final BJSView view) {
        this.view = view;
    }

    public void setModel(final BJSModel model) {
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
                case "\n":
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
        transactions = Arrays.asList(BJSUtils.load("transCaixa1M.txt", BJSUtils.parseTransaction),
                BJSUtils.load("transCaixa2M.txt", BJSUtils.parseTransaction),
                BJSUtils.load("transCaixa4M.txt", BJSUtils.parseTransaction),
                BJSUtils.load("transCaixa6M.txt", BJSUtils.parseTransaction));

        //this.t1();
        //this.t3();
        //this.t4();
        this.t5();
    }

    private void t1() {
        model.t1(transactions);
    }

    private void t2() {

    }

    private void t3() {
        final List<int[]> list = new ArrayList<>();

        IntStream.range(1, 9).forEach(i -> list.add(model.generateArrayInt(i * 1000000)));

        model.t3(list);
    }

    private void t4() {
        model.t4(transactions);
    }

    private void t5() {
        model.t5(transactions);
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