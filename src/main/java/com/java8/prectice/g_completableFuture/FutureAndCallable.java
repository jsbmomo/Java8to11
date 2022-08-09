import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureAndCallable {

	/**
	 * Callable은 void를 반환하는 Runnable과 달리, 어떤 결과를 return할 수 있다.
	 */
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		// 싱글 스레드를 사용하면 아래에서 Future, Callable 등을 사용하더라도, 원하는대로 결과가 안나올 수도 있음.
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		// Callable<String> callable = new Callable<String>() {
		// 	@Override
		// 	public String call() throws Exception {
		// 		return null;
		// 	}
		// };
		Callable<String> callable = () -> {
			Thread.sleep(2000L);
			return "Callable Return";
		};

		// 이전에 사용한 runnable submit도 Future로 반환받을 수 있었다.
		// 단, Future로 받은 값은 get()으로 꺼낼 수 있지만, get()를 사용할 경우,
		// 값을 불러오거나 작업을 완료할 때까지 기다린 후 뒤의 작업을 수행한다.
		// 때문에, 결과값을 가져올 때까지 대기하는 이러한 형태를 블록킹 콜이라고 한다.
		Future<String> submit = executorService.submit(callable);
		
		System.out.println(submit.isDone()); // get() 작업이 끝났는지 확인할 수 있다.
		System.out.println("START");
		
		// 작업을 취소시킬지 결정할 수 있다. true = 취소, false = 계속
		// cancel를 사용하면 값을 가져올 수 없다는 특징이 있다.
		// 때문에 cancel 뒤에 사용되는 get은 CancellationException이 발생한다. = 취소시켰기 때문.
		// submit.cancel(true); 
		submit.get(); // 블록킹

		System.out.println("END");
		System.out.println(submit.isDone());

		executorService.shutdown();


		// Callable을 활용해서 여러 개의 작업을 뭉쳐서 주는 방법.
		/**
		 * 밑의 코드를 살펴보면, 3개의 Callable에서 값을 동시에 가져오거나, 가장 빨리 작업을 끝낸 값만 가져오는 코드가 있습니다.
		 * 이때, 가장 작업을 빨리 끝낸 곳에서 값을 가져올 때(invokeAny)는 
		 * 스레드의 개수에 영향받아 만약 작업의 개수보다 스레드의 수가 적을 경우, 
		 * 가장 먼저 들어간 call1의 결과를 가져온다.
		 */
		ExecutorService executorService2 = Executors.newFixedThreadPool(4);
		Callable<String> call1 = () -> {
			Thread.sleep(2000L);
			return "Callable 1";
		};
		Callable<String> call2 = () -> {
			Thread.sleep(3000L);
			return "Callable 2";
		};
		Callable<String> call3 = () -> {
			Thread.sleep(1000L);
			return "Callable 3";
		};

		// 3개의 Callable를 하나의 Collection으로 반환
		// invokeAll은 3개의 작업이 모두 끝날 때까지 기다림.
		// 만약, 내가 가진 주식들의 총 가격을 보려면 이러한 기능이 필요함.
		List<Future<String>> futures = executorService2.invokeAll(Arrays.asList(call1, call2, call3));
		for (Future<String> f : futures) {
			System.out.println(f.get());
		}

	
		// 반면에, 3대의 서버에 같은 파일을 각각 복사 후, 그 같은 파일을 3개의 서버에서 다시 가져오는 경우라면?
		// 3대의 서버로부터 같은 파일을 가져오는데 기다릴 필요가 있을까.
		// 가장 빨리 받을 수 있는 서버로부터만 받아오면 된다. 이를 위한 메소드가 invokeAny다.
		// invokeAny는 블록킹 콜이기에 바로 결과가 타입으로 나온다.
		String invokeAnyStr = executorService2.invokeAny(Arrays.asList(call1, call2, call3));
		System.out.println(invokeAnyStr);


		executorService2.shutdown();
	}
}
