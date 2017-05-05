package net.tinyset.authorization.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BusinessUnit entity.
 */
public class BusinessUnitDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String division;

    private String website;

    @Size(max = 45)
    private String mainPhone;

    @Size(max = 45)
    private String otherPhone;

    @Size(max = 45)
    private String fax;

    private String email;

    private Long billToAddressId;

    private String billToAddressStreet1;

    private Long shipToAddressId;

    private String shipToAddressStreet1;

    private Long parentId;

    private String parentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    public String getMainPhone() {
        return mainPhone;
    }

    public void setMainPhone(String mainPhone) {
        this.mainPhone = mainPhone;
    }
    public String getOtherPhone() {
        return otherPhone;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getBillToAddressId() {
        return billToAddressId;
    }

    public void setBillToAddressId(Long addressId) {
        this.billToAddressId = addressId;
    }

    public String getBillToAddressStreet1() {
        return billToAddressStreet1;
    }

    public void setBillToAddressStreet1(String addressStreet1) {
        this.billToAddressStreet1 = addressStreet1;
    }

    public Long getShipToAddressId() {
        return shipToAddressId;
    }

    public void setShipToAddressId(Long addressId) {
        this.shipToAddressId = addressId;
    }

    public String getShipToAddressStreet1() {
        return shipToAddressStreet1;
    }

    public void setShipToAddressStreet1(String addressStreet1) {
        this.shipToAddressStreet1 = addressStreet1;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long businessUnitId) {
        this.parentId = businessUnitId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String businessUnitName) {
        this.parentName = businessUnitName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BusinessUnitDTO businessUnitDTO = (BusinessUnitDTO) o;

        if ( ! Objects.equals(id, businessUnitDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BusinessUnitDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", division='" + division + "'" +
            ", website='" + website + "'" +
            ", mainPhone='" + mainPhone + "'" +
            ", otherPhone='" + otherPhone + "'" +
            ", fax='" + fax + "'" +
            ", email='" + email + "'" +
            '}';
    }
}
