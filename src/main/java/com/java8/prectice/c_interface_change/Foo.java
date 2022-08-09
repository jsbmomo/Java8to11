package com.java8.prectice.c_interface_change;


/**
 * Q. 추후에 이미 구현한 인터페이스에 공통적으로 제공해주어야 하는 요구사항이 생기면 어떡할 것인가
 * A. 이미 구현한 인터페이스의 경우 여러 곳에서 사용하고 있다. 때문에, 해당 인터페이스를 사용하고
 *    있는 모든 Class에서 에러가 발생하게 된다. 이를 방지하고자 default를 사용하여 인터페이스 내에서
 *    특정 함수를 구현하는 방법을 사용한다. 실제 우리가 사용중인 Collection에서도 removeIf라는
 *    default 메서드를 사용하고 있다.
 *
 *    하지만, 인터페이스에 default 를 사용할 경우 이 기능이 항상 제대로 동작할 것이라는 보장이 없다.
 *    즉, 인터페이스 내부에 구현되어있기 때문에, 어떻게 구현되어 있는지 알기가 어렵다.
 *    때문에, 예외처리나 문서화를 잘 해야한다. 여기서는 ImplSpec를 사용했다.
 *
 *    참고로, 인터페이스 내부에 default로 구현한 메소드도 재정의가 가능하다.
 *    단, Object에서 기본적으로 제공하는 메소드는 재정의 할 수 없다. ex) toString 등등
 */

public interface Foo {

  void printName();

  /**
   * @implSpec 이 구현체는 getName()으로 가져온 문자를 대문자로 출력한다.
   */
  default void printNameUpperCase() {
    System.out.print("Interface Default Method. => ");
    System.out.println(getName().toUpperCase());
  }

//  Object Method! ERROR
//  default String toString() {
//
//  }

  String getName();
}
