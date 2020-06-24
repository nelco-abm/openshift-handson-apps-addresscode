# 住所コード検索API project

## 起動方法

### dev modeでアプリケーションを起動する

ライブコーディングを行うにはdev modeでアプリケーションを起動してください。　　
`./mvnw quarkus:dev`

### アプリケーションをパッケージングして起動する

アプリケーションは次のコマンドでパッケージングできます。　　
 `./mvnw package`.

このコマンドはファイル `handson-1.0-SNAPSHOT-runner.jar` を `/target` ディレクトリに出力します。  
  
`target/lib`ディレクトリにコピーされる依存ライブラリである_über-jar_ではないことにご注意ください。
  
アプリケーションは次のコマンドで起動します。　　
 `java -jar target/handson-1.0-SNAPSHOT-runner.jar`.

### native executableの作成

native executableは次のコマンドで作成できます。　　
`./mvnw package -Pnative`.

もしもGraalVMをインストールしていない場合コンテナ環境でビルドしたnative executableを実行することも可能です。　　  
次のコマンドでjarファイルを作成して...
 `sudo ./mvnw package -Pnative -Dquarkus.native.container-build=true`.

次のコマンドでnative executableを実行してください。 　　
`sudo ./target/handson-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

### Dockerイメージを作成、pushする

[QUARKUS - CONTAINER IMAGES](https://quarkus.io/guides/container-image)により、
Quarkusのビルドとコンテナイメージの作成、pushまでを一元的に行う

```shell
sudo ./mvnw clean package -Dquarkus.container-image.build=true  -Dquarkus.container-image.push=true -Dquarkus.container-image.username=forhandson -Dquarkus.container-image.password=<password>
```

## 各種機能について

## Quarkusについて

このプロジェクトはQuarkusを使用しています。  
QuarkusはすーぱーそにっくでさぶあとみっくなJavaフレームワークです。  
もしもQuakursについてさらに知りたい場合、下記のwebsiteをご覧ください。　  
https://quarkus.io/ .


## mvnwについて
mvnwはmvnのwrapperクラスで指定されたmavenのバージョンで処理を行ってくれます。  
初期状態ではmvnwは入っていなかったので下記コマンドで導入しました。  
`mvn -N io.takari:maven:0.7.7:wrapper`
