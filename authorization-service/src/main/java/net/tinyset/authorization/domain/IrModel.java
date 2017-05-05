package net.tinyset.authorization.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A IrModel.
 */
@Entity
@Table(name = "ir_model")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IrModel extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-z0-9._]+$")
    @Column(name = "model", nullable = false)
    private String model;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "state")
    private String state;

    @Column(name = "info")
    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public IrModel model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public IrModel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public IrModel state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public IrModel info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IrModel irModel = (IrModel) o;
        if (irModel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, irModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "IrModel{" +
            "id=" + id +
            ", model='" + model + "'" +
            ", name='" + name + "'" +
            ", state='" + state + "'" +
            ", info='" + info + "'" +
            '}';
    }
}
