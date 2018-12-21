package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import java.util.AbstractMap.SimpleEntry;

import static java.util.stream.Collectors.toList;


public final class BJSUtils {
    private final static int RUNS = 10;

    public static <R> List<R> load(final String filename, final Function<String, R> function) {
        List<R> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            list = stream.map(function).collect(toList());
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    public static Function<String, Transaction> toTransaction = (line) -> {
        final String[] fields = line.split("/");
        final String id = fields[0].trim();
        final String counterId = fields[1].trim();
        final double value;

        try {
            value = Double.parseDouble(fields[2]);
        } catch(InputMismatchException | NumberFormatException e) {
            return null;
        }

        final String[] dMYHMS = fields[3].split("T");
        final String[] dMY = dMYHMS[0].split(":");
        final String[] hM = dMYHMS[1].split(":");

        final int year;
        final int month;
        final int day;
        final int hours;
        final int minutes;

        try {
            day = Integer.parseInt(dMY[0]);
            month = Integer.parseInt(dMY[1]);
            year = Integer.parseInt(dMY[2]);
            hours = Integer.parseInt(hM[0]);
            minutes = Integer.parseInt(hM[1]);
        } catch(InputMismatchException | NumberFormatException e) {
            return null;
        }

        return Transaction.of(id, counterId, value, LocalDateTime.of(year, month, day, hours, minutes, 0));
    };

    public static Supplier<double[]> j7ForT1(final List<Transaction> transactions) {
        return () -> {
            final int size = transactions.size();
            final double[] values = new double[size];

            for (int i = 0; i < size; i++) {
                values[i] = transactions.get(i).getValue();
            }
            return values;
        };
    }

    public static <R> SimpleEntry<Double,R> testBox(int runs, Supplier<? extends R> supplier)  {
        // warm up
        for(int i = 1 ; i <= runs; i++) {
            supplier.get();
        }
        System.gc();
        Crono.start();
        R r = supplier.get();
        Double time = Crono.stop();
        return new SimpleEntry<>(time, r);
    }

    public static <R> SimpleEntry<Double,R> testBox(Supplier<? extends R> supplier)  {
       return testBox(RUNS, supplier);
    }

    public static Supplier<double[]> j7ForEachT1(List<Transaction> transactions) {
        return () -> {
            final double[] values = new double[transactions.size()];

            int i = 0;
            for (Transaction t : transactions) {
                values[i++] = t.getValue();
            }
            return values;
        };
    }

    public static Supplier<Stream<Double>> j8SDT1(List<Transaction> transactions) {
        return () -> transactions.stream().map(Transaction::getValue);
    }

    public static Supplier<DoubleStream> j8DST1(List<Transaction> transactions) {
        return () -> transactions.stream().mapToDouble(Transaction::getValue);
    }

    public static Supplier<Stream<Double>> j8SDPT1(List<Transaction> transactions) {
        return () -> transactions.parallelStream().map(Transaction::getValue);
    }

    public static Supplier<DoubleStream> j8DSPT1(List<Transaction> transactions) {
        return () -> transactions.parallelStream().mapToDouble(Transaction::getValue);
    }
    


}
