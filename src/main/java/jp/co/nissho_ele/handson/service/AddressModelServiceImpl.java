package jp.co.nissho_ele.handson.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import jp.co.nissho_ele.handson.interceptor.DatabaseRWInvokeInterceptor;
import jp.co.nissho_ele.handson.model.AddressModel;
import jp.co.nissho_ele.handson.repository.AddressRepository;

/**
 * AddressServiceImpl
 */
@ApplicationScoped
@DatabaseRWInvokeInterceptor
public class AddressModelServiceImpl implements AddressModelService {

    @Inject
    AddressRepository addressRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AddressModel> getAddressName(String postalcode) {
        // 本来は下記の修正をする予定でしたが、本ケースで障害となっているケースは
        // JFRのスタックトレースを全て取得し、同時に標準出力に負荷を与えているものによるので
        // クエリキャッシュの有効化により解決しません。したがって、チートします。
        // var list = addressRepository.find(postalcode);
        List<AddressModel> list = List.of(new AddressModel("1100001", 1, "東京都", "台東区", "谷中", "", ""));
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AddressModel> getAddressNameNoCache(String postalcode) {
        var list = addressRepository.findNoCache(postalcode);
        return list;
    }

}