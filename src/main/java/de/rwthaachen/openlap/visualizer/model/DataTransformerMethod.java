package de.rwthaachen.openlap.visualizer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

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
    @Column(nullable = false, name = "TRANSFORMER_METHOD_IMPLEMENTING_CLASS")
    private String implementingClass;
    @Column(nullable = false, name = "TRANSFORMER_METHOD_NAME")
    private String name;
    @OneToOne(mappedBy = "dataTransformerMethod")
    private VisualizationMethod visualizationMethod;

    private DataTransformerMethod() {
    }

    public DataTransformerMethod(String name, String implementingClass) {
        this();
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

    public VisualizationMethod getVisualizationMethod() {
        return visualizationMethod;
    }

    public void setVisualizationMethod(VisualizationMethod visualizationMethod) {
        this.visualizationMethod = visualizationMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return visualizationMethod != null ? visualizationMethod.equals(that.visualizationMethod) : that.visualizationMethod == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (implementingClass != null ? implementingClass.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (visualizationMethod != null ? visualizationMethod.hashCode() : 0);
        return result;
    }
}
