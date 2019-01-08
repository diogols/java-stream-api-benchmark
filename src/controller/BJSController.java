package controller;

import model.BJSModel;
import model.BJSUtils;
import model.Crono;
import model.Transaction;
import view.BJSView;

import java.util.Arrays;
import java.util.List;

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
        Crono.start();
        transactions = Arrays.asList(BJSUtils.load("transCaixa1M.txt", BJSUtils.parseTransaction),
                BJSUtils.load("transCaixa2M.txt", BJSUtils.parseTransaction),
                BJSUtils.load("transCaixa4M.txt", BJSUtils.parseTransaction),
                BJSUtils.load("transCaixa6M.txt", BJSUtils.parseTransaction));
        System.out.println("Files loaded in " + Crono.stop() + " seconds");

        System.out.println("Starting test 1");
        model.t1(transactions);
        System.out.println("Test 1 done");
        System.out.println("Starting test 2");
        model.t2(transactions);
        System.out.println("Test 2 done");
        System.out.println("Starting test 3");
        model.t3();
        System.out.println("Test 3 done");
        System.out.println("Starting test 4");
        model.t4(transactions);
        System.out.println("Test 4 done");
        System.out.println("Starting test 5");
        model.t5(transactions);
        System.out.println("Test 5 done");
        System.out.println("Starting test 6");
        model.t6(transactions);
        System.out.println("Test 6 done");
        System.out.println("Starting test 7");
        model.t7(transactions);
        System.out.println("Test 7 done");
        System.out.println("Starting test 8");
        model.t8(transactions);
        System.out.println("Test 8 done");
        System.out.println("Starting test 9");
        model.t9(transactions);
        System.out.println("Test 9 done");
        System.out.println("Starting test 10");
        model.t10(transactions);
        System.out.println("Test 10 done");
        System.out.println("Starting test 11");
        model.t11(transactions);
        System.out.println("Test 11 done");
        System.out.println("Starting test 12");
        model.t12(transactions);

        System.out.println("All done");
    }
}