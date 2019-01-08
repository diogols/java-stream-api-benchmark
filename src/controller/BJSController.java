package controller;

import model.BJSModel;
import model.BJSUtils;
import model.Crono;
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
        Crono.start();
        transactions = Arrays.asList(BJSUtils.load("transCaixa1M.txt", BJSUtils.parseTransaction),
                BJSUtils.load("transCaixa2M.txt", BJSUtils.parseTransaction),
                BJSUtils.load("transCaixa4M.txt", BJSUtils.parseTransaction),
                BJSUtils.load("transCaixa6M.txt", BJSUtils.parseTransaction));
        System.out.println("Files loaded in " + Crono.stop() + " seconds");

        //model.t1(transactions);
        //model.t2(transactions);
        //model.t3();
        //model.t4(transactions);
        //model.t5(transactions);
        /*model.t6(transactions);
        model.t7(transactions);
        model.t8(transactions);
        model.t9(transactions);
        model.t10(transactions);
        model.t11(transactions);
        model.t12(transactions);*/
    }
}