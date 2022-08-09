package com.java8.prectice.c_interface_change;

public class FooDefault implements Foo {

  String name;

  public FooDefault(String name) {
    this.name = name;
  }

  @Override
  public void printName() {
    System.out.print("Default Foo. => ");
    System.out.println(this.name);
  }

  @Override
  public String getName() {
    return this.name;
  }
}
