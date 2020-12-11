load input.txt;

P = nchoosek(input, 2);
S = prod(P(sum(P, 2) == 2020, :));

sprintf('%.0f', S)