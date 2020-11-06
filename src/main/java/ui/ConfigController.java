package ui;

import ai.InputNode;
import game.Direction;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Main;
import main.configuration.Theme;
import main.configuration.Config;
import main.configuration.Mode;
import ui.painter.impl.NetworkPainter;
import javax.xml.soap.Text;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ConfigController implements Initializable {

  @FXML
  private HBox baseControls;

  @FXML
  private TextField boardWithControl;

  @FXML
  private TextField boardHeightControl;

  @FXML
  private ComboBox<String> themeSelector;

  @FXML
  private ComboBox<String> modeSelector;

  @FXML
  private HBox neuralNetworkControls;

  @FXML
  private HBox hiddenLayerConfiguration;

  @FXML
  private ComboBox hiddenLayerCount;

  @FXML
  private HBox hiddenLayerControls;

  @FXML
  private VBox inputNodeConfiguration;

  @FXML
  private Canvas layerPane;

  @FXML
  private TextField generationControl;

  @FXML
  private TextField populationControl;

  @FXML
  private TextField randomizationControl;

  @FXML
  private HBox statisticControls;

  @FXML
  private Label generationCounter;

  @FXML
  private Button startButton;

  @FXML
  private Button stopButton;

  @FXML
  private Button statisticsButton;

  private static ConfigController instance;
  private NetworkPainter networkPainter;
  private GraphicsContext context;
  private ObservableList<String> colorList = FXCollections.observableArrayList(Arrays.stream(
      Theme.values()).map(Enum::name).collect(Collectors.toList()));
  private ObservableList<String> modeList = FXCollections.observableArrayList(
      Arrays.stream(Mode.values()).map(Mode::getLabel).collect(Collectors.toList()));
  private ObservableList<String> layerCount = FXCollections
      .observableArrayList("0", "1", "2", "3", "4", "5");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    instance = this;
    context = layerPane.getGraphicsContext2D();

    initializeBaseControls();
    initializeLayerControls();
    initializeGenerationControls();

    updateNetworkPainter();

    startButton.setOnAction(e -> {
      ApplicationController.start();
    });

    stopButton.setOnAction(e -> {
      ApplicationController.stop();
    });

    statisticsButton.setDisable(true);
    stopButton.setDisable(true);
    neuralNetworkControls.managedProperty().bind(neuralNetworkControls.visibleProperty());
    statisticControls.managedProperty().bind(statisticControls.visibleProperty());
    Platform.runLater(() -> boardWithControl.getParent().requestFocus());

  }

  static void disableInputs(boolean setDisable) {
    instance.baseControls.setDisable(setDisable);
    instance.neuralNetworkControls.setDisable(setDisable);
    instance.startButton.setDisable(setDisable);
    instance.stopButton.setDisable(!setDisable);
    if (!setDisable) {
      instance.networkPainter.paintNetwork();
      instance.generationCounter.setText("0");
      Platform.runLater(() -> instance.boardWithControl.getParent().requestFocus());
    }
  }



  private void updateNetworkPainter() {
    networkPainter = new NetworkPainter(context, Config.getInstance().getLayerConfigurationAsList(), inputNodeConfiguration);
    networkPainter.paintNetwork();
  }

  private void initializeBaseControls() {
    boardWithControl.setText(Config.getInstance().getBoardWidth() + "");
    boardHeightControl.setText(Config.getInstance().getBoardHeight() + "");
    AtomicReference<String> tempWidth = new AtomicReference<String>(Config.getInstance().getBoardWidth() + "");
    boardWithControl.focusedProperty().addListener((o, oldValue, newValue) -> {
      String previousValue = tempWidth.toString();
      tempWidth.set(boardWithControl.getText());
      if (configureTextField(boardWithControl, 1, 100, tempWidth.toString(), previousValue)) {
        if (!tempWidth.toString().equals(previousValue)) {
          Config.getInstance().setBoardWidth(Integer.parseInt(tempWidth.toString()));
          GameController.resetGamePanel();
        }
      }
    });
    AtomicReference<String> tempHeight = new AtomicReference<String>(Config.getInstance().getBoardHeight() + "");
    boardHeightControl.focusedProperty().addListener((o, oldValue, newValue) -> {
      String previousValue = tempHeight.toString();
      tempHeight.set(boardHeightControl.getText());
      if (configureTextField(boardHeightControl, 1, 100, tempHeight.toString(), previousValue)) {
        if (!tempHeight.toString().equals(previousValue)) {
          Config.getInstance().setBoardHeight(Integer.parseInt(tempHeight.toString()));
          GameController.resetGamePanel();
        }
      }
    });
    themeSelector.setItems(colorList);
    themeSelector.getSelectionModel().select(Config.getInstance().getTheme().ordinal());
    themeSelector.setOnAction( e -> updateTheme());


    modeSelector.setItems(modeList);
    modeSelector.getSelectionModel().select(Config.getInstance().getMode().ordinal());
    modeSelector.setOnAction( e -> updateMode());
    updateMode();
  }

  private void initializeGenerationControls() {
    generationControl.setText(Config.getInstance().getGenerationCount() + "");
    populationControl.setText(Config.getInstance().getPopulationSize() + "");
    randomizationControl.setText(Config.getInstance().getRandomizationRate() + "");

    AtomicReference<String> tempGenerations = new AtomicReference<String>(Config.getInstance().getGenerationCount() + "");
    generationControl.focusedProperty().addListener((o, oldValue, newValue) -> {
      String previousValue = tempGenerations.toString();
      tempGenerations.set(generationControl.getText());
      if (configureTextField(generationControl, 1, 5000, tempGenerations.toString(), previousValue)) {
        Config.getInstance().setGenerationCount(Integer.parseInt(tempGenerations.toString()));
      }
    });

    AtomicReference<String> tempPopulations = new AtomicReference<String>(Config.getInstance().getPopulationSize() + "");
    populationControl.focusedProperty().addListener((o, oldValue, newValue) -> {
      String previousValue = tempPopulations.toString();
      tempPopulations.set(populationControl.getText());
      if (configureTextField(populationControl, 1, 5000, tempPopulations.toString(), previousValue)) {
        Config.getInstance().setPopulationSize(Integer.parseInt(tempPopulations.toString()));
      }
    });

    AtomicReference<String> tempRate = new AtomicReference<String>(Config.getInstance().getRandomizationRate() + "");
    randomizationControl.focusedProperty().addListener((o, oldValue, newValue) -> {
      String previousValue = tempRate.toString();
      tempRate.set(randomizationControl.getText());
      if (configureDoubleTextField(randomizationControl, 0, 1, tempRate.toString(), previousValue)) {
        Config.getInstance().setRandomizationRate(Double.parseDouble(tempRate.toString()));
      }
    });
  }

  static void display(int currentGeneration, Direction direction) {
    if (instance != null) {
      instance.networkPainter.flashOutput(direction.ordinal());
      instance.generationCounter.setText(currentGeneration+"");
    }
  }

  private void initializeLayerControls() {
    hiddenLayerCount.setItems(layerCount);
    hiddenLayerCount.getSelectionModel().select((Config.getInstance().getLayerConfigurationAsList().size()-2));
    hiddenLayerCount.setOnAction( e -> updateHiddenLayerSelection());
    //updateHiddenLayerSelection();   // TODO: test

    for (int i = 0; i < Config.getInstance().getLayerConfiguration()[0]; i++) {
      RadioButton button = new RadioButton();
      button.setSelected(true);
      inputNodeConfiguration.getChildren().add(button);
    }

    for (int i = 0; i < hiddenLayerControls.getChildren().size(); i++) {
      TextField field = (TextField) hiddenLayerControls.getChildren().get(i);
      List<Integer> layer = Config.getInstance().getLayerConfigurationAsList();
      if (i < layer.size()-2) {
        field.setText(layer.get(i+1) + "");
      } else {
        field.setVisible(false);
      }
      field.textProperty().addListener((o, oldValue, newValue) -> {
        if (configureTextField(field, 1, 64, newValue, oldValue)) {
          updateNetworkParameter();
        }});
    }
    for (int i = 0; i < inputNodeConfiguration.getChildren().size(); i++) {
      RadioButton box = (RadioButton) inputNodeConfiguration.getChildren().get(i);
      box.setTooltip(new Tooltip(InputNode.values()[i].getTooltipText()));
      box.setOnAction(e -> {
        if (!box.isSelected()) {
          int activeNodes = (int) inputNodeConfiguration.getChildren().stream().filter(n -> ((RadioButton) n).isSelected()).count();
          if (activeNodes == 0) {
            box.setSelected(true);
          }
        }

        int index = inputNodeConfiguration.getChildren().indexOf(box);
        if (box.isSelected()) {
          Config.getInstance().addInputNode(index);
        } else {
          Config.getInstance().removeInputNodeFromSelection(index);
        }
        int[] netParams = Config.getInstance().getLayerConfiguration();
        int activeNodes = (int) inputNodeConfiguration.getChildren().stream().filter(n -> ((RadioButton) n).isSelected()).count();
        netParams[0] = activeNodes;
        Config.getInstance().setLayerConfiguration(netParams);
        updateNetworkPainter();

      });
    }
  }

  private void updateHiddenLayerSelection() {
    int selection = Integer.parseInt(hiddenLayerCount.getValue().toString());
    for (int i = 0; i < hiddenLayerControls.getChildren().size(); i++) {
      TextField field = (TextField) hiddenLayerControls.getChildren().get(i);
      if (i < selection) {
        if (!field.isVisible()) {
          int hiddenLayerNodeCount = 4;
          field.setText(hiddenLayerNodeCount + "");
          field.setVisible(true);
        }
      } else {
        field.setVisible(false);
      }
    }
    updateNetworkParameter();
  }

  void updateNetworkParameter() {
    int[] currentParams = Config.getInstance().getLayerConfiguration();
    int nodes = (int) hiddenLayerControls.getChildren().stream().filter(n -> n.isVisible()).count();
    int[] network = new int[nodes+2];
    network[0] = currentParams[0];
    network[network.length-1] = currentParams[currentParams.length-1];

    int index = 1;
    for (Node node : hiddenLayerControls.getChildren()) {
      TextField field = (TextField) node;
      if (field.isVisible()) {
        network[index] = Integer.parseInt(field.getText());
        index++;
      }
    }
    Config.getInstance().setLayerConfiguration(network);
    updateNetworkPainter();
  }

  private void updateTheme() {
    List<String> cssList = Arrays.stream(Theme.values()).map(Theme::getCss).collect(Collectors.toList());
    for (Object str : cssList) {
      String sheet = (String) str;
      this.boardWithControl.getScene().getStylesheets().removeIf(s -> s.matches(
          Objects.requireNonNull(Main.class.getClassLoader().getResource(sheet)).toExternalForm()));
    }
    Scene scene = this.boardWithControl.getScene();
    Theme theme = Theme.valueOf(themeSelector.getValue());
    scene.getStylesheets().remove(Config.getInstance().getTheme().getCss());
    Config.getInstance().setTheme(theme);
    GameController.resetGamePanel();
    scene.setFill(theme.getBackgroundColor());
    scene.getStylesheets().add(Objects.requireNonNull(Main.class.getClassLoader().getResource(theme.getCss())).toExternalForm());
    updateNetworkPainter();
  }

  private void updateMode() {
    Mode mode = Arrays.stream(Mode.values()).filter(e -> e.getLabel().equals(modeSelector.getValue())).findFirst().get();
    neuralNetworkControls.setVisible(mode == Mode.NEURAL_NETWORK);
    statisticControls.setVisible(mode == Mode.NEURAL_NETWORK);
    Config.getInstance().setMode(mode);
  }


  private boolean configureTextField(TextField field, int min, int max, String newValue, String oldValue) {
    try {
      int result = Integer.parseInt(newValue);
      if (result >= min && result <= max) {
        field.setText(result + "");
        return true;
      } else {
        field.setText(oldValue);
      }
    } catch (Exception e) {
      field.setText(oldValue);
    }
    return false;
  }

  private boolean configureDoubleTextField(TextField field, int min, int max, String newValue, String oldValue) {
    try {
      double result = Double.parseDouble(newValue);
      if (result >= min && result <= max) {
        field.setText(result + "");
        return true;
      } else {
        field.setText(oldValue);
      }
    } catch (Exception e) {
      field.setText(oldValue);
    }
    return false;
  }


}
