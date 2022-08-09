package com.java8.prectice.e_optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptionalApi {

  public static void main(String[] args) {
    List<OnlineClass> springClasses = new ArrayList<>();
    springClasses.add(new OnlineClass(1, "spring boot", true));
    springClasses.add(new OnlineClass(5, "Rest Api development", false));

    Optional<OnlineClass> spring = springClasses.stream()
        .filter((oc -> oc.getTitle().startsWith("spring")))
        .findFirst();

    boolean present = spring.isPresent(); // java11 부터 isEmpty() 도 지원
    System.out.println(present);

    /**
     * Optional의 값은 get()을 통해 꺼낼 수 있다. 하지만, 이 경우 값이 들어있으면 문제가 없지만,
     * 값이 없는(비어있는) 경우 RuntimeException 계열인 NoSuchElementException이 발생한다.
     * 때문에 항상 값이 있는지 확인하고 값을 꺼내야한다.
     * (Intellij 는 이 경우 항상 경고를 띄워줌)
     *
     * 가급적이면 get보다는 다른 api를 사용하는 것을 권고한다.
     * 아래와 같이 ifPresent 를 활용하여 데이터 존재 여부 확인 후, 데이터를 출력하는 방법도 있다.
     */

    // OnlineClass onlineClass = spring.get();
    spring.ifPresent(oc -> System.out.println(oc.getTitle()));
    // System.out.println(onlineClass.getTitle());


    /**
     * 아래의 코드는 위의 spring 변수의 값에 따라 다른 결과를 출력한다.
     * 현재 spring 변수는 "jpa"라는 값의 존재여부를 파악하고 있다.
     * 이때, "jpa"라는 값이 존재하지 않을 경우(optional == false) createNewClass를 호출하여
     * 새로운 OnlineClass 클래스를 생성하고 반환한다.
     * .orElse : 상수로 이미 만들어져잇는 경우
     * .orElseGet : 동적으로 작업을 하여 만들경우
     * .orElseThorw : 값이 없을 때 에러를 발생시킬 경우
     */
    OnlineClass onlineClass = spring.orElseGet(OptionalApi::createNewClass);
    System.out.println(onlineClass.getTitle());
  }


  private static OnlineClass createNewClass() {
    return new OnlineClass(10, "New Class", false);
  }

}
