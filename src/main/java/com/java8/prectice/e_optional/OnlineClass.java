package com.java8.prectice.e_optional;


import java.util.Optional;

public class OnlineClass {

  private Integer id;
  private String title;
  private boolean closed;
  public Progress progress;

  public OnlineClass(Integer id, String title, boolean closed) {
    this.id = id;
    this.title = title;
    this.closed = closed;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isClosed() {
    return closed;
  }

  public void setClosed(boolean closed) {
    this.closed = closed;
  }

  /**
   * 자바는 에러가 발생하면 StackTrace 를 찍는다.
   * 즉, 에러가 발생하기 전까지의 Call Stack 를 거쳐서 에러가 발생했는지에 대한
   * 정보를 담게되는데 이 자체로 리소스를 사용하게 되므로 로직을 처리할 때 에러를
   * 사용하는 것은 좋은 습관이 아니다.
   * @return
   */
//  public Progress getProgress() {
//    if (this.progress == null) {
//      throw new IllegalStateException();
//    }
//    return progress;
//  }

  public Optional<Progress> getProgress() {
    return Optional.ofNullable(progress);
  }

  public void setProgress(Progress progress) {
    this.progress = progress;
  }
}
