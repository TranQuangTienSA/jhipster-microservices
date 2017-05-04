package net.tinyset.authorization.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street_1")
    private String street1;

    @Column(name = "street_2")
    private String street2;

    @Column(name = "street_3")
    private String street3;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Size(max = 45)
    @Column(name = "postal_code", length = 45)
    private String postalCode;

    @Column(name = "country")
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet1() {
        return street1;
    }

    public Address street1(String street1) {
        this.street1 = street1;
        return this;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public Address street2(String street2) {
        this.street2 = street2;
        return this;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getStreet3() {
        return street3;
    }

    public Address street3(String street3) {
        this.street3 = street3;
        return this;
    }

    public void setStreet3(String street3) {
        this.street3 = street3;
    }

    public String getCity() {
        return city;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public Address province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Address postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public Address country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        if (address.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + id +
            ", street1='" + street1 + "'" +
            ", street2='" + street2 + "'" +
            ", street3='" + street3 + "'" +
            ", city='" + city + "'" +
            ", province='" + province + "'" +
            ", postalCode='" + postalCode + "'" +
            ", country='" + country + "'" +
            '}';
    }
}
