#!/bin/bash

# 資産管理アプリケーション デプロイスクリプト

echo "=== 資産管理アプリケーション デプロイ開始 ==="

# 1. Mavenでビルド
echo "1. Mavenでビルド中..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "ビルドに失敗しました"
    exit 1
fi

echo "ビルド完了"

# 2. 環境変数の確認
echo "2. 環境変数の確認..."
if [ -z "$DB_PASSWORD" ]; then
    echo "警告: DB_PASSWORD環境変数が設定されていません"
    echo "本番環境では必ず設定してください"
fi

if [ -z "$SPRING_PROFILES_ACTIVE" ]; then
    echo "SPRING_PROFILES_ACTIVEが設定されていません。dev環境で起動します"
    export SPRING_PROFILES_ACTIVE=dev
fi

# 3. アプリケーションの起動
echo "3. アプリケーションを起動中..."
echo "プロファイル: $SPRING_PROFILES_ACTIVE"

java -jar target/asset-management-0.0.1-SNAPSHOT.jar \
    --spring.profiles.active=$SPRING_PROFILES_ACTIVE

echo "=== デプロイ完了 ===" 