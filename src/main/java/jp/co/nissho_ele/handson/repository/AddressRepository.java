package jp.co.nissho_ele.handson.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import io.quarkus.panache.common.Sort;

import jp.co.nissho_ele.handson.model.AddressModel;

@ApplicationScoped
public class AddressRepository {

    public List<AddressModel> findAll() {

        return AddressModel.listAll(Sort.by("zip_code"));

    }
}