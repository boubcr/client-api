package com.sharpon.client.address.entity;

import com.sharpon.client.utils.Auditable;
import com.sharpon.client.lookup.entity.Category;
import com.sharpon.client.lookup.entity.City;
import com.sharpon.client.lookup.entity.Country;
import com.sharpon.client.lookup.entity.Province;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.sharpon.client.address.entity.Address.TABLE_NAME;


@Getter
@Setter
@Entity
@Table(name = TABLE_NAME)
public class Address extends Auditable<String> {

    public static final String TABLE_NAME = "ADDRESS";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Long version;

    private String street;
    private String postalCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CITY_ID", nullable = false)
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVINCE_ID")
    private Province province;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    private boolean enabled;

}