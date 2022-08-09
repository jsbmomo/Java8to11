package com.java8.prectice.b_interface_lambda;


import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class MethodReference {

  public static void main(String[] args) {
    /**
     * UnaryOperator는 입력 값과 결과 값의 타입이 같은 경우 사용.
     * ::를 사용한다면 method reference 를 사용하는 것으로, 아래는 static 메소드에 접근할 때 사용.
     */
    UnaryOperator<String> hi = MethodReferenceGreeting::hi;
    // UnaryOperator<String> hi = (s) -> "hi " + s;

    /**
     * 단순히 MethodReferenceGreeting::new 를 사용한다고 인스턴스가 생성되는 것은 아니다.
     * newGreting.get() 를 사용해야 인스턴스가 생성된다.
     */
    Supplier<MethodReferenceGreeting> newGreeting = MethodReferenceGreeting::new;
    MethodReferenceGreeting greeting = newGreeting.get();
    System.out.println(greeting);

    /**
     * String 입력 값을 받고. Greeting을 리턴
     * newGreet1 과 newGreet2 는 서로 다른 생성자를 참조하고 있다.
     * newGreet1 은 MethodReferenceGreeting 에서 파라미터(String)가 있는 생성자를
     * newGreet2 는 MethodReferenceGreeting 에서 파라미터가 없는 생성자를 참조한다.
     */
    Function<String, MethodReferenceGreeting> newGreet1 = MethodReferenceGreeting::new;
    Supplier<MethodReferenceGreeting> newGreet2 = MethodReferenceGreeting::new;

    MethodReferenceGreeting keesun = newGreet1.apply("keesun");
    System.out.println("PRINT NAME : " + keesun.getNm());

    /**
     * 불특정 다수 인스턴스를 참조하는 방법
     */
    String[] names = {"jeon", "seung", "beom"};

    /**
     * Comparator 는 안의 내용을 구현하여 값을 어떻게 정렬할지 결정할 때 주로 사용
     * COmparator 는 람다식으로 표현할 수 있으며, 람다식에 메소드 레퍼런스를 적용한 것이
     * 아래의 모습이다.
     */
    Arrays.sort(names, String::compareToIgnoreCase);

  }
}
