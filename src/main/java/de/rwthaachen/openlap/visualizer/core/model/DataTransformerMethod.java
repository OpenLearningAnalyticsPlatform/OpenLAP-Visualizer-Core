package de.rwthaachen.openlap.visualizer.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

/**
 * The model representing the Data Transformer (Data Adapters) concrete implementations
 *
 * @author Bassim Bashir
 */
@Entity
@Table(name = "DATA_TRANSFORMER_METHODS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataTransformerMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TRANSFORMER_METHOD_ID")
    private long id;
    @Column(nullable = false, name = "TRANSFORMER_METHOD_IMPLEMENTING_CLASS", unique = true)
    private String implementingClass;
    @Column(nullable = false, name = "TRANSFORMER_METHOD_NAME")
    private String name;
    @OneToMany(mappedBy = "dataTransformerMethod", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<VisualizationMethod> visualizationMethods;

    public DataTransformerMethod() {
    }

    public DataTransformerMethod(String name, String implementingClass) {
        this.name = name;
        this.implementingClass = implementingClass;
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

    public List<VisualizationMethod> getVisualizationMethods() {
        return visualizationMethods;
    }

    public void setVisualizationMethods(List<VisualizationMethod> visualizationMethods) {
        this.visualizationMethods = visualizationMethods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataTransformerMethod that = (DataTransformerMethod) o;

        if (id != that.id) return false;
        if (implementingClass != null ? !implementingClass.equals(that.implementingClass) : that.implementingClass != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return visualizationMethods != null ? visualizationMethods.equals(that.visualizationMethods) : that.visualizationMethods == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (implementingClass != null ? implementingClass.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (visualizationMethods != null ? visualizationMethods.hashCode() : 0);
        return result;
    }
}
