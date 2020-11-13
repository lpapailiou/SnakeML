package webserver;

import java.util.List;

public class ItemHolder {
    private static List<String> item;
    public synchronized void produce(List<String> item) {
      ItemHolder.item = item;
      notify();
    }
    public synchronized List<String> consume() {
      while (item == null) {
        try {
          wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      return item;
    }
  }


