package com.java8.prectice.e_optional;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Optional 은 Java8에 새롭게 추가된 인터페이스.
 * 비어있을 수도 있고, 무언가를 담고 있을 수도 있는 컨테이너.
 *
 */
public class OptionalStart {

  public static void main(String[] args) {
    List<OnlineClass> springClasses = new ArrayList<>();
    springClasses.add(new OnlineClass(1, "spring boot", true));
    springClasses.add(new OnlineClass(2, "spring data jpa", true));
    springClasses.add(new OnlineClass(3, "spring mvc", false));
    springClasses.add(new OnlineClass(4, "spring core", false));
    springClasses.add(new OnlineClass(5, "rest api development", true));

    // [ 아래의 코드는 Progress 가 NULL 이기 때문에, NullPointException 발생 ]

    // OnlineClass spring_boot = new OnlineClass(1, "spring boot", true);
    // Duration studyDuration = spring_boot.getProgress().getStudyDuration();
    // System.out.println(studyDuration);


    // 아래와 같은 코드는 에러를 만들기 좋은 코드이다. 왜냐하면 if (progress != null) 를 깜빡할 수 있기 때문.
    // 즉, 매번 NULL 체크를 반환하는지 확인해야하는 것이 문제다.
    // 추가로 NULL를 Return 하는 것 역시 문제다. Java 8 이전에는 별다른 대안이 없었지만, Optional 를 통해
    // NULL 를 Return 하는 것에 대한 문제를 해결할 수 있게 되었다.

    // OnlineClass springBoot = new OnlineClass(1, "spring boot", true);
    // Progress progress = springBoot.getProgress();
    // if (progress != null) {
    //  System.out.println(progress.getStudyDuration());
    // }


  }
}
