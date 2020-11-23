package jp.co.nissho_ele.handson.repository;

import java.util.Collections;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import io.quarkus.panache.common.Sort;
import jp.co.nissho_ele.handson.model.AddressModel;

@RequestScoped
public class AddressRepository {

    @Inject
    EntityManager em;

    /**
     * 住所情報を全て取得する
     * 
     * @return 住所情報
     */
    public List<AddressModel> findAll() {
        return AddressModel.listAll(Sort.by("zip_code"));
    }

    /**
     * 郵便番号に紐づく住所情報を取得する
     * 
     * @param zip_code
     * @return 住所情報
     */
    @SuppressWarnings("unchecked")
    public List<AddressModel> find(String zip_code) {
        Query query = em.createQuery("select a from AddressModel a where zip_code ='" + zip_code + "'");
        query.setHint("org.hibernate.cacheable", Boolean.TRUE);
        query.setHint("javax.persistence.query.timeout", 60000);
        return Collections.checkedList(query.getResultList(), AddressModel.class);
    }

    /**
     * 郵便番号に紐づく住所情報を取得する
     * 
     * @param zip_code
     * @return 住所情報
     */
    @SuppressWarnings("unchecked")
    public List<AddressModel> findNoCache(String zip_code) {
        // OCP4.6更新、リファクタリング実施後、無駄に早くなってしまい、このままだとハンズオンのシナリオ通りにいかないことが判明...
        // 強制的にスリープ処理をいれてみる
        em.getTransaction().begin();
        Query query = em.createQuery("select a from AddressModel a where zip_code ='" + zip_code + "'");
        query.setHint("org.hibernate.cacheable", Boolean.FALSE);
        query.setHint("javax.persistence.query.timeout", 60000);
        var list = Collections.checkedList(query.getResultList(), AddressModel.class);
        em.getTransaction().commit();
        return list;
    }

}