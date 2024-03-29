package ui.painter.impl;

import game.Direction;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.configuration.IPainterConfigReader;
import main.configuration.Theme;
import ui.painter.INetworkPainter;

/**
 * This is a helper class which isolates the visualization of the NeuralNetwork on the user
 * interface.
 */
public class NetworkPainter implements INetworkPainter {

  private IPainterConfigReader config = IPainterConfigReader.getInstance();
  private Theme colors = config.getTheme();
  private GraphicsContext context;
  private int width = 520;
  private int height = 290;
  private double correctionOffsetX = 0.8;
  private double correctionOffsetY = 1.9;
  private int radius = 16;
  List<List<GraphNode>> graph = new ArrayList<>();

  /**
   * The constructor is designed to store date which should not be refreshed during a game batch is
   * run.
   *
   * @param context                the graphics context of the panel to be painted on
   * @param networkConfiguration   the current architecture of the NeuralNetwork
   * @param inputNodeConfiguration the currently selected input nodes of the NeuralNetwork
   */
  public NetworkPainter(GraphicsContext context, List<Integer> networkConfiguration,
      VBox inputNodeConfiguration) {
    this.context = context;
    int inputNodes = inputNodeConfiguration.getChildren().size();
    if (inputNodes > 0) {
      networkConfiguration.set(0, inputNodes);
      initializeNetwork(networkConfiguration, inputNodeConfiguration);
    }
  }

  private void initializeNetwork(List<Integer> networkConfiguration, VBox inputNodeConfiguration) {
    graph.clear();
    int w = width / networkConfiguration.size();
    for (int i = 0; i < networkConfiguration.size(); i++) {
      List<GraphNode> layer = new ArrayList<>();
      int h = height / networkConfiguration.get(i);
      int hOffset = (height - ((networkConfiguration.get(i) - 1) * h) - 20) / 2;
      for (int j = 0; j < networkConfiguration.get(i); j++) {
        int offset = 0;
        GraphNode node = new GraphNode((w * i) + offset, (h * j) + hOffset);
        layer.add(node);
        if (i == 0) {
          RadioButton box = (RadioButton) inputNodeConfiguration.getChildren().get(j);
          if (!box.isSelected()) {
            node.active = false;
          }
        }
      }
      graph.add(layer);
    }
  }

  /**
   * This method triggers the visualisation of the neural network.
   */
  @Override
  public void paintNetwork() {
    paintBackground();
    paintLines();
    paintDots();
  }

  private void paintLines() {
    for (int i = 1; i < graph.size(); i++) {
      cross(graph.get(i), graph.get(i - 1));
    }
  }

  private void cross(List<GraphNode> layer1, List<GraphNode> layer2) {
    for (GraphNode a : layer1) {
      for (GraphNode b : layer2) {
        if (a.active && b.active) {
          paintLine(a, b);
        }
      }
    }
  }

  /**
   * This method will trigger the output nodes of the visual representation of the NeuralNetwork to
   * flash, according to the predicted direction.
   *
   * @param flash the ordinal of the input node to be highlighted
   */
  public void flashOutput(int flash) {
    for (int i = 0; i < graph.get(graph.size() - 1).size(); i++) {
      GraphNode node = graph.get(graph.size() - 1).get(i);
      if (i == flash) {
        paintDot(node.x, node.y, radius, colors.getFoodColor());
      } else {
        paintDot(node.x, node.y, radius);
      }
    }
  }

  private void paintDots() {
    for (int i = 0; i < graph.size(); i++) {
      for (int j = 0; j < graph.get(i).size(); j++) {
        GraphNode node = graph.get(i).get(j);
        if (node.active) {
          paintDot(node.x, node.y, radius);
          if (i == graph.size() - 1) {
            context.setLineWidth(0.7);
            context.strokeText(Direction.values()[j].name(), node.x + radius * 0.5,
                node.y - radius * 0.5);
          }
        }
      }
    }
  }

  private void paintDot(int x, int y, int radius) {
    context
        .setFill(colors.isDarkTheme() ? colors.getFrameActiveColor() : colors.getSnakeBodyColor());
    context.fillOval(x + correctionOffsetX, y + correctionOffsetY, radius, radius);
  }

  private void paintDot(int x, int y, int radius, Color color) {
    context.setFill(color);
    context.fillOval(x + correctionOffsetX, y + correctionOffsetY, radius, radius);
  }

  private void paintLine(GraphNode a, GraphNode b) {
    context.setStroke(colors.isDarkTheme() ? colors.getSnakeBodyColor().darker()
        : colors.getFrameActiveColor().darker());
    context.setLineWidth(2);
    context
        .strokeLine(a.x + (radius / 2) + correctionOffsetX, a.y + (radius / 2) + correctionOffsetY,
            b.x + (radius / 2) + correctionOffsetX, b.y + (radius / 2) + correctionOffsetY);
  }


  private void paintBackground() {
    context.clearRect(0, 0, width, height);
    context.setFill(colors.getBackgroundColor());
    context.fillRect(0, 0, width, height);
  }

  /**
   * This inner class serves to build up a graph of the NeuralNetwork, which will be visualized on
   * the user interface.
   */
  private static class GraphNode {

    int x;
    int y;
    boolean active = true;

    GraphNode(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
}
