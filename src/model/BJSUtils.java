package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import java.util.AbstractMap.SimpleEntry;

import static java.util.stream.Collectors.*;


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

    public static Comparator<Transaction> compareTransactionsById = Comparator.comparing(Transaction::getId);

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

    // T1

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




    // T5

    public static Supplier<Set<Transaction>> t5_7(List<Transaction> transactions, Comparator<Transaction> comparator) {
        return () -> new TreeSet<>(comparator) {{ addAll(transactions); }};
    }

    public static Supplier<List<Transaction>> t5_8(List<Transaction> transactions, Comparator<Transaction> comparator) {
        return () -> transactions.stream().sorted(comparator).collect(toList());
    }

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

	
	// T7
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
        };
    }

    /*
    public static Supplier<Double> t7_8_2(List<Transaction> transactions) {
        return () -> transactions.stream().mapToDouble(Transaction::getValue).sum();
    }
    */

    public static Supplier<Double> t7_8_2(List<Transaction> transactions) {
        return () -> transactions.stream().map(Transaction::getValue).reduce(0.0, Double::sum);
    }

    public static Supplier<Double> t7_8_3(List<Transaction> transactions) {
        return () -> transactions.parallelStream().map(Transaction::getValue).reduce(0.0, Double::sum);
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

    // T9

    public static Supplier<Double> t9_7(List<Transaction> transactions, int weekOfYear) {
        return t9_7(toWeekDayLists(transactions).get(weekOfYear));
    }

    public static Supplier<Double> t9_8(List<Transaction> transactions, int weekOfYear) {
        return t9_8(toWeekDayLists(transactions).get(weekOfYear));
    }


    // Argument is the week of transactions of some week in a year
    public static Supplier<Double> t9_7(List<List<Transaction>> transactions) {
        return () -> {
            double sum = 0;
            for (List<Transaction> l : transactions) {
                for (Transaction t : l) {
                    sum += t.getValue();
                }
            }
            return sum;
        };
    }

    public static Supplier<Double> t9_8(List<List<Transaction>> transactions) {
        return () -> transactions.stream().map(l -> l.stream().map(Transaction::getValue).reduce(0.0, Double::sum)).reduce(0.0, Double::sum);
    }

    // Create another method with Java 8 maybe
    public static List<List<List<Transaction>>> toWeekDayLists(List<Transaction> transactions) {
        int week;
        int day;
        List<List<Transaction>> days;

        final List<List<List<Transaction>>> weekDayList = new ArrayList<>();

        for (int i = 0; i < 53; i++) {
            days = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                days.add(new ArrayList<>());
            }
            weekDayList.add(days);
        }

        for (Transaction t: transactions) {
            week = t.getDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) - 1;
            day = t.getDate().getDayOfWeek().getValue() - 1;

            weekDayList.get(week).get(day).add(t);
        }

        return weekDayList;
    }

    // T10

    /*
    T10: Admitindo que o IVA a entregar por transacção é de 12% para transacções menores que 20 Euros,
    20% entre 20 e 29 e 23% para valores superiores,
    crie uma tabela com o valor de IVA total a pagar por mês.
    Compare as soluções em JAVA 7 e Java 8.
     */

    public static Supplier<List<Double>> t10_7(List<Transaction> transactions) {
        return () -> {
            double value;
            int month;
            final List<Double> vat = new ArrayList<>();

            for (int i = 0; i < 12; i++) {
                vat.add(0.0);
            }

            for (Transaction t : transactions) {
                value = t.getValue();
                month = t.getDate().getMonth().getValue() - 1;

                if (value > 29) {
                    vat.set(month, vat.get(month) + value * 0.23);
                } else if (value < 20) {
                    vat.set(month, vat.get(month) + value * 0.12);
                } else {
                    vat.set(month, vat.get(month) + value * 0.20);
                }
            }

            return vat;
        };
    }

    // should return toList() directly but i do not know how
    public static Supplier<List<Double>> t10_8(List<Transaction> transactions) {
        return () -> new ArrayList<>(transactions.stream().collect(groupingBy(t -> t.getDate().getMonth().getValue(), TreeMap::new, summingDouble(t -> {
            if (t.getValue() > 29) {
                return t.getValue() * 0.23;
            } else if (t.getValue() < 20) {
                return t.getValue() * 0.12;
            } else {
                return t.getValue() * 0.20;
            }
        }))).values());
    }

    // T12

    public static Supplier<Map<String, Map<Month, List<Transaction>>>> t12_Map_1(List<Transaction> transactions) {
        return () -> transactions.parallelStream().collect(groupingBy(Transaction::getCounterId,
                groupingBy(t -> t.getDate().getMonth())));
    }

    public static Supplier<ConcurrentMap<String, ConcurrentMap<Month, List<Transaction>>>> t12_ConcurrentMap_1(List<Transaction> transactions) {
        return () -> transactions.parallelStream().collect(groupingByConcurrent(Transaction::getCounterId,
                groupingByConcurrent(t -> t.getDate().getMonth())));
    }

    public static Supplier<Map<String, Double>> t12_Map_2(Map<String, Map<Month, List<Transaction>>> map) {
        return () -> map.entrySet().parallelStream().collect(toMap(
                Map.Entry::getKey,
                e -> e.getValue().values().stream().map(l -> l.stream().map(Transaction::getValue)
                        .reduce(0.0, Double::sum)).reduce(0.0, Double::sum), Double::sum)
        );
    }

    public static Supplier<ConcurrentMap<String, Double>> t12_ConcurrentMap_2(ConcurrentMap<String, ConcurrentMap<Month, List<Transaction>>> map) {
        return () -> map.entrySet().parallelStream().collect(toConcurrentMap(
                ConcurrentMap.Entry::getKey,
                e -> e.getValue().values().stream().map(l -> l.stream().map(Transaction::getValue)
                                .reduce(0.0, Double::sum)).reduce(0.0, Double::sum), Double::sum)
        );
    }
}
