package com.stevecorp.codingcontest.adventofcode._2020._21;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

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
        final Map<String, Set<String>> intermediary = new HashMap<>();
        final Set<String> resolvedAllergies = new HashSet<>();

        input.stream()
                .peek(food -> allIngredients.addAll(food.ingredients))
                .forEach(food -> allAllergies.addAll(food.allergies));

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

        while (resolvedAllergies.size() < allAllergies.size()) {
            allAllergies.stream()
                    .filter(allergy -> intermediary.get(allergy).size() == 1 && !resolvedAllergies.contains(allergy))
                    .peek(resolvedAllergies::add)
                    .map(allergy -> new String[] {allergy, intermediary.get(allergy).iterator().next()})
                    .forEach(resolvedAllergy -> allAllergies.stream()
                            .filter(allergyLookup -> !resolvedAllergy[0].equals(allergyLookup))
                            .forEach(allAllergyLookup -> intermediary.get(allAllergyLookup).remove(resolvedAllergy[1])));
        }

        final String ingredientList = resolvedAllergies.stream().sorted()
                .map(allergy -> intermediary.get(allergy).iterator().next())
                .collect(Collectors.joining(","));

        System.out.println("The canonical dangerous ingredient list: " + ingredientList);
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
