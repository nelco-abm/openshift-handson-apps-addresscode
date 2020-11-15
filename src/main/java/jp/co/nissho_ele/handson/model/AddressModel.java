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

    /**
     * 郵便番号7桁
     */
    @Id
    private String zip_code;

    /**
     * 区分
     */
    private Integer zip_type;

    /**
     * 都道府県名
     */
    private String pref;

    /**
     * 市区町村名
     */
    private String city;

    /**
     * 町域名(大字)
     */
    private String town;

    /**
     * 小字名、丁目、番地など
     */
    private String street;

    /**
     * 大口事業所名
     */
    private String name;
}