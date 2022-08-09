import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class TwiceCompletableFuture {
	/**
	 * 조금 깊게 공부하고 싶다면, ForkJoin 프레임워크와 Flow Api 공부를 추천
	 */
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
			System.out.println("Hello " + Thread.currentThread().getName());
			return "Hello";
		});
		
		// thenCompose 를 사용하면 뒤에 이어서 추가적인 작업을 수행할 수 있다.
		// 단, 아래의 방법은 두 개가 서로 연관관계가 있는 경우에 사용 가능하다.
		CompletableFuture<String> future = completableFuture.thenCompose(s -> getWorld(s));
		System.out.println(future.get());



		/* 아래의 경우 서로 연관관계가 없지만, 동시에 비동기적으로 실행이 가능하다. */
		// 애플 주식을 가져온다는 가정
		CompletableFuture<String> apple = CompletableFuture.supplyAsync(() -> {
			System.out.println("Apple: " + Thread.currentThread().getName());
			return "Apple";
		});
		// 마이크로소프트 주식을 가져온다는 가정 
		CompletableFuture<String> microsoft = CompletableFuture.supplyAsync(() -> {
			System.out.println("MicroSoft: " + Thread.currentThread().getName());
			return "Microsoft";
		});
		// KaKao 주식을 가져온다는 가정. 단 KaKao는 정수 return
		CompletableFuture<String> kakao = CompletableFuture.supplyAsync(() -> {
			System.out.println("KaKao: " + Thread.currentThread().getName());
			return "KaKao";
		});


		// 애플과 마이크로소프트 각각 요청을 보내고, 둘 다 결과가 도착했을 때, 별도의 작업을 수행하고 싶은 경우.
		// thenCombine 사용.
		CompletableFuture<String> result 
				= apple.thenCombine(microsoft, (a, m) -> a + " and " + m); // 입력 값 두 개, 결과 값 하나일 때, by function Lambda 사용
		System.out.println(result.get());


		// allOf 2개 이상의 작업일 때, 여러 작업을 모두 합쳐서 실행하는 방법
		// 모든 작업이 끝났을 때 해당 작업(thenAccept) 수행 
		// 단, 각 작업마다 반환값이 다르거나 에러가 있을 수도 있다. 이때 NULL이 출력된다. 
		CompletableFuture<Void> result2 = CompletableFuture.allOf(apple, microsoft)
				.thenAccept(System.out::println);
		System.out.println(result2.get());


		// 위에서 여러 작업이 서로 다른 값을 반환하거나, 에러가 있으면 NULL이 출력되었다.
		// 해당 문제를 해결하기 위해 아래의 방법을 사용 
		List<CompletableFuture<String>> futures = Arrays.asList(microsoft, kakao);
		CompletableFuture<String>[] futuresArray = futures.toArray(new CompletableFuture[futures.size()]);
		CompletableFuture<List<String>> result3 = CompletableFuture.allOf(futuresArray)
				.thenApply(v -> futures.stream()
									.map(CompletableFuture::join) // get()은 checkedException 필요하기에 Join 사용 
									.collect(Collectors.toList()));
		result3.get().forEach(System.out::println);
		

		// 가장 빨리 끝나는 작업의 결과값을 가져오기
		CompletableFuture<Void> result4 =
				CompletableFuture.anyOf(apple, microsoft, kakao).thenAccept(System.out::println);
		result4.get();


		/**
		 * 비동기 작업 중, 에러가 발생할 경우, exceptionally 사용 (예외처리) 
		 * 이외에도 .handle이 있는데, 정상적으로 동작했을 때 결과값과 에러가 발생했을 때 값을 동시에 받아
		 * 처리하는 방법도 있다. 
		 */
		boolean throwError = true;
		CompletableFuture<String> result5 = CompletableFuture.supplyAsync(() -> {
			if (throwError) {
				throw new IllegalArgumentException();
			}
			System.out.println("Exception : " + Thread.currentThread().getName());
			return "Exception Message";
		})
		.handle((value, ex) -> {
			if (ex != null) {
				System.out.println(ex);
				return "ERROR!!!";
			}
			return value;
		});
		// .exceptionally(ex -> {
		// 	return "Error Occur!!!";
		// });

		System.out.println(result5.get());
	}


	private static CompletableFuture<String> getWorld(String message) {
		return CompletableFuture.supplyAsync(() -> {
			System.out.println("World " + Thread.currentThread().getName());
			return "CompletableFuture With " + message;
		});
	}
}
