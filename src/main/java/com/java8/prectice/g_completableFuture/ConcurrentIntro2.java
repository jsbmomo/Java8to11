package com.java8.prectice.g_completableFuture;

public class ConcurrentIntro2 {

  public static void main(String[] args) {
    System.out.println("Main: " + Thread.currentThread().getName()); // 현재 실행 중인 스레드

    // Thread 만들기 1 = 스레드 상속받기 (조금 불편한 방법)

  }


  class MyThread extends Thread {
    @Override
    public void run() {
      System.out.println("Hello: " + Thread.currentThread().getName());
    }
  }

}
