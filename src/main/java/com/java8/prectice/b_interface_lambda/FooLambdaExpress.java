package com.java8.prectice.b_interface_lambda;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class FooLambdaExpress {

  public static void main(String[] args) {
    FooLambdaExpress foo = new FooLambdaExpress();
    foo.run();
  }

  private void run() {
    final int baseNumber = 10; // final 값 변경 불가

    /** 로컬 클래스 */
    class LocalClass {
      void printBaseNumber() {
        int baseNumber = 11;
        System.out.println(baseNumber);
      }
    }

    /** 익명 클래스 */
    Consumer<Integer> integerConsumer = new Consumer<Integer>() {
      @Override
      public void accept(Integer integer) {
        int baseNumber = 12;
        System.out.println(baseNumber);
      }
    };

    /** 람다 활용 */
    IntConsumer printInt = (i) -> {
      // int baseNumber = 13; // "baseNumber" already defined in the scope
      System.out.println(i + baseNumber);
    };
    printInt.accept(10);
  }

  /**
   * 위의 3가지 방법의 공통점은 baseNumber 를 참조할 수 있다는 것이다.
   * 또한, 모두 FooLambdaExpress 안의 run 메서드 안에 있기도 하다.
   * 여기서 람다와 위의 2가지 클래스가 다른 점은 로컬, 익명 클래스는 쉐도잉이 가능하지만
   * 람다는 쉐도잉이 불가능하다. 이 말은 위의 코드를 보면 알 수 있는데 익명, 로컬 클래스는
   * run 메서드 내에 선언된 baseNumber 변수를 사용할 수 있으나, 람다 내부에는 baseNumber 사용이
   * 불가하다. 즉, scope 에 차이가 있는 것이다.
   */

}
