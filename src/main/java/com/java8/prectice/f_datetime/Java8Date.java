package com.java8.prectice.f_datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Java8Date {

  public static void main(String[] args) {
    /** 메소드 실행 시간을 비교할 때 유용하다. */
    Instant instant = Instant.now(); // 현재시간을 기계시간으로 출력
    System.out.println("Instant: " + instant); // 기준시  UTC, GMT (둘 다 값이 같다. 이름만 다름)
    System.out.println("Instant: " + instant.atZone(ZoneId.of("UTC")));

    ZoneId zone = ZoneId.systemDefault();
    System.out.println("ZonedDateTime: " + zone);
    ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault()); // 시스템 기준 시간
    System.out.println("ZonedDateTime: " + zonedDateTime);


    /** 사람용, 시스템 기준 시간을 가져오기에 한국에서 개발하고 미국 서버에 배포하면 시스템 시간이 바뀜 */
    LocalDateTime now = LocalDateTime.now(); // 현재 시스템 zone 시간을 참조해서 값을 가져옴.
    System.out.println("LocalDateTime: " + now);
    LocalDateTime birthDay =
          LocalDateTime.of(1997, Month.NOVEMBER, 11, 11, 11);
    System.out.println("LocalDateTime: " + birthDay);

    /** 특정 Zone 의 시간을 보고 싶을 때 */
    ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    System.out.println("ZonedDateTime: " + nowInKorea);
    ZonedDateTime nowInKoreaWithInstant = instant.atZone(ZoneId.of("Asia/Seoul"));
    System.out.println("ZonedDateTime: " + nowInKoreaWithInstant);


    /** 기간을 비교하는 방법 */
    LocalDate today = LocalDate.now(); // 예시, 현재 날짜부터 내 생일까지 일 수
    LocalDate thisYearBirthday = LocalDate.of(2022, Month.NOVEMBER, 11);
    Period period = Period.between(today, thisYearBirthday);
    Period until = today.until(thisYearBirthday);
    System.out.println("Period: " + period.getDays());
    /* get 의 매개변수가 TemporalUnit 라고 되어있지만 ChronoUnit 를 사용해야 한다. */
    System.out.println("Period: " + until.get(ChronoUnit.DAYS));


    /** Period 가 사람용이라면, Duration 은 기계용 시간을 비교한다고 생각하면 좋을 듯.  */
    Instant nowInstant = Instant.now();
    Instant plusInstant = nowInstant.plus(10, ChronoUnit.SECONDS);
    Duration between = Duration.between(nowInstant, plusInstant);
    System.out.println("Duration: " + between.getSeconds());


    /** Application 에서 사용하기 쉬운 형태를 출력할 때 유용, 특정 포멧으로 변환할 때 역시 유용 (기본적을 지원하는 포멧이 많음) */
    LocalDateTime nowLocal = LocalDateTime.now();
    DateTimeFormatter mmddyyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    System.out.println("DateTimeFormatter: " + nowLocal.format(mmddyyyy));

    LocalDate parse = LocalDate.parse("11/11/1997", mmddyyyy);
    System.out.println("LocalDate: " + parse);


    /** Legacy api 지원 */
    Date date = new Date();
    Instant instantWithLegacy = date.toInstant();
    Date newDate = Date.from(instantWithLegacy); // instant를 통한 date 만들기

    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    // instant 로 바꿀 수 있으면 뭐든 최신 API 로 바꿀 수 있다.
//    LocalDateTime dateTimeWithCanlendar =
//          gregorianCalendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    ZonedDateTime dateTimeWithCanlendar = gregorianCalendar.toInstant().atZone(ZoneId.systemDefault());
    GregorianCalendar from = GregorianCalendar.from(dateTimeWithCanlendar);

    ZoneId zoneId = TimeZone.getTimeZone("PST").toZoneId(); // TimeZone 도 예전 API
    TimeZone timeZone = TimeZone.getTimeZone(zoneId);

  }
}
