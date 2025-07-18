document.addEventListener('DOMContentLoaded', function() {

    document.getElementById('addAssetButton').addEventListener('click', function() {
        const assetModal = document.getElementById('addAssetModal');
        assetModal.style.display = 'block';

        const firstInput = assetModal.querySelector('input[type="text"]');
        firstInput.focus();
    });

    document.getElementById('closeAssetButton').addEventListener('click', function() {
        // モーダル要素を取得
        const assetModal = document.getElementById('addAssetModal');
    
        // モーダルを非表示（display: noneに設定）
        assetModal.style.display = 'none';        
        // フォームをリセット（入力内容をクリア）
        const form = document.getElementById('addAssetForm');
        form.reset();
    });

    document.getElementById('addInvestButton').addEventListener('click', function() {
        const investModal = document.getElementById('addInvestModal');
        investModal.style.display = 'block';

        const firstInput = investModal.querySelector('input[type="text"]');
        firstInput.focus();
    });

    document.getElementById('closeInvestButton').addEventListener('click', function() {
        const investModal = document.getElementById('addInvestModal');
        investModal.style.display = 'none';
        const form = document.getElementById('addInvestForm');
        form.reset();
    });
});