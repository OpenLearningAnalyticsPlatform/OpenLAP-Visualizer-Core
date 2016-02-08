package de.rwthaachen.openlap.visualizer.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "VIS_SUGGESTIONS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualizationSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VIS_SUGGESTION_ID")
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VIS_METHOD_FID")
    private VisualizationMethod visualizationMethod;
    @Column(name = "INPUT_COLUMN_CONFIG")
    private String olapDataSetConfiguration;

    public VisualizationSuggestion(){

    }

    public VisualizationSuggestion(VisualizationMethod visMethod,String olapDataSetConfiguration){
        this.visualizationMethod = visMethod;
        this.olapDataSetConfiguration = olapDataSetConfiguration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public VisualizationMethod getVisualizationMethod() {
        return visualizationMethod;
    }

    public void setVisualizationMethod(VisualizationMethod visualizationMethod) {
        this.visualizationMethod = visualizationMethod;
    }

    public String getOlapDataSetConfiguration() {
        return olapDataSetConfiguration;
    }

    public void setOlapDataSetConfiguration(String olapDataSetConfiguration) {
        this.olapDataSetConfiguration = olapDataSetConfiguration;
    }
}
