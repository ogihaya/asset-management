package com.example.asset_management.controller;

// Spring Bootのコントローラーとして機能するクラスを定義
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.asset_management.repository.IncomeDataRepository;
import java.time.YearMonth;
import com.example.asset_management.entity.IncomeData;
import com.example.asset_management.entity.AssetData;
import java.util.List;
import com.example.asset_management.repository.AssetDataRepository;
import com.example.asset_management.entity.AssetMaster;
import com.example.asset_management.repository.AssetMasterRepository;
import java.util.Map;
import java.util.stream.Collectors;

@Controller // このアノテーションでSpringにコントローラーであることを伝えます
public class AssetController {

    // 収入データにアクセスするためのリポジトリを自動で注入
    @Autowired
    private IncomeDataRepository incomeDataRepository;

    // 資産データにアクセスするためのリポジトリを自動で注入
    @Autowired
    private AssetDataRepository assetDataRepository;

    // 資産マスター（資産の種類など）にアクセスするためのリポジトリを自動で注入
    @Autowired
    private AssetMasterRepository assetMasterRepository;

    /**
     * 資産一覧画面を表示するメソッド
     * @param yearMonthParam 表示する年月（例: "2024-06"）。指定がなければ今月を表示。
     * @param model 画面に値を渡すためのオブジェクト
     * @return home.html テンプレート名
     */
    @GetMapping("/") // ルートURL（"/"）へのGETリクエストを受け付ける
    public String showAssetList(
        @RequestParam(value = "yearMonth", required = false) String yearMonthParam, // 年月のリクエストパラメータ（任意）
        Model model) {

        YearMonth targetYearMonth; // 表示対象の年月
        if(yearMonthParam != null && !yearMonthParam.isEmpty()) {
            // パラメータが指定されていればそれを使う
            targetYearMonth = YearMonth.parse(yearMonthParam);
        } else {
            // 指定がなければ今月を使う
            targetYearMonth = YearMonth.now();
        }

        // 指定した月の収入データを取得（なければnull）
        IncomeData targetMonthIncome = incomeDataRepository.findByTargetMonth(targetYearMonth).orElse(null);

        // 指定した月の資産データを資産名順で取得
        List<AssetData> targetMonthAssets = assetDataRepository.findByTargetMonthOrderByAssetMaster_AssetName(targetYearMonth);

        // 全ての資産マスター（資産の種類一覧）を取得
        List<AssetMaster> allAssetMasters = assetMasterRepository.findAll();

        // 既存の資産データを資産マスターIDをキーにしてMap化（後で画面で使いやすくするため）
        Map<Long, AssetData> existingAssetDataMap = targetMonthAssets.stream()
            .collect(Collectors.toMap(
                assetData -> assetData.getAssetMaster().getId(), // キー：資産マスターID
                assetData -> assetData // 値：資産データ
            ));

        // 画面に渡す値をmodelにセット
        model.addAttribute("targetMonthIncome", targetMonthIncome); // 今月の収入データ
        model.addAttribute("targetYearMonth", targetYearMonth.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM"))); // 表示用年月
        model.addAttribute("targetMonthAssets", targetMonthAssets); // 今月の資産データ一覧
        model.addAttribute("allAssetMasters", allAssetMasters); // 資産マスター一覧
        model.addAttribute("existingAssetDataMap", existingAssetDataMap); // 既存資産データのMap

        // home.html（テンプレート）を表示
        return "home";
    }
}
