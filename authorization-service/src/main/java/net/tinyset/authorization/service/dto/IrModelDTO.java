package net.tinyset.authorization.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the IrModel entity.
 */
public class IrModelDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-z0-9._]+$")
    private String model;

    @NotNull
    private String name;

    private String state;

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

    public void setModel(String model) {
        this.model = model;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getInfo() {
        return info;
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

        IrModelDTO irModelDTO = (IrModelDTO) o;

        if ( ! Objects.equals(id, irModelDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "IrModelDTO{" +
            "id=" + id +
            ", model='" + model + "'" +
            ", name='" + name + "'" +
            ", state='" + state + "'" +
            ", info='" + info + "'" +
            '}';
    }
}
