package webserver;


import ai.GameBatch;

class ItemHolder {
    private static GameBatch item;
    public synchronized void produce(GameBatch item) {
      ItemHolder.item = item;
      notify();
    }
    public synchronized GameBatch consume() {
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


