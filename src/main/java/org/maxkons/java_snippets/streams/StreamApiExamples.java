package org.maxkons.java_snippets.streams;


import scala.Tuple2;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamApiExamples {

    public static List<TableRow> data = new ArrayList<>();

    // Sample data imitates table: | ID (Long) | user (String) | amount (Integer) |
    static {
        data.add(new TableRow(10L, "Cindy", 111));
        data.add(new TableRow(9L, "Cindy", 222));
        data.add(new TableRow(2L, "Alex", 333));
        data.add(new TableRow(5L, "Alex", 444));
        data.add(new TableRow(4L, "Alex", 555));
        data.add(new TableRow(7L, "Bob", 666));
        data.add(new TableRow(8L, "Bob", 777));
    }

    private static class TableRow {
        public Long id;
        public String user;
        public Integer amount;

        public TableRow(Long id, String user, Integer amount) {
            this.id = id;
            this.user = user;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "TableRow{" +
                    "id=" + id +
                    ", user='" + user + '\'' +
                    ", amount=" + amount +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        findAnyRowByPredicate();
        getIndexById();
        getRowsWithHighestAmountForEachUser();
    }

    public static void findAnyRowByPredicate() {
        Predicate<TableRow> predicate = t -> t.user.equals("Alex");
        System.out.println("findAnyRowByPredicate user == 'Alex': " +
            data.
            stream()
            .filter(predicate)
            .findAny()
            .orElse(null)
        );
        System.out.println();
    }

    public static void getIndexById() {
        long elementToFind = 2L;
        System.out.println("getIndexById ID == 2: " +
                IntStream.range(0, data.size() - 1)
                .mapToObj(i -> new Tuple2<>(i, data.get(i).id))
                .filter(t -> t._2.equals(elementToFind))
                .findFirst().orElse(new Tuple2(-1, 0))._1
        );
        System.out.println();
    }

    public static void getRowsWithHighestAmountForEachUser() {
        List<TableRow> sorted = data
                .stream()
                .collect(Collectors.groupingBy(t -> t.user))
                .entrySet()
                .stream()
                .map(v -> v.getValue().stream().max((row1, row2) -> row1.amount.compareTo(row2.amount)).get())
                .collect(Collectors.toList());
        sorted.forEach(System.out::println);
        System.out.println();
    }


}
