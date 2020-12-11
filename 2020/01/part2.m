I = load('input.txt');
A = I + I';
M = I * I';

for i = 1:length(I)
  AS(:, :, i) = I(i) + A;
endfor

for i = 1:length(I)
  MS(:, :, i) = I(i) * M;
endfor

sprintf("%.0f", MS(AS == 2020)(1, 1))