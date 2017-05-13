package test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Lambda {

    static Consumer<Integer> consumInteger = i -> System.out.println(i);

    static Supplier<Double> supplyRand = () -> Math.random();

    static Predicate<Integer> predicate = i -> i % 3 == 0;

    static BiConsumer<String, String> consumerStrings = (str1, str2) -> {
        if (str1.equalsIgnoreCase(str2))
            System.out.println(str1);
        else
            System.out.println(str1 + str2);
    };

    static TriFunction<Integer> max = (i1, i2, i3) -> Math.max(Math.max(i1, i2), i3);

    static void methodSort() {
        Integer[] array = { 2, 8, 4, 6, 3, 7 };
        Arrays.sort(array, new Comparator<Integer>() {

            @Override
            public int compare(Integer i1, Integer i2) {

                return i1 - i2;
            }
        });

        System.out.println(Arrays.asList(array));
    }

    static void lambdaSort() {
        Integer[] array = { 2, 8, 4, 6, 3, 7 };
        Arrays.sort(array, (i1, i2) -> i1 - i2);

        System.out.println(Arrays.asList(array));
    }

    static void collections() {
        Integer[] array = { 2, 30, 2, 6, 56, 34, 12, 7, 26, 8, 48 };
        List<Integer> list = Arrays.asList(array);

        List<Integer> list2 = list.stream().filter(predicate).collect(Collectors.toList());
        System.out.println(list2);
    }

    static void groupList() {
        Integer[] array = { 2, 30, 11, 6, 56, 34, 12, 7, 26, 8, 48 };
        List<Integer> list = Arrays.asList(array);

        Map<String, List<Integer>> map = list.stream()
                .collect(Collectors.groupingBy(i -> i - i % 10 + "", Collectors.toList()));

        map.entrySet().forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue().toString()));
    }

    public static void main(final String[] args) {
        consumInteger.accept(19);
        System.out.println(supplyRand.get());
        System.out.println(predicate.test(19));
        consumerStrings.accept("string", "String");

        methodSort();
        lambdaSort();
        collections();
        groupList();

        System.out.println(max.Operation(1, 2, 3));
    }

    @FunctionalInterface
    interface TriFunction<T> {
        public T Operation(T t1, T t2, T t3);
    }
}
