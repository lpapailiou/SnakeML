package ai.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

/**
 * This class is the main data wrapper of game batch statistics data. It contains data about the
 * used algorithm and aggregated game data as list. Method names may not apply to the Google Java
 * Coding Styleguide, as they are optimized for the JSON parser by jackson.
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BatchEntity {

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
