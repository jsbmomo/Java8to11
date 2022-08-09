import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorsStart {
	/**
	 * Thread나 Runnable 처럼 Row Level API를 다루는 것이 아닌 (물론 Runnable 사용은 한다.),
	 * Thread를 만들고 관리하는 작업을 고 수준의 API에게 위임한다.
	 * Executor는 우리가 Runnable에 해야할 일만 정의하면, 스레드 실행, 종료와 같은 일련의 작업을 대신 수행해준다.
	 * 
	 * Executor 인터페이스가 존재하지만, 대체로 ExecutorService 인터페이스를 사용하게 된다.
	 * ExecutorService는 Executor를 상속받은 인터페이스인데, Runnable 하나밖에 없는 Executor와 달리
	 * ExecutorService는 다양한 기능이 존재한다.
	 * 
	 * 또한, ExecutorService를 상속받은 ScheduledExecutorService 인터페이스가 존재하는데,
	 * schedule 메소드를 제공하여 특정 시간 이후에 작업을 실행시키거나 주기적으로 실행시킬 때 사용하는 인터페이스이다.
	 * 
	 * 여담으로 Fork/Join 프레임워크가 있는데, Fork/Join도 ExecutorService의 구현제이지만,
	 * 멀티 스레드가 아닌, 멀티 프로세싱 애플리케이션 구현에 유용하다.
	 */
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		// Runnable를 생성하는 고전적인 방법
		// executorService.execute(new Runnable() {
		// 	@Override
		// 	public void run() {
		// 		System.out.println("Thread: " + Thread.currentThread().getName());
		// 	}
		// });
		
		// executorService는 작업 수행 후, 다른 작업이 들어올 때까지 계속 대기.
		// 때문에 명시적으로 종료를 해야한다.
		executorService.submit(() -> {
			System.out.println("Thread: " + Thread.currentThread().getName());
		});

		// Graceful shutdown: 우아하게 죽인다 = 현재 진행중인 작업은 마치고 종료.
		executorService.shutdown(); 
		// executorService.shutdownNow(); // 실행중인 작업과 상관없이 즉시 종료


		// 스레드는 2개지만, 작업은 5개 할당
		// 스레드 풀의 개수를 초과하면, Blocking Queue에 차례로 쌓인 후 대기한다.
		ExecutorService executorService2 = Executors.newFixedThreadPool(2);
		executorService2.submit(getRunnable("Jeon"));
		executorService2.submit(getRunnable("Seung"));
		executorService2.submit(getRunnable("Beom"));
		executorService2.submit(getRunnable("Complete"));
		executorService2.submit(getRunnable("Java"));
		executorService2.shutdown();


		// Scheduled
		ScheduledExecutorService executorService3 = Executors.newSingleThreadScheduledExecutor();
		// executorService3.schedule(getRunnable("Timeout 3s"), 3, TimeUnit.SECONDS); // 3초 후 실행
		executorService3.scheduleAtFixedRate(getRunnable("Timeout 3s"), 3, 1, TimeUnit.SECONDS); // 3초후, 1초에 한 번 반복
		// scheduleAtFixedRate는 주기적으로 실행할 때 주로 사용하는데, 뒤에 shutdown이 있으면 
		// scheduleAtFixedRate 내의 InterruptedException으로 인해 shutdown을 만날경우 종료되어버린다.
		// executorService3.shutdown();
	}


	private static Runnable getRunnable(String message) {
		return () -> System.out.println(message + " - " + Thread.currentThread().getName());
	}

}
