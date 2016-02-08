package de.rwthaachen.openlap.visualizer.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Model class representing the metadata of a Visualization Method(Technique) of a
 * Visualization Framework
 *
 * @author Bassim Bashir
 */
@Entity
@Table(name = "VIS_METHODS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualizationMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VIS_METHOD_ID")
    private long id;
    @Column(nullable = false, name = "VIS_METHOD_IMPLEMENTING_CLASS", unique = true)
    private String implementingClass;
    @Column(nullable = false, name = "VIS_METHOD_NAME")
    private String name;
    @JoinColumn(name = "VIS_FRAMEWORK_FID")
    @ManyToOne
    @JsonIgnore
    private VisualizationFramework visualizationFramework;
    @JoinColumn(name = "DATA_TRANSFORMER_METHOD_FID")
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private DataTransformerMethod dataTransformerMethod;

    public VisualizationMethod() {
    }

    public VisualizationMethod(String implementingClassName, String name,VisualizationFramework visualizationFramework, DataTransformerMethod dataTransformerMethod) {
        this.implementingClass = implementingClassName;
        this.visualizationFramework = visualizationFramework;
        this.name = name;
        this.dataTransformerMethod = dataTransformerMethod;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImplementingClass() {
        return implementingClass;
    }

    public void setImplementingClass(String implementingClass) {
        this.implementingClass = implementingClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VisualizationFramework getVisualizationFramework() {
        return visualizationFramework;
    }

    public void setVisualizationFramework(VisualizationFramework visualizationFramework) {
        this.visualizationFramework = visualizationFramework;
    }

    public DataTransformerMethod getDataTransformerMethod() {
        return dataTransformerMethod;
    }

    public void setDataTransformerMethod(DataTransformerMethod dataTransformerMethod) {
        this.dataTransformerMethod = dataTransformerMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VisualizationMethod that = (VisualizationMethod) o;

        if (id != that.id) return false;
        if (implementingClass != null ? !implementingClass.equals(that.implementingClass) : that.implementingClass != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (visualizationFramework != null ? !visualizationFramework.equals(that.visualizationFramework) : that.visualizationFramework != null)
            return false;
        return dataTransformerMethod != null ? dataTransformerMethod.equals(that.dataTransformerMethod) : that.dataTransformerMethod == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (implementingClass != null ? implementingClass.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (visualizationFramework != null ? visualizationFramework.hashCode() : 0);
        result = 31 * result + (dataTransformerMethod != null ? dataTransformerMethod.hashCode() : 0);
        return result;
    }
}
