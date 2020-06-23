package jp.co.nissho_ele.handson.model;

import javax.persistence.Cacheable;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 住所モデル
 */

@Cacheable
@javax.persistence.Entity
@Getter
@Setter
@Table(name = "ZIP_CODE")
public class AddressModel extends PanacheEntityBase {

    @Id
    private String zip_code;

    private Integer zip_type;

    private String pref;

    private String city;

    private String town;

    private String street;

    private String name;
}