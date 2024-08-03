package com.sharpon.client.contact.entity;

import com.sharpon.client.utils.Auditable;
import com.sharpon.client.lookup.entity.Category;
import com.sharpon.client.lookup.entity.ContactType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.sharpon.client.contact.entity.Contact.TABLE_NAME;


@Getter
@Setter
@Entity
@Table(name = TABLE_NAME)
public class Contact extends Auditable<String> {

    public static final String TABLE_NAME = "CONTACT";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Long version;

    @Column(name = "VALUE", nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONTACT_TYPE_ID", nullable = false)
    private ContactType contactType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    private Boolean enabled;

}