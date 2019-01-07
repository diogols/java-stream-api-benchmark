package view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public final class BJSView {

    private final Map<Integer, Menu> menus;

    public BJSView() {
        menus = Map.ofEntries(
                entry(0, new Menu("Choose an option...", Arrays.asList(
                        new Option("Run all tests -", "1"),
                        new Option("Run one test --", "2"),
                        new Option("Exit ----------", "0"))))
        );
    }

    public void displaySpacing() {
        System.out.println("\n".repeat(10));
    }

    public void displayMessage(final String message) {
        System.out.print(message);
    }

    public void displayLocalDate(final LocalDate date, final DateTimeFormatter formatter) {
        System.out.println(date.format(formatter));
    }

    public void displayLocalDateTime(final LocalDateTime datetime, final DateTimeFormatter formatter) {
        System.out.print(datetime.format(formatter));
    }

    public void displayPage(final List<String> elements, final int currentPage, final int totalPages) {
        this.displaySpacing();

        int i = 1;

        for(String element : elements) {
            System.out.println(element + " " + i);
            i++;
        }

        System.out.println("Page " + currentPage + " of " + totalPages);
    }

    public void displayMenu(final int menu) {
        this.displaySpacing();

        if (menus.containsKey(menu)) {
            this.menus.get(menu).show();
        }
    }
}