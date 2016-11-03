package com.maltseva.viewmodel;

import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.Size;

public class ContactCreate {
    private int id;
    private int idUser;

    @Size(min = 4, message = "Your Last Name must be more than 3 characters")
    private String lastName;

    @Size(min = 4, message = "Your First Name must be more than 3 characters")
    private String firstName;

    @Size(min = 4, message = "Your Middle Name must be more than 3 characters")
    private String middleName;

    private String cellPhone;
    private String homePhone;
    private String address;

    @Email
    private String email;

    public ContactCreate(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
