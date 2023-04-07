package com.java8.prectice.d_stream_add;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class A_generateStream {

  public void CharStream() {
    // 문자열의 각 문자를 IntStream으로 변환 가능.
    IntStream intStream = "Java8Stream".chars();
    intStream.forEach(System.out::println);

    IntStream intStreamFilter = "Java8Stream".chars().filter((tempInteger) -> tempInteger > 100);

    IntStream intStream1 = "asdfasdf".chars();

    // 정규식을 활용하여 문자열을 자르고 스트림으로 생성
    Stream<String> stringStream = Pattern.compile(" ").splitAsStream("Java8의 Stream 입니다.");
    stringStream.forEach(System.out::println);
  }

  public void CollectionStream() {
    String[] ary = new String[]{"Java", "eight", "Stream"};
    Stream<String> stream1 = Arrays.stream(ary); // stream 생성
    Stream<String> partStream = Arrays.stream(ary, 1, 2); // 일부만 stream

    // List<String> list1 = stream1.toList(ary);
    List<String> list2 = Arrays.asList("Java", "eight", "Stream");

    Stream<String> stream2 = list2.stream();
    Stream<String> parallelStream = list2.parallelStream(); // 병렬 처리 스트림
  }

}
