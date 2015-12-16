package de.rwthaachen.openlap.visualizer.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The model representing the Visualization framework (libraries)
 *
 * @author Bassim Bashir
 */
@Entity
@Table (name = "VIS_FRAMEWORKS")
public class VisualizationFramework {

    @Column(unique = true, nullable = false, name = "VIS_FRAMEWORK_NAME")
    String name;
    @Column(nullable = false, name = "VIS_FRAMEWORK_UPLOADER")
    String uploadedBy;
    @Column(name = "VIS_FRAMEWORK_DESCRIPTION")
    String description;
    @Column(nullable = false, name = "VIS_FRAMEWORK_LOCATION")
    String frameworkLocation;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VIS_FRAMEWORK_ID")
    private long id;
    @OneToMany(mappedBy = "visualizationFramework", fetch = FetchType.LAZY)
    private List<VisualizationMethod> visualizationMethods;

    private VisualizationFramework(){
        visualizationMethods = new ArrayList<>();
    }

    public VisualizationFramework(String name, String uploadedBy, String description, String frameworkLocation){
        this();
        this.name = name;
        this.uploadedBy = uploadedBy;
        this.description = description;
        this.frameworkLocation = frameworkLocation;
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

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
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
        return "Vis Framework details :[id:" + id + ",name:" + name + ",uploadedBy:" + uploadedBy + ",description:" + description + ",location:" + frameworkLocation + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VisualizationFramework that = (VisualizationFramework) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (uploadedBy != null ? !uploadedBy.equals(that.uploadedBy) : that.uploadedBy != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (frameworkLocation != null ? !frameworkLocation.equals(that.frameworkLocation) : that.frameworkLocation != null)
            return false;
        return visualizationMethods != null ? visualizationMethods.equals(that.visualizationMethods) : that.visualizationMethods == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (uploadedBy != null ? uploadedBy.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (frameworkLocation != null ? frameworkLocation.hashCode() : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (visualizationMethods != null ? visualizationMethods.hashCode() : 0);
        return result;
    }
}
