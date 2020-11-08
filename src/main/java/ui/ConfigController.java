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
import javafx.util.Duration;
import main.Main;
import main.configuration.INeuralNetworkConfig;
import main.configuration.Theme;
import main.configuration.Config;
import main.configuration.Mode;
import ui.painter.impl.NetworkPainter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicMarkableReference;
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
  private Label generationCounter;

  @FXML
  private Button statisticsButton;

  @FXML
  private HBox actionControls;

  @FXML
  private Button startButton;

  @FXML
  private Button stopButton;

  private static ConfigController instance;
  private INeuralNetworkConfig config = Config.getInstance();
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

  static void display(int currentGeneration, Direction direction) {
    if (instance != null) {
      instance.networkPainter.flashOutput(direction.ordinal());
      instance.generationCounter.setText(currentGeneration+"");
    }
  }

  public static void enableStatistics() {
    instance.statisticsButton.setDisable(false);
    instance.openStatistics();
  }

  private void openStatistics() {
    String url = "http://localhost:63342/pa-5/SnakeML/Dashboard.html?_ijt=t2hl0s5te6ddfuu4fjsbdpivgj";
    String os = System.getProperty("os.name").toLowerCase();
    Runtime rt = Runtime.getRuntime();

    try{

      if (os.contains("win")) {
        rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);

      } else if (os.contains("mac")) {
        rt.exec( "open " + url);
      } else if (os.contains("nix") || os.contains("nux")) {
        String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
            "netscape","opera","links","lynx"};
        StringBuffer cmd = new StringBuffer();
        for (int i=0; i<browsers.length; i++)
          cmd.append(i == 0 ? "" : " || ").append(browsers[i]).append(" \"").append(url)
              .append("\" ");
        rt.exec(new String[] { "sh", "-c", cmd.toString() });
      }

  } catch (IOException e) {
      e.printStackTrace();
    }
  }

    private void updateNetworkPainter() {
    networkPainter = new NetworkPainter(context, config.getLayerConfigurationAsList(), inputNodeConfiguration);
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
    scene.getStylesheets().remove(Config.getInstance().getTheme().getCss());
    Config.getInstance().setTheme(theme);
    GameController.resetGamePanel();
    scene.setFill(theme.getBackgroundColor());
    scene.getStylesheets().add(Objects.requireNonNull(Main.class.getClassLoader().getResource(theme.getCss())).toExternalForm());
    updateNetworkPainter();
  }

  private void updateMode() {
    Mode mode = Arrays.stream(Mode.values()).filter(e -> e.getLabel().equals(modeSelector.getValue())).findFirst().get();
    Scene scene = ApplicationController.getScene();
    final double transitionDuration = 0.3;
    if (scene != null && (mode == Mode.NEURAL_NETWORK ||(mode == Mode.MANUAL && Config.getInstance().getMode() != Mode.NEURAL_NETWORK_DEMO) || (mode == Mode.NEURAL_NETWORK_DEMO && Config.getInstance().getMode() != Mode.MANUAL))) {
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
    Config.getInstance().setMode(mode);
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

  private void initializeBaseControls() {
    boardWithControl.setText(config.getBoardWidth() + "");
    boardHeightControl.setText(config.getBoardHeight() + "");
    AtomicReference<String> tempWidth = new AtomicReference<String>(config.getBoardWidth() + "");
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

    AtomicReference<String> tempHeight = new AtomicReference<String>(config.getBoardHeight() + "");
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

    neuralNetworkControls.managedProperty().bind(neuralNetworkControls.visibleProperty());
    statisticControls.managedProperty().bind(statisticControls.visibleProperty());
  }

  private void initializeLayerControls() {
    hiddenLayerCount.setItems(layerCount);
    hiddenLayerCount.getSelectionModel().select((config.getLayerConfigurationAsList().size()-2));
    hiddenLayerCount.setOnAction( e -> updateHiddenLayerSelection());

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
      String nodeCount = (Config.getInstance().getLayerConfiguration().length < i+1) ? Config.getInstance().getLayerConfiguration() +"": "4";
      AtomicReference<String> tempValue = new AtomicReference<String>(nodeCount);
      field.focusedProperty().addListener((o, oldValue, newValue) -> {
        String previousValue = tempValue.toString();
        tempValue.set(field.getText());
        if (configureTextField(field, 1, 64, tempValue.toString(), previousValue)) {
          if (!tempValue.equals(previousValue)) {
            updateNetworkParameter();
          }
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

  private void initializeButtons() {
    statisticsButton.setOnAction(e -> {
      openStatistics();
    });

    startButton.setOnAction(e -> {
      ApplicationController.start();
    });

    stopButton.setOnAction(e -> {
      ApplicationController.stop();
    });

    statisticsButton.setDisable(false);

    stopButton.setDisable(false);
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
