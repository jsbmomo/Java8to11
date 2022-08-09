package com.java8.prectice.c_interface_change;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FooMain {

  public static void main(String[] args) {
    Foo foo = new FooDefault("jeon");
    foo.printName();

    // Foo 인터페이스에 메소드 선언이 아닌 구현제를 만들어 사용가능.
    foo.printNameUpperCase();

    System.out.println("\n[ Learn List ]");
    /**
     * List 의 다양한 활용
     */
    List<String> name = new ArrayList<>();
    name.add("jeon");
    name.add("seung");
    name.add("beom");
    name.add("fight");
    name.add("remove");

    // 특정 문자로 시작하는 것을 제거
    name.removeIf(s -> s.startsWith("r"));
    name.forEach(System.out::print);

    // List의 String 하나하나를 가져와서 대분자로 변환,
    // 첫 글자가 J이면 개수 카운트
    long cnt = name.stream().map(String::toUpperCase)
                  .filter(s -> s.startsWith("J"))
                  .count();
    System.out.println("\nJ count : " + cnt);

    // 시작 문자가 J 인것만 HashSet 으로 생성
    Set<String> contain = name.stream().map(String::toUpperCase)
                  .filter(s -> s.startsWith("J"))
                  .collect(Collectors.toSet());
    System.out.println("Set J : " + contain.toString());

    // 문자열의 정렬과 역순, 또 다른 조건으로 정렬
    Comparator<String> comparatorToIgnoreCase = String::compareToIgnoreCase;
    name.sort(comparatorToIgnoreCase);
    name.sort(comparatorToIgnoreCase.reversed());
    // name.sort(comparatorToIgnoreCase.reversed().thenComparing());
    System.out.println(name.toString());

  }
}
