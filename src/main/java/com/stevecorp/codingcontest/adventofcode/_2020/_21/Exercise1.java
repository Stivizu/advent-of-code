package com.stevecorp.codingcontest.adventofcode._2020._21;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise1 {

    private static final Function<String, Food> MAPPER = inputLine -> {
        final String[] splitInput = inputLine.replaceAll("[,)]", "").split(" \\(contains ");
        return new Food(
                Arrays.asList(splitInput[0].split(" ")),
                Arrays.asList(splitInput[1].split(" "))
        );
    };

    public static void main(String[] args) {
        final List<Food> input = parseFile("2020/21/input.txt", MAPPER);

        final Set<String> allIngredients = new HashSet<>();
        final Set<String> allAllergies = new HashSet<>();
        final Map<String, Integer> ingredientOccurrences = new HashMap<>();
        final Map<String, Set<String>> intermediary = new HashMap<>();

        input.stream()
                .peek(food -> allIngredients.addAll(food.ingredients))
                .peek(food -> allAllergies.addAll(food.allergies))
                .map(food -> food.ingredients)
                .forEach(ingredientList -> ingredientList.forEach(ingredient -> ingredientOccurrences.merge(ingredient, 1, Integer::sum)));

        allAllergies.forEach(allergy -> intermediary.put(allergy, new HashSet<>(allIngredients)));

        for (final Food food : input) {
            for (final String allergy : food.allergies) {
                for (final String ingredient : allIngredients) {
                    if (!food.ingredients.contains(ingredient)) {
                        intermediary.get(allergy).remove(ingredient);
                    }
                }
            }
        }

        final int numberOfIngredientsWithoutAllergens = allIngredients.stream()
                .filter(ingredient -> intermediary.values().stream()
                        .noneMatch(intermediaryValue -> intermediaryValue.contains(ingredient)))
                .mapToInt(ingredientOccurrences::get)
                .sum();

        System.out.println("The number of ingredients that cannot possible contain any of the allergens is: " + numberOfIngredientsWithoutAllergens);
    }

    private static final class Food {
        List<String> ingredients;
        List<String> allergies;

        public Food(final List<String> ingredients, final List<String> allergies) {
            this.ingredients = ingredients;
            this.allergies = allergies;
        }
    }
}
