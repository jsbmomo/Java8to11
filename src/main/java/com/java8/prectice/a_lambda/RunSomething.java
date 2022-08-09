package com.java8.prectice.a_lambda;


/**
 * 함수형 인터페이스 예시
 * 추상 메서드가 1개 있다면 함수형 인터페이스라고 볼 수 있다.
 * 이 경우 아래와 같은 어노테이션을 붙여주기도 한다.
 * 만약, 함수형 인터페이스의 규칙을 위반하면 @functionalInterface에서 에러가 발생.
 *
 * @FunctionalInterface는 Java가 제공하는 Annotation으로, 함수형 인터페이스를 사용하려면
 * 붙여주는 것이 좋다.
 */
@FunctionalInterface
public interface RunSomething {

  void doIt(); // 추상메서드

  static void doItAgain() { // 인터페이스 임에도, 내부 구현 사용가능.
    System.out.println("Jeon SeungBeom");
  }

  default void printAge() {
    System.out.println("25");
  }

}
