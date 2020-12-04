package main.agent;

import game.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.State;

public class ManualAgent extends Agent {

  private Game game;

  public ManualAgent(State state) {
    super(state);
    game = state.getGame();
    game.onGameOver(this::stopTimer);
  }

  @Override
  public Timeline startTimeline(int speed) {
    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {

      game.changeDirection(state.getDirection());
      game.onTick();

    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
    return timeline;
  }

}
