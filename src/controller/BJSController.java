package controller;

import model.BJSUtils;
import view.BJSView;

public class BJSController {
    private BJSView view;
    private BJSUtils model;

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
}