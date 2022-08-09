package com.java8.prectice.a_lambda;

public class RunMain {

  public static void main(String[] args) {

    // Java 8 이전, 익명 내부 클래스를 주로 사용
    RunSomething runSomethingBefore = new RunSomething() {
      @Override
      public void doIt() {
        System.out.println("Anonymous Inner Class");
      }
    };

    // 익명 내부 클래스 anonymous inner class
    // intellij 는 람다식으로 자동 변경하는 기능 제공
    // 내부에 구현된 코드가 한 줄일 때,
    RunSomething runSomethingSimple = () -> System.out.println("HELLO");

    // 내부에 구현된 코드가 여러 줄일 때,
    RunSomething runSomething = () -> {
      System.out.println("HELLO");
      System.out.println("HELLO");
    };

    runSomethingBefore.printAge();
    runSomethingSimple.doIt();

    runSomething.doIt();
    runSomething.printAge();
    /**
     * 람다식을 사용하면, 마치 함수를 정의한 것처럼 보이긴 하지만,
     * 실질적으로는 JAVA 에서는 특수한 형태의 Object 라고 볼 수 있다.
     * (함수형 인터페이스를 인라인으로 구현한 Object)
     *
     * JAVA는 객체지향 언어기에 () -> System.out.println("HELLO") 부분을
     * runSomthingSimple 변수에 할당하거나, 변수의 parameter 에 할당하거나,
     * return 타입으로 return 할 수도 있다.
     *
     * 즉, Object(함수)를 return 또는 parameter 로 사용할 수 있다는 것이다.
     */

  }

  private static RunSomething getRunSomething(RunSomething runSomething) {
    return runSomething;
  }
}
