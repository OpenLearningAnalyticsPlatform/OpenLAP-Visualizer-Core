package de.rwthaachen.openlap.visualizer.model;

import javax.persistence.*;

/**
 * Model class representing the metadata of a Visualization Method(Technique) of a
 * Visualization Framework
 *
 * @author Bassim Bashir
 */
@Entity
@Table (name = "VIS_METHODS")
public class VisualizationMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VIS_METHOD_ID")
    private long id;
    @Column(nullable = false, name = "VIS_METHOD_IMPLEMENTING_CLASS")
    private String implementingClassName;
    @Column(nullable = false, name = "VIS_METHOD_NAME")
    private String name;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "VIS_FRAMEWORK_FID")
    private VisualizationFramework visualizationFramework;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImplementingClassName() {
        return implementingClassName;
    }

    public void setImplementingClassName(String implementingClassName) {
        this.implementingClassName = implementingClassName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VisualizationMethod that = (VisualizationMethod) o;

        if (id != that.id) return false;
        if (implementingClassName != null ? !implementingClassName.equals(that.implementingClassName) : that.implementingClassName != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return visualizationFramework != null ? visualizationFramework.equals(that.visualizationFramework) : that.visualizationFramework == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (implementingClassName != null ? implementingClassName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (visualizationFramework != null ? visualizationFramework.hashCode() : 0);
        return result;
    }
}
