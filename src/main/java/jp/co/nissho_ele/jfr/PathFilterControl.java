package jp.co.nissho_ele.jfr;

import java.util.Set;
import java.util.regex.Pattern;

import jdk.jfr.SettingControl;

/**
 * JFRの設定コントロール<br/>
 * 
 * 設定値が変更されたときに呼び出される。<br/>
 * 通常は、記録の開始または停止時に呼ばれます。
 */
public class PathFilterControl extends SettingControl {

    private Pattern pattern = Pattern.compile(".*");

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(String value) {
        System.out.println("SetValue #### " + value);
        this.pattern = Pattern.compile(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String combine(Set<String> values) {
        System.out.println("Combine ##### " + values);
        return String.join("|", values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        System.out.println("GetValue ##### " + pattern);
        return pattern.toString();
    }

    /**
     * 条件チェック<br/>
     * 
     * URLが指定された条件に合致したときにのみJFRに記録する
     */
    public boolean matches(String s) {
        System.out.println("Matches #### " + pattern + " " + s);
        return pattern.matcher(s).matches();
    }
}