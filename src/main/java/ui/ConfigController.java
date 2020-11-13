package ui;

import ai.InputNode;
import game.Direction;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
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
import javafx.stage.Popup;
import javafx.util.Duration;
import main.Main;
import main.configuration.IConfigReader;
import main.configuration.IConfigWriter;
import main.configuration.Theme;
import main.configuration.Config;
import main.configuration.Mode;
import ui.painter.impl.NetworkPainter;
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
  private Label openLabel;

  @FXML
  private Button statisticsButton;

  @FXML
  private HBox actionControls;

  @FXML
  private Button startButton;

  @FXML
  private Button stopButton;

  private static ConfigController instance;
  private IConfigReader configReader = Config.getConfigReader();
  private IConfigWriter configWriter = Config.getConfigWriter();
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
    initializeButtons();
    updateNetworkPainter();

    Platform.runLater(() -> boardWithControl.getParent().requestFocus());
  }

  static void display(Direction direction) {
    if (instance != null) {
      instance.networkPainter.flashOutput(direction.ordinal());
    }
  }

  static void enableStatistics() {
    instance.openLabel.setDisable(false);
    instance.statisticsButton.setDisable(false);
  }

  private void openStatistics() {
    //TODO: open statistics in browser
  }

  private void updateNetworkPainter() {
    networkPainter = new NetworkPainter(context, configReader.getLayerConfigurationAsList(), inputNodeConfiguration);
    networkPainter.paintNetwork();
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
    scene.getStylesheets().remove(configReader.getTheme().getCss());
    configWriter.setTheme(theme);
    GameController.resetGamePanel();
    scene.setFill(theme.getBackgroundColor());
    scene.getStylesheets().add(Objects.requireNonNull(Main.class.getClassLoader().getResource(theme.getCss())).toExternalForm());
    updateNetworkPainter();
  }

  private void updateMode() {
    Mode mode = Arrays.stream(Mode.values()).filter(e -> e.getLabel().equals(modeSelector.getValue())).findFirst().get();
    Scene scene = ApplicationController.getScene();
    final double transitionDuration = 0.3;
    if (scene != null && (mode == Mode.NEURAL_NETWORK ||(mode == Mode.MANUAL && configReader.getMode() != Mode.NEURAL_NETWORK_DEMO) || (mode == Mode.NEURAL_NETWORK_DEMO && configReader.getMode() != Mode.MANUAL))) {
      modeSelector.hide();
      TranslateTransition baseControlsTransition = new TranslateTransition(Duration.seconds(transitionDuration), baseControls);
      if (mode == Mode.NEURAL_NETWORK) {
        baseControlsTransition.setFromY(237);
        baseControlsTransition.setToY(0);
      } else {
        baseControlsTransition.setFromY(-237);
        baseControlsTransition.setToY(0);
      }
      TranslateTransition actionControlsTransition = new TranslateTransition(Duration.seconds(transitionDuration), actionControls);
      if (mode == Mode.NEURAL_NETWORK) {
        actionControlsTransition.setFromX(-177);
        actionControlsTransition.setToX(0);
        actionControlsTransition.setFromY(-235);
        actionControlsTransition.setToY(0);
      } else {
        actionControlsTransition.setFromX(177);
        actionControlsTransition.setToX(0);
        actionControlsTransition.setFromY(235);
        actionControlsTransition.setToY(0);
      }
      ScaleTransition actionControlsScaleTransition = new ScaleTransition(Duration.seconds(transitionDuration), actionControls);
      if (mode == Mode.NEURAL_NETWORK) {
        actionControlsScaleTransition.setFromX(2);
        actionControlsScaleTransition.setToX(1);
      } else {
        actionControlsScaleTransition.setFromX(0.5);
        actionControlsScaleTransition.setToX(1);
      }
      neuralNetworkControls.setOpacity(0);
      FadeTransition neuralNetworkTransition = new FadeTransition((Duration.seconds(0.0001)), neuralNetworkControls);
      if (mode == Mode.NEURAL_NETWORK) {
        neuralNetworkTransition.setFromValue(0);
        neuralNetworkTransition.setToValue(1);
      }
      statisticControls.setOpacity(0);
      FadeTransition statisticTransition = new FadeTransition((Duration.seconds(0.0001)), statisticControls);
      if (mode == Mode.NEURAL_NETWORK) {
        statisticTransition.setFromValue(0);
        statisticTransition.setToValue(1);
      }
      ParallelTransition para1 = new ParallelTransition();
      para1.getChildren().addAll(baseControlsTransition, actionControlsTransition, actionControlsScaleTransition);
      ParallelTransition para2 = new ParallelTransition();
      para2.getChildren().addAll(neuralNetworkTransition, statisticTransition);
      SequentialTransition seq = new SequentialTransition();
      seq.getChildren().addAll(para1, para2);
      seq.play();
    }

    neuralNetworkControls.setVisible(mode == Mode.NEURAL_NETWORK);
    statisticControls.setVisible(mode == Mode.NEURAL_NETWORK);
    configWriter.setMode(mode);
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

  private void updateNetworkParameter() {
    int[] currentParams = configReader.getLayerConfiguration();
    int nodes = (int) hiddenLayerControls.getChildren().stream().filter(Node::isVisible).count();
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
    configWriter.setLayerConfiguration(network);
    updateNetworkPainter();
  }

  private void initializeBaseControls() {
    boardWithControl.setText(configReader.getBoardWidth() + "");
    boardHeightControl.setText(configReader.getBoardHeight() + "");
    AtomicReference<String> tempWidth = new AtomicReference<>(configReader.getBoardWidth() + "");
    boardWithControl.focusedProperty().addListener((o, oldValue, newValue) -> {
      String previousValue = tempWidth.toString();
      tempWidth.set(boardWithControl.getText());
      if (configureTextField(boardWithControl, 4, 100, tempWidth.toString(), previousValue)) {    // TODO: make sure minimum fits snake starting position
        if (!tempWidth.toString().equals(previousValue)) {
          configWriter.setBoardWidth(Integer.parseInt(tempWidth.toString()));
          GameController.resetGamePanel();
        }
      } else {
        showPopupMessage("min: 4, max: 100", boardWithControl);
      }
    });

    AtomicReference<String> tempHeight = new AtomicReference<>(configReader.getBoardHeight() + "");
    boardHeightControl.focusedProperty().addListener((o, oldValue, newValue) -> {
      String previousValue = tempHeight.toString();
      tempHeight.set(boardHeightControl.getText());
      if (configureTextField(boardHeightControl, 4, 100, tempHeight.toString(), previousValue)) {    // TODO: make sure minimum fits snake starting position
        if (!tempHeight.toString().equals(previousValue)) {
          configWriter.setBoardHeight(Integer.parseInt(tempHeight.toString()));
          GameController.resetGamePanel();
        }
      } else {
        showPopupMessage("min: 4, max: 100", boardHeightControl);
      }
    });

    themeSelector.setItems(colorList);
    themeSelector.getSelectionModel().select(configReader.getTheme().ordinal());
    themeSelector.setOnAction( e -> updateTheme());

    modeSelector.setItems(modeList);
    modeSelector.getSelectionModel().select(configReader.getMode().ordinal());
    modeSelector.setOnAction( e -> updateMode());

    updateMode();

    neuralNetworkControls.managedProperty().bind(neuralNetworkControls.visibleProperty());
    statisticControls.managedProperty().bind(statisticControls.visibleProperty());
  }

  private void initializeLayerControls() {
    hiddenLayerCount.setItems(layerCount);
    hiddenLayerCount.getSelectionModel().select((configReader.getLayerConfigurationAsList().size()-2));
    hiddenLayerCount.setOnAction( e -> updateHiddenLayerSelection());

    for (int i = 0; i < configReader.getLayerConfiguration()[0]; i++) {
      RadioButton button = new RadioButton();
      button.setSelected(true);
      inputNodeConfiguration.getChildren().add(button);
    }

    for (int i = 0; i < hiddenLayerControls.getChildren().size(); i++) {
      TextField field = (TextField) hiddenLayerControls.getChildren().get(i);
      List<Integer> layer = configReader.getLayerConfigurationAsList();
      if (i < layer.size()-2) {
        field.setText(layer.get(i+1) + "");
      } else {
        field.setVisible(false);
      }
      String nodeCount = (configReader.getLayerConfiguration().length > i+1) ? configReader.getLayerConfiguration()[i+1] +"": "4";
      AtomicReference<String> tempValue = new AtomicReference<>(nodeCount);
      field.focusedProperty().addListener((o, oldValue, newValue) -> {
        String previousValue = tempValue.toString();
        tempValue.set(field.getText());
        if (configureTextField(field, 1, 64, tempValue.toString(), previousValue)) {
          if (!tempValue.toString().equals(previousValue)) {
            updateNetworkParameter();
          }
        }  else {
          showPopupMessage("min: 1, max: 64", field);
        }});
    }
    for (int i = 0; i < inputNodeConfiguration.getChildren().size(); i++) {
      RadioButton box = (RadioButton) inputNodeConfiguration.getChildren().get(i);
      box.setTooltip(new Tooltip(InputNode.values()[i].getTooltipText()));
      box.selectedProperty().addListener(e -> {
        if (!box.isSelected()) {
          int activeNodes = (int) inputNodeConfiguration.getChildren().stream().filter(n -> ((RadioButton) n).isSelected()).count();
          if (activeNodes == 0) {
            box.setSelected(true);
          }
        }

        int index = inputNodeConfiguration.getChildren().indexOf(box);
        if (box.isSelected()) {
          configWriter.addInputNode(index);
        } else {
          configWriter.removeInputNodeFromSelection(index);
        }
        int[] netParams = configReader.getLayerConfiguration();
        int activeNodes = (int) inputNodeConfiguration.getChildren().stream().filter(n -> ((RadioButton) n).isSelected()).count();
        netParams[0] = activeNodes;
        configWriter.setLayerConfiguration(netParams);
        updateNetworkPainter();

      });
    }
  }

  private void initializeGenerationControls() {
    generationControl.setText(configReader.getGenerationCount() + "");
    populationControl.setText(configReader.getPopulationSize() + "");
    randomizationControl.setText(configReader.getRandomizationRate() + "");

    AtomicReference<String> tempGenerations = new AtomicReference<>(configReader.getGenerationCount() + "");
    generationControl.focusedProperty().addListener((o, oldValue, newValue) -> {
      String previousValue = tempGenerations.toString();
      tempGenerations.set(generationControl.getText());
      if (configureTextField(generationControl, 1, 5000, tempGenerations.toString(), previousValue)) {
        configWriter.setGenerationCount(Integer.parseInt(tempGenerations.toString()));
      } else {
        showPopupMessage("min: 1, max: 5000", generationControl);
      }
    });

    AtomicReference<String> tempPopulations = new AtomicReference<>(configReader.getPopulationSize() + "");
    populationControl.focusedProperty().addListener((o, oldValue, newValue) -> {
      String previousValue = tempPopulations.toString();
      tempPopulations.set(populationControl.getText());
      if (configureTextField(populationControl, 1, 5000, tempPopulations.toString(), previousValue)) {
        configWriter.setPopulationSize(Integer.parseInt(tempPopulations.toString()));
      } else {
        showPopupMessage("min: 1, max: 5000", populationControl);
      }
    });

    AtomicReference<String> tempRate = new AtomicReference<>(configReader.getRandomizationRate() + "");
    randomizationControl.focusedProperty().addListener((o, oldValue, newValue) -> {
      String previousValue = tempRate.toString();
      tempRate.set(randomizationControl.getText());
      if (configureDoubleTextField(randomizationControl, 0, 1, tempRate.toString(), previousValue)) {
        configWriter.setRandomizationRate(Double.parseDouble(tempRate.toString()));
      } else {
        showPopupMessage("min: 0, max: 1", randomizationControl);
      }
    });
  }

  private void initializeButtons() {
    statisticsButton.setOnAction(e -> openStatistics());

    startButton.setOnAction(e -> ApplicationController.start());

    stopButton.setOnAction(e -> ApplicationController.stop());

    openLabel.setDisable(true);
    statisticsButton.setDisable(true);    // TODO: enable earlier if we use realtime graphs
    stopButton.setDisable(true);
  }

  static void disableInputs(boolean setDisable) {
    instance.baseControls.setDisable(setDisable);
    instance.neuralNetworkControls.setDisable(setDisable);
    instance.startButton.setDisable(setDisable);
    instance.stopButton.setDisable(!setDisable);
    if (!setDisable) {
      instance.networkPainter.paintNetwork();
      Platform.runLater(() -> instance.boardWithControl.getParent().requestFocus());
    }
  }

  static void selectAllRadioButtons() {
    for (Node node : instance.inputNodeConfiguration.getChildren()) {
      ((RadioButton) node).setSelected(true);
    }
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

  private static Popup createPopup(final String message) {
    final Popup popup = new Popup();
    popup.setAutoFix(true);
    popup.setAutoHide(true);
    popup.setHideOnEscape(true);
    Label label = new Label("  " + message);    // space for graphic reasons (padding left did not work)
    label.setOnMouseReleased(e -> popup.hide());
    popup.getContent().add(label);
    return popup;
  }

  private static void showPopupMessage(final String message, Node node) {
    final Popup popup = createPopup(message);
    popup.setOnShown(e -> {
      popup.setX(node.localToScreen(node.getBoundsInLocal()).getMinX());
      popup.setY(node.localToScreen(node.getBoundsInLocal()).getMinY()-30);
    });
    if (ApplicationController.getStage() != null) {
      popup.show(ApplicationController.getStage());
    }
  }


}