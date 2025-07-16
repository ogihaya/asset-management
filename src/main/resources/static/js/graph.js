let dataChart;
let currentType = "total-assets";
let currentAssetId = null;

document.addEventListener("DOMContentLoaded", function() {
    loadGraph();
    setupEventListeners();
});

function setupEventListeners() {
    const dataTypeSelect = document.getElementById("dataTypeSelect");

    dataTypeSelect.addEventListener("change", function() {
        updateAssetOptions();
        loadGraph();
    });
    

    const assetSelect = document.getElementById("assetSelect");

    assetSelect.addEventListener("change", function() {
        currentAssetId = assetSelect.value;
        loadGraph();
    });
    

    const updateGraphBtn = document.getElementById("updateGraphBtn");

    updateGraphBtn.addEventListener("click", function() {
        loadGraph();
    });
    
}

function updateAssetOptions() {
    const dataTypeSelect = document.getElementById("dataTypeSelect");
    const assetSelectContainer = document.getElementById("assetSelectContainer");
    const assetSelect = document.getElementById("assetSelect");

    if(dataTypeSelect.value === "specific-asset") {
        assetSelectContainer.style.display = "block";

        if(assetSelect.options.length > 0) {
            currentAssetId = assetSelect.options[0].value;
        }
    } else {
        assetSelectContainer.style.display = "none";
        currentAssetId = null;
        assetSelect.value = "";
    }
}

async function loadGraph() {
    try {
        const dataTypeSelect = document.getElementById("dataTypeSelect");
        currentType = dataTypeSelect.value;

        let url = `/api/graph/data?type=${currentType}`;
        if(currentType === "specific-asset" && currentAssetId) {
            url += `&assetId=${currentAssetId}`;
        }

        const response = await fetch(url);
        const data = await response.json();

        drawGraph(data.data, data.type);

    } catch (error) {
        console.error("グラフの読み込みに失敗しました:", error);
    }
}

function drawGraph(data, type) {
    const ctx = document.getElementById("dataChart").getContext("2d");

    if(dataChart) {
        dataChart.destroy();
    }

    const labels = data.map(item => item.month);
    const amounts = data.map(item => item.amount);

    const { title, color, backgroundColor } = getGraphConfig(type);

    document.getElementById("graphTitle").textContent = title;

    dataChart = new Chart(ctx, {
        type: "line",
        data: {
            labels: labels,
            datasets: [{
                label: title,//名前
                data: amounts,
                borderColor: color,//線の色
                backgroundColor: backgroundColor,//背景の色
                borderWidth: 3,//線の太さ
                fill: true,//塗りつぶし
                pointBackgroundColor: color,//ポイントの背景色
                pointBorderColor: '#ffffff',//ポイントの枠線色
                pointBorderWidth: 2,//ポイントの枠線の太さ
                pointRadius: 6,//ポイントの半径
                pointHoverRadius: 8,//ポイントのホバー時の半径
            }]
        },
        options: {
            responsive: true,//画面サイズに合わせてグラフを表示
            maintainAspectRatio: false,//グラフの縦横比を維持
            plugins: {
                legend: {
                    display: true,//凡例を表示
                    position: "top",//凡例の位置
                },
                tooltip: {
                    mode: "index",//ツールチップの表示位置
                    intersect: false,
                    callbacks: {
                        label: function(context) {
                            return title + ": " + context.parsed.y.toLocaleString() + "円";
                        }
                    }
                }
            },
            scales: {
                y: getYAxisConfig(amounts, type),
            },
            interaction: {
                mode: 'nearest',
                axis: 'x',
                intersect: false,
            }
        }
    });
}

function getYAxisConfig(amounts, type) {
    const maxAmount = Math.max(...amounts);
    const minAmount = Math.min(...amounts);
    const range = maxAmount - minAmount;

    if(type === "income") {
        return {
            beginAtZero: true,
            ticks: {
                callback: function(value) {
                    return value.toLocaleString() + "円";
                }
            }
        };
    }

    if (minAmount < 0) {
        return {
            beginAtZero: false,
            ticks: {
                callback: function(value) {
                    return value.toLocaleString() + "円";
                }
            },

            suggestedMax: maxAmount + range * 0.1,
            suggestedMin: minAmount - range * 0.1,
        };
    } else {
        return {
            beginAtZero: true,
            ticks: {
                callback: function(value) {
                    return value.toLocaleString() + "円";
                }
            }
        };
    }
}

function getGraphConfig(type) {
    const config = {
        "income": {
            title: "収入",
            color: "#48bb78",
            backgroundColor: "rgba(72, 187, 120, 0.1)",
        },
        "total-assets": {
            title: "資産合計",
            color: "#667eea",
            backgroundColor: "rgba(102, 126, 234, 0.1)",
        },
        "specific-asset": {
            title: "特定資産",
            color: "#ed8936",
            backgroundColor: "rgba(237, 137, 54, 0.1)",
        }
    };

    return config[type] || config["income"];
}

window.addEventListener("error", function(e) {
    console.error("JavaScriptエラー:", e.error);
});
