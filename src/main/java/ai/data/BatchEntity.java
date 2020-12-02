package ai.data;

import java.util.List;

public class BatchEntity {

  // note: method names may not apply to coding guidelines, because jackson library will use method names to generate variable names for JSON file.

  private ConfigurationEntity configuration;
  private List<GenerationEntity> generations;

  public void setConfigurationEntity(ConfigurationEntity configurationEntity) {
    this.configuration = configurationEntity;
  }

  public void setGenerationEntities(List<GenerationEntity> generationEntity) {
    this.generations = generationEntity;
  }

  public ConfigurationEntity getConfiguration() {
    return configuration;
  }

  public List<GenerationEntity> getGenerations() {
    return generations;
  }

}
