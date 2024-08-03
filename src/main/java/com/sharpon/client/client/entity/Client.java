package com.sharpon.client.client.entity;

import com.sharpon.client.address.entity.Address;
import com.sharpon.client.utils.Auditable;
import com.sharpon.client.contact.entity.Contact;
import com.sharpon.client.lookup.entity.Company;
import com.sharpon.client.lookup.entity.Gender;
import com.sharpon.client.lookup.entity.Nationality;
import com.sharpon.client.lookup.entity.Occupation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static com.sharpon.client.client.entity.Client.TABLE_NAME;


@Getter
@Setter
@Entity
@Table(name = TABLE_NAME)
public class Client extends Auditable<String> {

    public static final String TABLE_NAME = "CLIENT";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Long version;

    @Column(name = "CODE", nullable = false, length = 10)
    private String code;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GENDER_ID", nullable = false)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NATIONALITY_ID")
    private Nationality nationality;

    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OCCUPATION_ID")
    private Occupation occupation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    private BigDecimal salary;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "CLIENT_ADDRESS", joinColumns = @JoinColumn(name = "CLIENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID"))
    private Set<Address> addresses;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "CLIENT_CONTACT", joinColumns = @JoinColumn(name = "CLIENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONTACT_ID"))
    private Set<Contact> contacts;
}