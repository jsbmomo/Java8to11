package com.java8.prectice.b_interface_lambda;


public class MethodReferenceGreeting {

  private String nm;

  /**
   * 생성자를 호출할 때 Return 값은 객체의 타입.
   */
  public MethodReferenceGreeting() {

  }

  public MethodReferenceGreeting(String nm) {
    this.nm = nm;
  }

  public String getNm() {
    return nm;
  }

  public void setNm(String nm) {
    this.nm = nm;
  }

  public String hello(String nm) {
    return "hello " + nm;
  }

  public static String hi(String nm) {
    return "hi " + nm;
  }
}
