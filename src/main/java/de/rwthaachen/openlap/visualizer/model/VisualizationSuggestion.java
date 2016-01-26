package de.rwthaachen.openlap.visualizer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

public class VisualizationSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VIS_SUGGESTION_ID")
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VIS_METHOD_FID")
    @JsonIgnore
    private VisualizationMethod visualizationMethod;
    @Column(name = "INPUT_COLUMN_CONFIG")
    private String olapInputColumnConfiguration;

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

    public String getOlapInputColumnConfiguration() {
        return olapInputColumnConfiguration;
    }

    public void setOlapInputColumnConfiguration(String olapInputColumnConfiguration) {
        this.olapInputColumnConfiguration = olapInputColumnConfiguration;
    }
}
