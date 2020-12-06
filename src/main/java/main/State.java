package main;

import ai.data.GenerationEntity;
import game.Direction;
import game.Game;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javafx.animation.Animation.Status;
import javafx.animation.Timeline;

/**
 * This class is used to abstract game logic from the user interface. The game engine will provide
 * and push game objects to this instance, while the user interface will 'subscribe' to events by
 * implementing an according PropertyChangeListener.
 */
public class State {

  private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  private Timeline timeline;
  private GenerationEntity generationEntity;
  private Game game;
  private Direction direction;

  /**
   * A new game including mode-specific logic is injected as timeline. This method will take up the
   * timeline and add the required PropertyChangeListener.
   *
   * @param timeline the Timeline containing the mode-specific game
   */
  public void setTimeline(Timeline timeline) {
    this.timeline = timeline;
    this.timeline.statusProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == Status.STOPPED) {
        pcs.firePropertyChange("timeline", false, true);
      }
    });
  }

  /**
   * This method allows to check if the contained game is running.
   *
   * @return true, if the game is running
   */
  public boolean isTimelineRunning() {
    if (timeline == null) {
      return false;
    }
    return timeline.getStatus() == Status.RUNNING;
  }

  /**
   * This method allows to interrupt a running game.
   */
  public void stopTimeline() {
    if (timeline != null) {
      timeline.stop();
    }
  }

  /**
   * With this method, aggregated data of a machine learning batch can be injected. It may be used
   * to visualize statistics on the user interface.
   *
   * @param generationEntity the data wrapper class of the current generation
   */
  public void setGenerationEntity(GenerationEntity generationEntity) {
    this.generationEntity = generationEntity;
  }

  /**
   * This method allows to get the aggregated data of the current generation.
   *
   * @return statistics of the current generation
   */
  public GenerationEntity getGenerationEntity() {
    return generationEntity;
  }

  /**
   * This method allows to inject the current game-to-be visualized. Additionally, it will implement
   * a PropertyChangeListener, in order to inform the user interface a move was executed.
   *
   * @param game the game to be visualized
   */
  public void setGame(Game game) {
    this.game = game;
    this.game.addListener(e -> pcs.firePropertyChange("game", false, true));
    pcs.firePropertyChange("game", false, true);
  }

  /**
   * This method allows to get the currently visualized game.
   *
   * @return the current game
   */
  public Game getGame() {
    return game;
  }

  /**
   * This method injects the direction to-be-used for the next move. This implementation is
   * considered only for manual games.
   *
   * @param direction the next direction
   */
  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  /**
   * Provides the current direction for the next move.
   *
   * @return the direction to-go-to
   */
  public Direction getDirection() {
    return direction;
  }

  /**
   * This method allows to listen to property changes of the timeline. The event will fire, as soon
   * as a game over occurred.
   *
   * @param l the PropertyChangeListener for the timeline
   */
  public void addTimelineListener(PropertyChangeListener l) {
    pcs.addPropertyChangeListener("timeline", l);
  }

  /**
   * This method allows to listen to property changes of the game. The event will fire, as soon as a
   * game is injected to the state instance, as well as on every game tick.
   *
   * @param l the PropertyChangeListener for the game
   */
  public void addGameListener(PropertyChangeListener l) {
    pcs.addPropertyChangeListener("game", l);
  }
}
