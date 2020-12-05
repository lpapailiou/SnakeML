package main;

import ai.data.GenerationEntity;
import game.Direction;
import game.Game;
import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class State {

  private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  private Timeline timeline;
  private GenerationEntity generationEntity;
  private Game game;
  private Direction direction;

  public void setTimeline(Timeline timeline) {
    this.timeline = timeline;
    this.timeline.statusProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == Status.STOPPED) {
        pcs.firePropertyChange("timeline", false, true);
      }
    });
  }

  public boolean isTimelineRunning() {
    if (timeline == null) {
      return false;
    }
    return timeline.getStatus() == Status.RUNNING;
  }

  public void stopTimeline() {
    if (timeline != null) {
      timeline.stop();
    }
  }

  public void setGenerationEntity(GenerationEntity generationEntity) {
      this.generationEntity = generationEntity;
  }

  public GenerationEntity getGenerationEntity() {
    return generationEntity;
  }

  public void setGame(Game game) {
      this.game = game;
      this.game.addListener(e -> pcs.firePropertyChange("game", false, true));
      pcs.firePropertyChange("game", false, true);
  }

  public Game getGame() {
    return game;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Direction getDirection() {
    return direction;
  }

  public void addTimelineListener(PropertyChangeListener l) {
    pcs.addPropertyChangeListener("timeline", l);
  }

  public void addGameListener(PropertyChangeListener l) {
    pcs.addPropertyChangeListener("game", l);
  }
}
