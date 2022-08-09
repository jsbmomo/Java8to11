package com.java8.prectice.g_completableFuture;


public class ConcurrentIntro {
	/**
	 * 동시에 여러 개의 애플리케이션을 사용하는 것도 Concurrent Sw
	 * ex) 웹 브라우저로 유튜브를 보면서 키보드로 문서에 타이핑을 할 수 있다.
	 *
	 * Concurrent 코딩을 지원한는 Java의 멀티프로세싱, 멀티쓰레드 (여기선 멀티쓰레드 만.)
	 * 
	 * Java에서 기존에 제공했던 Thread의 join, interrupt 등은 스레드의 개수가 많아지면 동작의 흐름이 복잡해진다.
	 * 이는 개발자가 직접 수십 개의 스레드를 코딩으로 관리하기는 어려운 상황을 만든다.
	 * 이러한 문제를 해결하기 위해 Executor라는 것이 나온다. 
	 * 또한 Executor를 익히면 Future, Completable Future를 차례로 익혀갈 수 있다. 
	 */
	public static void main(String[] args) throws InterruptedException {
		MyThread myThread = new MyThread();
		myThread.start();

		System.out.println("Hello: " + Thread.currentThread().getName());


		// 스레드 사용 방법 2. Thread runnable OR Lamda
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Thread Hello: " + Thread.currentThread().getName());
			}
		});
		thread.start();

		Thread threadLamda = new Thread(() -> {
			try {
				Thread.sleep(1000L); // 해당 스레드가 쉬는 동안 다른 스레드에게 우선권이 간다.
			} catch (InterruptedException e) { // 어떤 작업이 쉬고 있는 스레드를 깨울 경우 실행됨. = 깨우는 방법
				e.printStackTrace();
			}

			System.out.println("Thread Lamda: " + Thread.currentThread().getName());
		});
		threadLamda.start();
		System.out.println("Main : " + Thread.currentThread().getName());

		// Thread Interrupted
		System.out.println("======= Thread Interrupted =======");
		Thread threadTest = new Thread(() -> {
			// InterruptedException 시 스레드를 종료시키는 로직
			while (true) {
				System.out.println("Thread Loop: " + Thread.currentThread().getName());
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) { 
					System.out.println("Exit!");
					return ; // Runnable의 run 메서드는 return이 void 이지만, return을 하면 종료된다.
				}
			}
		});
		threadTest.start();

		Thread.sleep(3000L);
		threadTest.interrupt(); // interrupt는 스레드를 종료시키는 메소드가 아닌, 스레드를 깨우는 메소드


		// Thread Join
		Thread threadJoin = new Thread(() -> {
			System.out.println("Thread Join: " + Thread.currentThread().getName());
			try {
				Thread.sleep(3000L);
			} catch (InterruptedException e) {
				
			}
		});
		threadJoin.start();

		// Main 스레드가 threadJoin 안의 스레드를 기다리도록. 이를 통해 스레드가 끝날 때까지 기다림
		// 여기서 참고할 점은, 대기 중인 스레드를 Interrupt할(실행시킬) 경우, InterruptedException 발생한다.
		try {
			threadJoin.join(); 
		} catch (InterruptedException e) {
			System.out.println("Thread Interruption");
		}

		threadJoin.interrupt();
		System.out.println(threadJoin + " is finished.");
	}

	// 스레드 사용 방법 1. Thread 상속 후, run 사용
	static class MyThread extends Thread {
		@Override
		public void run() {
			System.out.println("Thread:  " + Thread.currentThread().getName());
		}
	}
}
