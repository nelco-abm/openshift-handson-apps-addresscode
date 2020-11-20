package jp.co.nissho_ele.handson.service;

import java.util.List;

import jp.co.nissho_ele.handson.model.AddressModel;

/**
 * AddressService
 */
public interface AddressModelService {

    /**
     * 住所コード名の取得
     * 
     * @param postalcode 住所コード
     * @throws RuntimeException("空データ") 検索該当なし
     * @throws RuntimeException("形式不正") フォーマットが異なる
     * @throws RuntimeException         破損データ
     * @return 住所名
     */
    public List<AddressModel> getAddressName(String postalcode);

    /**
     * 住所コード名の取得
     * 
     * @param postalcode 住所コード
     * @throws RuntimeException("空データ") 検索該当なし
     * @throws RuntimeException("形式不正") フォーマットが異なる
     * @throws RuntimeException         破損データ
     * @return 住所名
     */
    public List<AddressModel> getAddressNameNoCache(String postalcode);
}