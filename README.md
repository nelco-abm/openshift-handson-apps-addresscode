# 住所コード検索API Project

このプロジェクトは住所コード検索APIのハンズオン、デモ用に作成したアプリケーションです。

## 開発環境

### VSCode Remote Container

このプロジェクトはVSCode Remote Container形式のWorkspaceをサポートしています。
VSCodeで開くことでコンテナ上にJDKを含む開発環境を自動的に構築します。

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

### native executableを作成して起動する
  
native executableは次のコマンドで作成できます。  
`./mvnw package -Pnative`.

もしもGraalVMをインストールしていない場合コンテナ環境でビルドしたnative executableを実行することも可能です。  
次のコマンドでjarファイルを作成して...    
 `sudo ./mvnw package -Pnative -Dquarkus.native.container-build=true`.

次のコマンドでnative executableを実行してください。   　　  
`sudo ./target/handson-1.0-SNAPSHOT-runner`
  
native executablesの構築方法についてもっと知りたい場合は次の[リンク](https://quarkus.io/guides/building-native-image)にアクセスしてください。  
  
## 各種機能について

### Quarkusについて

このプロジェクトはQuarkusを使用しています。  
QuarkusはすーぱーそにっくでさぶあとみっくなJavaフレームワークです。  
もしもQuakursについてさらに知りたい場合、下記のwebsiteをご覧ください。　 
https://quarkus.io/

### mvnwについて
mvnwはmvnのwrapperクラスで指定されたmavenのバージョンで処理を行ってくれます。    
初期状態ではmvnwは入っていなかったので下記コマンドで導入しました。  
`mvn -N io.takari:maven:0.7.7:wrapper`

## 謝辞
このアプリケーションはGunnar Morling氏による下記の解説記事を参考にしながら作成しました。  
この場をお借りしてお礼申し上げます。  
https://www.morling.dev/blog/rest-api-monitoring-with-custom-jdk-flight-recorder-events/
