import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class StartCompletableFuture {
	/**
	 * CompletableFuture를 사용하면 비동기 프로그래밍이 가능해진다.
	 * 이전의 Future 방식은 예외처리를 지원하지 않고, 여러 Future를 조합하는 것도 어렵다.
	 * 또한, future의 get은 블록킹 콜이기에, future의 값을 가져와야하는 경우 get 뒤에 로직이 작성되어야 한다.
	 * 
	 * CompletableFuture는 Future도 구현하고 있고, CompletionStage 라는 인터페이스도 제공하는데,
	 * 몇 초 이내 응답이 안오면 캐시해둔 값을 리턴할 때 유용하며, 
	 * CompletableFuture를 사용하면 return을 하거나, Executor를 사용하지 않더라도 
	 * CompletableFuture를 활용한 비동기 작업을 수행할 수 있다.
	 * 
	 * 그런데 아래의 CompletableFuture를 사용해보면 별도의 Thread Pool를 생성하지 않고도,
	 * 사용이 가능한데, 그 이유는 ForkJoinPool 때문이다.
	 * 
	 * ForkJoinPool은 Java7에 추가된 것으로 Executor의 구현체 중 하나이다.
	 * 다만, 작업을 Dequeue(맨 마지막에 들어온 것이 먼저 나가는 형태)를 사용하여 가지고 있는 
	 * Thread가 할 일이 없다면 Thread가 직접 Dequeue로부터 할 일을 가져와서 처리한다.
	 * 또한, 자신이 파생시킨 sub task가 있다면, 파생된 sub task를 쪼개고, 다른 Thread에 분산시켜 
	 * 작업을 처리한다. 이후 처리된 작업을 모아서(Join) 결과 값을 도출하는 것이 ForkJoin Framework이다.
	 * 
	 * 결론은 우리가 별다른 Executor를 사용하지 않아도 내부적으로 ForkJoinPool에 있는 CommonPool를 사용한다는 것이다.
	 * 하지만, 우리가 원하면 스레드를 만들어서 할당할 수도 있다.
	 * 예시로 supplyAsync를 사용할 때 두 번째 인자로 주는 방법이 있다.
	 * 또한, thenAsync, thenRunAsync 등 다양한 메소드도 두 번째 인자를 제공하여 스레드의 개수를 정하는 방법이 있다.
	 */
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		// ExecutorService executorService = Executors.newFixedThreadPool(4);
		// Future<String> future = executorService.submit(() -> "Hello");
		
		// future.get();

		CompletableFuture<String> completableFuture = new CompletableFuture<>();
		completableFuture.complete("Jeon"); // Future의 기본값을 설정 
		System.out.println(completableFuture.get());

		// static factory 방법
		CompletableFuture<String> completableFuture2 = CompletableFuture.completedFuture("SeungBeom");
		System.out.println(completableFuture2.get());

		// return 이 없는 경우 (단, 이경우 제네릭 타입이 void)
		CompletableFuture<Void> completableFuture3 = CompletableFuture.runAsync(() -> {
			System.out.println("RunAsync: " + Thread.currentThread().getName());
		});
		completableFuture3.get(); // get를 해야 runAsync안의 내용이 실제로 실행

		// return 이 있는 경우
		CompletableFuture<String> completableFuture4 = CompletableFuture.supplyAsync(() -> {
			System.out.println("SupplyAsync: " + Thread.currentThread().getName());
			return "SupplyAsync";
		});
		System.out.println(completableFuture4.get()); // get을 해야 안의 내용이 실행 


		/**
		 * Callback 방법, 결과값을 .thenApply를 통해 반환
		 */
		CompletableFuture<String> completableFuture5 = CompletableFuture.supplyAsync(() -> {
			System.out.println("SupplyAsync: " + Thread.currentThread().getName());
			return "SupplyAsync";
		}).thenApply((s) -> {
			System.out.println(Thread.currentThread().getName());
			return s.toUpperCase();
		});
		// .thenAccept((s) -> { // return 할 필요가 없을 때 thenAccept 사용 
		// 	System.out.println(s);
		// });
		// .thenRun(() -> { // runnable과 같은 방식, 온전히 실행만 수행 
		// 	System.out.println(s);
		// })

		System.out.println(completableFuture5.get());


		// 스레드 직접 할당하기
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		CompletableFuture<Void> completableFuture6 = CompletableFuture.supplyAsync(() -> {
			System.out.println("CompletableFuture: " + Thread.currentThread().getName());
			return "CompletableFuture SupplyAsync With thenRun.";
		}, executorService).thenRunAsync(() -> {
			System.out.println(Thread.currentThread().getName());
		}, executorService);

		completableFuture6.get();
		
		executorService.shutdown(); // Executor를 사용하여 shutdown 필요.
	}
}
