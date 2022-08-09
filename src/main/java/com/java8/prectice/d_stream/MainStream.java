package com.java8.prectice.d_stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JAVA 8 에서 새롭게 생성된 기능이다.
 * Stream API 는 우리가 가지고 있는 Collection 이나 연속된 데이터를 처리하는
 * Operation 의 모음이라고 볼 수 있다.
 *
 * Stream 을 사용하는 이유 중 하나는 받은 데이터를 점점 잘게 쪼개면서 처리하기 위함이다.
 * 또한, Functional 하나는 장점을 가지고 있는데, 여기서 Functional 하다는 의미는
 * 소스를 변경하지 않는다는 것이다.
 */
public class MainStream {

  public static void main(String[] args) {
    List<String> names = new ArrayList<>();
    names.add("jeon");
    names.add("seung");
    names.add("beom");
    names.add("fight");

    // 아래의 결과는 Stream 형태의 리스트가 반환된다. 즉, 전달받은 데이터 자체를
    // 변경하는 것이 아니라, 데이터를 새롭게 만든다.
    Stream<String> strStream = names.stream().map(String::toUpperCase);

    /**
     * 아래의 코드는 출력되지 않는다. 근본적으로 중계형 Operator 들은
     * Terminal Operator 에 오기 전까지 실행을 하지 않는다.
     * 이것은 단순히 String 파이프 라인을 정의를 한 것 뿐이다
     * .
     * 중계형 Operator 의 특징 중 하나는 여러 개가 올 수 있으나, 항상 종료형
     * Operator 가 마지막에 와야한다는 것이다.
     * 즉, 아래의 코드가 Console에 출력되지 않은 이유는 종료형 Operator 가 없어서이다.
     * 종료형 Operator 가 없기 때문에 아예 실행조차 하지 않는다.
     *
     * 아래의 코드를 B와 같이 수정하면 정상적으로 동작한다.
     */
    names.stream().map((s) -> {
      System.out.print(s + " ");
      return s.toUpperCase();
    });

    /**
     * B
     * .collect(Collectors.toList()) <== 종료형 Operator 예시
     */
    List<String> collect = names.stream().map((s) -> {
      System.out.println(s + " ");
      return s.toUpperCase();
    }).collect(Collectors.toList());
    collect.forEach(System.out::println);

    System.out.println("==============");
    names.forEach(System.out::println);

    System.out.println("-< Stream 병열 처리 >-");

    // 기존 ==> loop 를 돌면서 병렬적으로 처리하기 어렵다.
    for (String name : names) {
      if (name.startsWith("j")) {
        System.out.println(name.toUpperCase());
      }
    }

    // stream ==> parallelStream() 을 사용하면 JVM 이 알아서 병렬로 처리
    // parallelStream() 뒤에 오는 Operator 들은 자동으로 병렬처리가 된다.
    // 아래의 코드를 실행하면 ForkJoinPool 를 사용하여 병렬처리가 되고 있다는 것을
    // 확인할 수 있다.
    List<String> parall = names.parallelStream().map((s) -> {
      System.out.println(s + "  " + Thread.currentThread().getName());
      return s.toUpperCase();
    }).collect(Collectors.toList());

    parall.forEach(System.out::println);

    /**
     * 주의할 점은 parallelStream()을 사용한다고 무조건 빨라진다는 것이 아니라는 것이다.
     * 오히려 느려질 수가 있다. 병렬 처리가 다 좋다면, Reactive 는 등장할 이유가 없다.
     *
     * 스레드를 만든다는 것은 분명히 비용이 드는 작업이다.
     * 스레드를 만들고, 병렬처리를 위해 수집하고, 스레드 간에 왔다갔다하는 Context Switch 비용 등
     * 이런 것들이 한 스레드에서 처리하는 것보다 느리게 동작하는 원인이 될 수 있다.
     *
     * 다만, parallelStream()이 정말 유용할 때는 데이터가 방대하게 큰 경우이기에
     * 대부분의 경우는 Stream 을 사용해도 무방하다. 뿐만아니라, 처리하는 내용마다 parallelStream()이
     * 유용할 때가 있으므로, 케이스마다 한번씩 parallelStream()과 stream() 각각에 대한 성능체크를 해보는 방법 밖에 없다.
     */

  }
}
