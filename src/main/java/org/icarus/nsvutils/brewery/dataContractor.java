package org.icarus.nsvutils.brewery;

import com.dre.brewery.Brew;
import com.dre.brewery.api.BreweryApi;
import com.dre.brewery.recipe.*;
import com.dre.brewery.integration.item.BreweryPluginItem;
import com.dre.brewery.utility.Tuple;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.*;

import static java.lang.Math.log;
import static java.lang.Math.min;

public class dataContractor {
    public static @Nullable Brew getBrew(ItemStack item) {
        return Brew.get(item);
    }

    public static int getQuality(Brew brew) {
        return brew.getQuality();
    }

    public static @Nullable String getRecipeName(Brew brew) {
        BRecipe recipe = brew.getCurrentRecipe();
        if (recipe != null) {
            return recipe.getRecipeName();
        }
        return null;
    }

    public static double getAgingTime(Brew brew) {
        return (double) brew.getAgeTime();
    }

    // 单位为分钟
    public static int getCookingTime(Brew brew) {
        return brew.getIngredients().getCookedTime();
    }

    public static int getDistillRun(Brew brew) {
        return (int) brew.getDistillRuns();
    }

    // 如果不存在，那么 distill_time 就是0
    public static int getDistillTime(Brew brew) {
        int time = 0;
        BRecipe recipe = brew.getCurrentRecipe();
        if (recipe == null) return time;
        return recipe.getDistillTime();
    }

    public static @Nullable List<Ingredient> getIngredients(Brew brew) {
        return brew.getIngredients().getIngredientList();
    }

    /*
    DFS，会一直往下搜索寻找直到不可分的原版材料
    */
    public static @Nullable HashMap<Material, Integer> getVanillaIng(Brew brew) {
        Queue<Tuple<Brew, Integer>> brewQueue = new ArrayDeque<>();
        HashMap<Material, Integer> materialMap = new HashMap<>();
        Set<String> visited = new HashSet<>();
        brewQueue.add(new Tuple<>(brew, 1));
        while (!brewQueue.isEmpty()) {
            Tuple<Brew, Integer> current = brewQueue.remove();
            Brew currentBrew = current.first();
            int brewAmount = current.second();
            BRecipe recipe = currentBrew.getCurrentRecipe();
            if (recipe == null) continue;
            List<Ingredient> ingredients = getIngredients(currentBrew);
            if (ingredients == null || !visited.add(recipe.getRecipeName())) continue;
            for (Ingredient ingredient : ingredients) {
                if (ingredient instanceof SimpleItem) {
                    Material material = ((SimpleItem) ingredient).getMaterial();
                    Integer amount = ingredient.getAmount() * brewAmount;
                    materialMap.merge(material, amount, Integer::sum);
                } else if (ingredient instanceof BreweryPluginItem) {
                    String itemId = ((BreweryPluginItem) ingredient).getItemId();
                    BRecipe subrecipe = BRecipe.getById(itemId);
                    if (subrecipe == null) {
                        subrecipe = BRecipe.getMatching(itemId);
                    }
                    if (subrecipe == null) {
                        continue;
                    }
                    Brew item = BreweryApi.createBrew(subrecipe, 10);
                    brewQueue.add(new Tuple<>(item, ingredient.getAmount()));
                }
            }
        }
        return materialMap;
    }

    public static double calculatePrice(Brew brew, HashMap<Material, Double> priceTable) {
        // 本小姐就大发慈悲的给你一块钱吧
        double ingredientPrice = 1.0;
        HashMap<Material, Integer> materialMap = getVanillaIng(brew);
        // 这里写一个 early-exit
        if (materialMap == null || materialMap.isEmpty()) return ingredientPrice;
        BRecipe recipe = brew.getCurrentRecipe();

        // 顺手获取一下材料种类和个数
        int materialCount = 0;
        int materialAmount = 0;

        for (Map.Entry<Material, Integer> entry : materialMap.entrySet()) {
            // 顺手的事
            materialCount += 1;
            materialAmount += entry.getValue();

            // 不在价目表进下一次循环
            if (priceTable.get(entry.getKey()) == null) continue;
            ingredientPrice += priceTable.get(entry.getKey()) * entry.getValue();
        }
        // 复杂度乘子
        double complexityAmplifier = (1.0 + 0.5 * log(1 + materialCount))
                * (1.0 + 0.3 * log(1 + materialAmount));
        ingredientPrice *= complexityAmplifier;

        // 困难度乘子
        Integer difficulty = recipe.getDifficulty();
        double difficultyAmplifier = 1.0 + ((double) (difficulty * difficulty) / 80);
        ingredientPrice *= difficultyAmplifier;

        // 加工乘子
        int distillTime = getDistillTime(brew) / 20;
        int distillRuns = getDistillRun(brew);
        int cookingTime = getCookingTime(brew);
        double agingTime = getAgingTime(brew);
        double processingAmplifier = 1.0 + min(cookingTime / 200, 1) * 0.05 + min(agingTime / 30, 1) * 0.05
                + min(distillTime * distillRuns / 600, 1) * 0.02;
        ingredientPrice *= processingAmplifier;

        // 质量乘子
        int quality = getQuality(brew);
        double qualityAmplifier = 1 + (quality - 5) * 0.1;
        ingredientPrice *= qualityAmplifier;

        return ingredientPrice;
    }
}
