//package main;
//
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.util.Duration;
//import org.junit.Test;
//
//public class StateTest {
//
//  @Test
//  public void StateIntegrationTest() {    // TODO: remove or leave as documentation to show we tried?
////    State state = new State();
////    state.addGameListener(e -> {
////      System.out.println("---------> onTick");
////    });
////    state.addTimelineListener(e -> System.out.println("---------> gameOver"));
////    ITestConfig.getInstance().setMode(Mode.MANUAL);
////    ITestConfig.getInstance().getMode().getAgent().setState(state).setSpeed(100).build();
////    while (state.isTimelineRunning()) {
////      System.out.println("running");
////    }
//
//    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
//      System.out.println("running");
//    }));
//    timeline.setCycleCount(Animation.INDEFINITE);
////    timeline.play();    // fails, because there is no Application Thread
//  }
//}
