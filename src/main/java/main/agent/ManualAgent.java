package main.agent;

import game.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ManualAgent extends Agent {

  private Game game;

  @Override
  public void build() {
    super.build();
    state.setDirection(config.getInitialDirection());
    game = new Game();
    state.setGame(game);

    game.onGameOver(this::stopTimer);

    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
      game.changeDirection(state.getDirection());
      game.onTick();

    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    state.setTimeline(timeline);
    timeline.play();
  }

}
