package view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class BJSView {

    private final Map<Integer, Menu> menus;

    public BJSView() {
        menus = new HashMap<>();
        menus.put(0, new Menu("Choose an option...",
                Arrays.asList(new Option("Run all tests -", "1"),
                        new Option("Run one test --", "2"),
                        new Option("Exit ----------", "0")))
        );
    }

    public void displayMessage(final String message) {
        System.out.print(message);
    }

    public void displayMenu(final int menu) {
        if (menus.containsKey(menu)) {
            this.menus.get(menu).show();
        }
    }
}