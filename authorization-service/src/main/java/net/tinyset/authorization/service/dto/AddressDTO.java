package net.tinyset.authorization.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Address entity.
 */
public class AddressDTO implements Serializable {

    private Long id;

    private String street1;

    private String street2;

    private String street3;

    private String city;

    private String province;

    @Size(max = 45)
    private String postalCode;

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

    public void setStreet1(String street1) {
        this.street1 = street1;
    }
    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }
    public String getStreet3() {
        return street3;
    }

    public void setStreet3(String street3) {
        this.street3 = street3;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCountry() {
        return country;
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

        AddressDTO addressDTO = (AddressDTO) o;

        if ( ! Objects.equals(id, addressDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
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
