package model;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import java.util.AbstractMap.SimpleEntry;

import static java.util.stream.Collectors.groupingBy;
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

    public static Supplier<double[]> t1_7_1(final List<Transaction> transactions) {
        return () -> {
            final int size = transactions.size();
            final double[] values = new double[size];

            for (int i = 0; i < size; i++) {
                values[i] = transactions.get(i).getValue();
            }
            return values;
        };
    }

    public static Supplier<double[]> t1_7_2(List<Transaction> transactions) {
        return () -> {
            final double[] values = new double[transactions.size()];

            int i = 0;
            for (Transaction t : transactions) {
                values[i++] = t.getValue();
            }
            return values;
        };
    }

    public static Supplier<Stream<Double>> t1_8_1(List<Transaction> transactions) {
        return () -> transactions.stream().map(Transaction::getValue);
    }

    public static Supplier<DoubleStream> t1_8_2(List<Transaction> transactions) {
        return () -> transactions.stream().mapToDouble(Transaction::getValue);
    }

    public static Supplier<Stream<Double>> t1_8_3(List<Transaction> transactions) {
        return () -> transactions.parallelStream().map(Transaction::getValue);
    }

    public static Supplier<DoubleStream> t1_8_4(List<Transaction> transactions) {
        return () -> transactions.parallelStream().mapToDouble(Transaction::getValue);
    }

    // DIOGO RAFAEL
    // T6

    public static Supplier<Map<Month, Map<Integer, Map<Integer, List<Transaction>>>>> t6_8_1(List<Transaction> transactions) {
        return () -> transactions.stream().collect(groupingBy(t -> t.getDate().getMonth(),
                groupingBy(t -> t.getDate().getDayOfMonth(),
                        groupingBy(t -> t.getDate().getHour()))));
    }

    public static Supplier<Map<DayOfWeek, Map<Integer, List<Transaction>>>> t6_7_2(List<Transaction> transactions) {
        return () -> {
            Map<DayOfWeek, Map<Integer, List<Transaction>>> map = new HashMap<>();
            DayOfWeek day;
            int hour;
            LocalDateTime dateTime;
            Map<Integer, List<Transaction>> hourMap;
            List<Transaction> list;

            for (Transaction t : transactions) {
                dateTime = t.getDate();
                day = dateTime.getDayOfWeek();
                hour = dateTime.getHour();

                hourMap = map.get(day);
                if (hourMap != null) {
                    list = hourMap.computeIfAbsent(hour, k -> new ArrayList<>());
                    list.add(t);
                } else {
                    list = new ArrayList<>();
                    list.add(t);

                    hourMap = new HashMap<>();
                    hourMap.put(hour, list);

                    map.put(day, hourMap);
                }
            }
            return map;
        };
    }

    public static Supplier<Map<Month, Map<Integer, Map<Integer, List<Transaction>>>>> t6_7_1(List<Transaction> transactions) {
        return () -> {
            Map<Month, Map<Integer, Map<Integer, List<Transaction>>>> map = new HashMap<>();
            Month month;
            int day;
            int hour;
            LocalDateTime dateTime;
            Map<Integer, Map<Integer, List<Transaction>>> dayMap;
            Map<Integer, List<Transaction>> hourMap;
            List<Transaction> list;

            for (Transaction t : transactions) {
                dateTime = t.getDate();
                month = dateTime.getMonth();
                day = dateTime.getDayOfMonth();
                hour = dateTime.getHour();

                dayMap = map.get(month);
                if (dayMap != null) {
                    hourMap = dayMap.get(day);

                    if (hourMap != null) {
                        list = hourMap.computeIfAbsent(hour, k -> new ArrayList<>());
                        list.add(t);
                    } else {
                        list = new ArrayList<>();
                        list.add(t);

                        hourMap = new HashMap<>();
                        hourMap.put(hour, list);

                        dayMap.put(day, hourMap);
                    }
                } else {
                    list = new ArrayList<>();
                    list.add(t);

                    hourMap = new HashMap<>();
                    hourMap.put(hour, list);

                    dayMap = new HashMap<>();
                    dayMap.put(day, hourMap);

                    map.put(month, dayMap);
                }
            }
            return map;
        };
    }

    public static Supplier<Map<DayOfWeek, Map<Integer, List<Transaction>>>> t6_8_2(List<Transaction> transactions) {
        return () -> transactions.stream().collect(groupingBy(t -> t.getDate().getDayOfWeek(),
                groupingBy(t -> t.getDate().getHour())));
    }

    public static Supplier<Double> t7_7(List<Transaction> transactions) {
        return () -> {
            double sum = 0;
            for (Transaction t : transactions) {
                sum += t.getValue();
            }
            return sum;
        };
    }

    public static Supplier<Double> t7_8_1(List<Transaction> transactions) {
        return () -> {
            Spliterator<Transaction> spliterator0 = transactions.spliterator();
            Spliterator<Transaction> spliterator1 = spliterator0.trySplit();
            Spliterator<Transaction> spliterator2 = spliterator0.trySplit();
            Spliterator<Transaction> spliterator3 = spliterator1.trySplit();

            ForkJoinPool pool = new ForkJoinPool(4);

            Function<Spliterator<Transaction>, Double> sumFunction = spliterator -> {
                var doubleWrapper = new Object() { double value = 0; };
                while(spliterator.tryAdvance(t -> doubleWrapper.value += t.getValue()));
                return doubleWrapper.value;
            };

            List<Future<Double>> futures = pool.invokeAll(Arrays.asList(
                    () -> sumFunction.apply(spliterator0),
                    () -> sumFunction.apply(spliterator1),
                    () -> sumFunction.apply(spliterator2),
                    () -> sumFunction.apply(spliterator3))
            );

            double sum = 0;
            for (Future<Double> future : futures) {
                try {
                    sum += future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            return sum;

//            return futures.stream().mapToDouble(f -> {
//                try {
//                    return f.get();
//                } catch (InterruptedException | ExecutionException e) {
//                    return 0;
//                }
//            }).sum();
        };
    }

    public static Supplier<Double> t7_8_2(List<Transaction> transactions) {
        return () -> transactions.stream().mapToDouble(Transaction::getValue).sum();
    }

    public static Supplier<Double> t7_8_3(List<Transaction> transactions) {
        return () -> transactions.parallelStream().mapToDouble(Transaction::getValue).sum();
    }

    public static Supplier<String> t8_7(List<Transaction> transactions) {
        return () -> {
            final int startHour = 16;
            final int endHour = 22;

            String id = "";
            double value = 0;

            for (Transaction t : transactions) {
                if (t.getDate().getHour() >= 16 && t.getDate().getHour() < 22 && t.getValue() > value) {
                    id = t.getId();
                    value = t.getValue();
                }
            }

            return id;
        };
    }

    public static Supplier<String> t8_8(List<Transaction> transactions) {
        return () -> {
            final int startHour = 16;
            final int endHour = 22;

            // i could use a Transaction instance but this seems faster
            var transaction = new Object() { String id; double value = 0; };

            // i am excluding 22:00:00 just for the sake of code simplicity
            transactions.forEach(t -> {
                        if (t.getDate().getHour() >= 16 && t.getDate().getHour() < 22 && t.getValue() > transaction.value) {
                            transaction.value = t.getValue();
                            transaction.id = t.getId();
                        }
                    } );

            return transaction.id;
        };
    }
}


























