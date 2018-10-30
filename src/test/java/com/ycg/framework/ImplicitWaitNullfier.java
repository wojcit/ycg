package com.ycg.framework;


import org.openqa.selenium.WebDriver;

import java.io.Closeable;

public class ImplicitWaitNullfier implements Closeable {
  private static ThreadLocal<Integer> localCount = ThreadLocal.withInitial(() -> 0);

  private final WebDriver driver;

  public ImplicitWaitNullfier(WebDriver driver) {
    this.driver = driver;
    WaitTool.nullifyImplicitWait(this.driver);
    increment();
  }

  @Override
  public void close() {
    if (decrement() == 0) {
      WaitTool.resetImplicitWait(this.driver);
    }
  }

  private static int increment() {
    Integer count = localCount.get();
    count++;
    localCount.set(count);
    return count;
  }

  private static int decrement() {
    Integer count = localCount.get();
    count--;
    localCount.set(count);
    return count;
  }
}