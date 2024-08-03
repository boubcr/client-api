package com.sharpon.client.lookup.entity;

import com.sharpon.client.utils.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.sharpon.client.lookup.entity.Occupation.TABLE_NAME;

@Getter
@Setter
@Entity
@Table(name = TABLE_NAME)
public class Occupation extends Auditable<String> {

    public static final String TABLE_NAME = "OCCUPATION";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Long version;

    @Column(name = "CODE", nullable = false, length = 10)
    private String code;

    @Column(name = "NAME", nullable = false)
    private String name;
}