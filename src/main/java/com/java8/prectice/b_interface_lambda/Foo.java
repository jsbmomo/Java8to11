package com.java8.prectice.b_interface_lambda;


import java.util.function.*;

public class Foo {

  /**
   * 아래의 내용들은 Java Document 의 Package java.util.function 를 참고할 것.
   * @param args
   */
  public static void main(String[] args) {

    Plus10 plus10 = new Plus10();
    System.out.println(plus10.apply(1));

    Function<Integer, Integer> plus10Functional = (i) -> i + 10;
    Function<Integer, Integer> multiply2 = (i) -> i * 2;

    /**
     * compose 는 입력값(multiply2)을 해당 함수(plus10)에 적용
     */
    Function<Integer, Integer> multiply2AndPlus10 = plus10.compose(multiply2);
    System.out.println("multiply2AndPlus10 : " + multiply2AndPlus10.apply(2));
    System.out.println("multiply2AndPlus10 : " + plus10.andThen(multiply2).apply(2));

    /**
     * 타입을 받아서 출력만 하는 함수
     */
    Consumer<Integer> printT = (i) -> System.out.println("printT : " + i);
    printT.accept(10);

    /**
     * 파라미터를 받지 않고, 10을 무조건 출력하는 람다 함수 ==> 받아올 값을 정의
     */
    Supplier<Integer> get10 = () -> 10;
    System.out.println("get10 : " + get10.get());

    /**
     * 인자 값을 입력받아서 true, false 를 반환하는 함수 인터페이스
     */
    Predicate<String> startsWithKeesun = (s) -> s.startsWith("keesun");
    System.out.println("startsWithKeesun : " + startsWithKeesun.test("Hello Jeon"));

    Predicate<Integer> isEven = (i) -> i % 2 == 0;
    System.out.println("isEven : " + isEven.test(10));

    /**
     *  Function 함수 인터페이스의 특수한 형태, 입력 값의 타입과 결과 값의 타입이 같은 경우. ex 입력 값이 하나인 경우
     */
    UnaryOperator<Integer> plus20 = (i) -> i + 20;
    Function<Integer, Integer> multply3 = (i) -> i * 3;

    System.out.println("Predicate : " + plus20.andThen(multply3).apply(6));

  }
}

