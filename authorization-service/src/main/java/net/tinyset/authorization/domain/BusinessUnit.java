package net.tinyset.authorization.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BusinessUnit.
 */
@Entity
@Table(name = "business_unit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BusinessUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "division")
    private String division;

    @Column(name = "website")
    private String website;

    @Size(max = 45)
    @Column(name = "main_phone", length = 45)
    private String mainPhone;

    @Size(max = 45)
    @Column(name = "other_phone", length = 45)
    private String otherPhone;

    @Size(max = 45)
    @Column(name = "fax", length = 45)
    private String fax;

    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(unique = true)
    private Address billToAddress;

    @OneToOne
    @JoinColumn(unique = true)
    private Address shipToAddress;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private BusinessUnit parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BusinessUnit name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDivision() {
        return division;
    }

    public BusinessUnit division(String division) {
        this.division = division;
        return this;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getWebsite() {
        return website;
    }

    public BusinessUnit website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMainPhone() {
        return mainPhone;
    }

    public BusinessUnit mainPhone(String mainPhone) {
        this.mainPhone = mainPhone;
        return this;
    }

    public void setMainPhone(String mainPhone) {
        this.mainPhone = mainPhone;
    }

    public String getOtherPhone() {
        return otherPhone;
    }

    public BusinessUnit otherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
        return this;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }

    public String getFax() {
        return fax;
    }

    public BusinessUnit fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public BusinessUnit email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getBillToAddress() {
        return billToAddress;
    }

    public BusinessUnit billToAddress(Address address) {
        this.billToAddress = address;
        return this;
    }

    public void setBillToAddress(Address address) {
        this.billToAddress = address;
    }

    public Address getShipToAddress() {
        return shipToAddress;
    }

    public BusinessUnit shipToAddress(Address address) {
        this.shipToAddress = address;
        return this;
    }

    public void setShipToAddress(Address address) {
        this.shipToAddress = address;
    }

    public BusinessUnit getParent() {
        return parent;
    }

    public BusinessUnit parent(BusinessUnit businessUnit) {
        this.parent = businessUnit;
        return this;
    }

    public void setParent(BusinessUnit businessUnit) {
        this.parent = businessUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusinessUnit businessUnit = (BusinessUnit) o;
        if (businessUnit.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, businessUnit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BusinessUnit{" +
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
