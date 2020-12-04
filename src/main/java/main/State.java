package main;

import ai.data.GenerationEntity;
import game.Direction;
import game.Game;
import main.configuration.Mode;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class State {

  private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  private GenerationEntity generationEntity;
  private Game game;
  private Direction direction;

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

  public void addGameListener(PropertyChangeListener l) {
    pcs.addPropertyChangeListener("game", l);
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Direction getDirection() {
    return direction;
  }
}
