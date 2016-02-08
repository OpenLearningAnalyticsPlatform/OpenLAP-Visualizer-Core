package de.rwthaachen.openlap.visualizer.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The model representing the Visualization framework (libraries)
 *
 * @author Bassim Bashir
 */
@Entity
@Table(name = "VIS_FRAMEWORKS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualizationFramework {
    @Column(unique = true, nullable = false, name = "VIS_FRAMEWORK_NAME")
    String name;
    @Column(nullable = false, name = "VIS_FRAMEWORK_IMPLEMENTOR")
    String creator;
    @Column(name = "VIS_FRAMEWORK_DESCRIPTION")
    String description;
    @Column(nullable = false, name = "VIS_FRAMEWORK_LOCATION")
    @JsonIgnore
    String frameworkLocation;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VIS_FRAMEWORK_ID")
    private long id;
    @OneToMany(mappedBy = "visualizationFramework", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VisualizationMethod> visualizationMethods;

    public VisualizationFramework() {
        visualizationMethods = new ArrayList<>();
    }

    public VisualizationFramework(String name, String creator, String description, String frameworkLocation) {
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.frameworkLocation = frameworkLocation;
        this.visualizationMethods = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrameworkLocation() {
        return frameworkLocation;
    }

    public void setFrameworkLocation(String frameworkLocation) {
        this.frameworkLocation = frameworkLocation;
    }

    public List<VisualizationMethod> getVisualizationMethods() {
        return visualizationMethods;
    }

    public void setVisualizationMethods(List<VisualizationMethod> visualizationMethods) {
        this.visualizationMethods = visualizationMethods;
    }

    @Override
    public String toString() {
        return "Vis Framework details :[id:" + id + ",name:" + name + ",creator:" + creator + ",description:" + description + ",location:" + frameworkLocation + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VisualizationFramework that = (VisualizationFramework) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (frameworkLocation != null ? !frameworkLocation.equals(that.frameworkLocation) : that.frameworkLocation != null)
            return false;
        return visualizationMethods != null ? visualizationMethods.equals(that.visualizationMethods) : that.visualizationMethods == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (frameworkLocation != null ? frameworkLocation.hashCode() : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (visualizationMethods != null ? visualizationMethods.hashCode() : 0);
        return result;
    }
}
