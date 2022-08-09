package com.java8.prectice.f_datetime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Java8Before {

  public static void main(String[] args) {
    /**
     * Java 8 이전에 사용하던 날짜 관련 메소드
     * 이름이 잘 작명되어있지 않다. Date 는 Date 라고 되어있지만, 시간과 TimeStamp 도 나타낼 수 있다.
     * 또한 버그 발생할 여지가 많다...
     * Date 는 Thread Safe 하지 않고,
     * Calendar 는 Type Safe 하지 않고,
     */
    Date date = new Date();
    long time = date.getTime(); // 날짜에서 시간을 가져와? 가져온 시간도 1970년 1월 1일 기준으로 ms?

    // Calendar 는 month 가 0부터 시작하기에, Calendar 에서 제공하는 상수를 사용해야 한다. (ex. Calendar.July)
    // 게다가 Calendar 의 매개변수는 int 이기에 마이너스도 받을 수 있다는 문제가 있다.
    Calendar calendar = new GregorianCalendar(1997, 11, 11); // intellij 는 바로 버그를 조심하라고 주의를 준다.
    SimpleDateFormat dateFormat = new SimpleDateFormat();

  }
}
