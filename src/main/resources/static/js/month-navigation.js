// 月ナビゲーション機能
async function changeMonth(direction) {
    try {
        // 現在の月を取得
        const currentMonthElement = document.getElementById('currentMonth');
        const currentYearMonth = currentMonthElement.textContent;
        
        // 前月または翌月を計算
        let targetYearMonth;
        if (direction === 'previous') {
            targetYearMonth = calculatePreviousMonth(currentYearMonth);
        } else if (direction === 'next') {
            targetYearMonth = calculateNextMonth(currentYearMonth);
        } else {
            // 直接年月が渡された場合
            targetYearMonth = direction;
        }
        
        console.log('月切り替え開始:', targetYearMonth);
        
        // サーバーからHTMLフラグメントを取得
        const url = `/api/month-fragment?yearMonth=${targetYearMonth}`;
        console.log('リクエストURL:', url);
        
        const response = await fetch(url);
        console.log('レスポンスステータス:', response.status);
        console.log('レスポンスヘッダー:', response.headers);
        
        if (!response.ok) {
            const errorText = await response.text();
            console.error('サーバーエラー詳細:', errorText);
            throw new Error(`サーバーエラーが発生しました: ${response.status} ${response.statusText}`);
        }
        
        const html = await response.text();
        console.log('取得したHTML:', html);
        
        // 特定の部分のみを更新
        document.getElementById('monthContent').innerHTML = html;
        
        // 現在月の表示を更新
        document.getElementById('currentMonth').textContent = targetYearMonth;
        
        // URLを更新（ブラウザの戻るボタン対応）
        updateURL(targetYearMonth);
        
        // グラフも更新
        if (typeof loadGraph === 'function') {
            loadGraph();
        }
        
        console.log('月切り替え完了');
        
    } catch (error) {
        console.error('月データの取得に失敗しました:', error);
        showError('データの取得に失敗しました: ' + error.message);
    } 
}

// 前月を計算する関数
function calculatePreviousMonth(yearMonth) {
    const [year, month] = yearMonth.split('-').map(Number);
    let newYear = year;
    let newMonth = month - 1;
    
    if (newMonth === 0) {
        newMonth = 12;
        newYear = year - 1;
    }
    
    return `${newYear}-${String(newMonth).padStart(2, '0')}`;
}

// 翌月を計算する関数
function calculateNextMonth(yearMonth) {
    const [year, month] = yearMonth.split('-').map(Number);
    let newYear = year;
    let newMonth = month + 1;
    
    if (newMonth === 13) {
        newMonth = 1;
        newYear = year + 1;
    }
    
    return `${newYear}-${String(newMonth).padStart(2, '0')}`;
}

function showError(message) {
    const monthContent = document.getElementById('monthContent');
    monthContent.innerHTML = `<div class="error">${message}</div>`;
}

function updateURL(yearMonth) {
    const url = new URL(window.location);
    url.searchParams.set('yearMonth', yearMonth);
    history.pushState({yearMonth: yearMonth}, '', url);
}

// ブラウザの戻る・進むボタン対応
window.addEventListener('popstate', function(event) {
    if (event.state && event.state.yearMonth) {
        changeMonth(event.state.yearMonth);
    }
});

// ページ読み込み時の初期化
document.addEventListener('DOMContentLoaded', function() {
    // 現在のURLからyearMonthパラメータを取得
    const urlParams = new URLSearchParams(window.location.search);
    const currentYearMonth = urlParams.get('yearMonth');
    
    if (currentYearMonth) {
        // 初期状態を設定
        history.replaceState({yearMonth: currentYearMonth}, '', window.location);
    }
}); 