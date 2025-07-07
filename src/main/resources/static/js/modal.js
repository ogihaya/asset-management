function openAddAssetModal() {
    // モーダル要素を取得
    const modal = document.getElementById('addAssetModal');
    
    modal.style.display = 'block';
        
    // フォーカスを最初の入力フィールドに移動（ユーザビリティ向上）
    const firstInput = modal.querySelector('input[type="text"]');
    firstInput.focus();
}


function closeAddAssetModal() {
    // モーダル要素を取得
    const modal = document.getElementById('addAssetModal');
 
    // モーダルを非表示（display: noneに設定）
    modal.style.display = 'none';        
    // フォームをリセット（入力内容をクリア）
    const form = document.getElementById('addAssetForm');
    form.reset();
}