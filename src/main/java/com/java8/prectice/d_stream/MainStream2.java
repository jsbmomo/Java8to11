package com.java8.prectice.d_stream;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStream2 {

  public static void main(String[] args) {
    List<OnlineClass> springClass = new ArrayList<>();
    springClass.add(new OnlineClass(1, "spring boot ", true));
    springClass.add(new OnlineClass(2, "spring data jpa ", false));
    springClass.add(new OnlineClass(3, "spring mvc", true));
    springClass.add(new OnlineClass(4, "spring core ", false));
    springClass.add(new OnlineClass(5, "rest api development ", true));

    List<OnlineClass> javaClasses = new ArrayList<>();
    javaClasses.add(new OnlineClass(6, "The Java, Test", false));
    javaClasses.add(new OnlineClass(7, "The Java, Code manipulation", true));
    javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));

    List<List<OnlineClass>> jeonEvents = new ArrayList<>();
    jeonEvents.add(springClass);
    jeonEvents.add(javaClasses);

    /**
     * forEach 는 아무것도 리턴하지 않는다(void).
     * Stream 을 Return 하지 않는 것은 모두 종료 Operator 이다.
     */
    System.out.println("[ Spring으로 시작하는 수업 목록만 간추리기 ]");
    springClass.stream().filter(oc -> oc.getTitle().startsWith("spring"))
                        .forEach(oc -> System.out.println(oc.getId()));

    /**
     * cloase 되지 않았다는 것은 true 나 false 를 return 하면 된다.
     * .filter(oc -> !oc.isClosed()) 를 사용할 수도 있지만, Predicate 를 통해 문제를 해결할 수도 있다.
     */
    System.out.println("[ close 되지않은 수업 ]");
    springClass.stream()
              .filter(Predicate.not(OnlineClass::isClosed))
              .forEach(oc -> System.out.println(oc.getId()));

    /**
     * .map 에서 String 형태로 변환함.
     */
    System.out.println("[ 수업 이름만 모아서 스트림 만들기 ] ");
    springClass.stream()
              .map(OnlineClass::getTitle)
              .forEach(System.out::println);

    /**
     * flatMap 을 통해 list 를 받고, 그것을 stream 으로 바꿔주면
     * flating 시킬 수 있다.
     * .flatMap(list -> list.stream()) 은 아래와 같이 바꿀 수 있다.
     * .flatMap(Collection::stream)
     *
     * Operator 에 오는 현재 타입이 무엇인지 항상 잘 알고 있어야 한다.
     */
    System.out.println("[ 두 수업 목록에 들어있는 모든 수업 아이디 출력 ]");
    jeonEvents.stream().flatMap(Collection::stream)
              .forEach(oc -> System.out.println(oc.getId()));

    System.out.println("[ 10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만 ]");
    Stream.iterate(10, i -> i + 1)
          .skip(10)
          .limit(10)
          .forEach(System.out::println);

    System.out.println("[ 자바 수업 중에 Test 가 들어있는 수업이 있는지 확인 ]");
    boolean test = javaClasses.stream().anyMatch(oc -> oc.getTitle().contains("test"));
    System.out.println("Test : " + test);

    System.out.println("[ 스프링 수업 중에서 제목에 spring이 들어간 제목만 모아서 List로 만들기 ]");
    List<String> spring = springClass.stream().filter(oc -> oc.getTitle().contains("spring"))
                                    .map(OnlineClass::getTitle)
                                    .collect(Collectors.toList());
    spring.forEach(System.out::println);
  }
}
