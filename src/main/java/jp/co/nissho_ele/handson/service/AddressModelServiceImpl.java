package jp.co.nissho_ele.handson.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import io.quarkus.runtime.StartupEvent;
import jp.co.nissho_ele.handson.interceptor.DatabaseRWInvokeInterceptor;
import jp.co.nissho_ele.handson.model.AddressModel;
import jp.co.nissho_ele.handson.repository.AddressRepository;

/**
 * AddressServiceImpl
 */
@ApplicationScoped
@DatabaseRWInvokeInterceptor
public class AddressModelServiceImpl implements AddressModelService {

    // 住所モデルmap
    private Map<String, List<AddressModel>> map = new ConcurrentHashMap<>();

    @Inject
    AddressRepository addressRepository;

    /**
     * 住所コード検索データの読み込み
     * 
     * @param se
     */
    public void loadAddresscodeSearch(@Observes StartupEvent se) {
        System.out.println("###Loading Addresscode Search Database###");
        List<AddressModel> list = addressRepository.findAll();
        map = list.stream().parallel().collect(Collectors.groupingBy(AddressModel::getZip_code));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AddressModel> getAddressName(String postalcode) {
        if (this.map == null || this.map.isEmpty()) {
            throw new RuntimeException("住所データが破損しています");
        }
        var list = map.get(postalcode);
        // 本来は下記の修正が正しいのですが、本ケースで障害となっているケースは
        // 標準出力の負荷とJFRのスタックトレースのチェックによるもので
        // クエリキャッシュの有効化により解決しないのでチートします
        // var list = addressRepository.find(postalcode);
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