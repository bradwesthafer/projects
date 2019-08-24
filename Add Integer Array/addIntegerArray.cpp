#include <iostream>

#include <cmath>

int* addIntArray(int*, int, int*, int, int&);

int main() {
   int aLength;
   std::cout << "How many digits are in the first number?" << std::endl;
   std::cin >> aLength;
   while(aLength < 1) { 
      std::cout << "The first number must have at least 1 digit." << std::endl;
      std::cin >> aLength;
   }
   int* a = new int[aLength];
   for(int i = 0; i < aLength; i++) {
      int temp;
      std::cout << "Enter digit # " << i << " from left" << std::endl;
      std::cin >> temp;
      while(temp < 0 || temp > 9) {
         std::cout << "Digits must be positive integers between 0 and 9" << std::endl;
         std::cin >> temp;
      }
      a[i] = temp;
   }
   int bLength;
   std::cout << "How many digits are in the second number?" << std::endl;
   std::cin >> bLength;
   while(aLength < 1) { 
      std::cout << "The second number must have at least 1 digit." << std::endl;
      std::cin >> bLength;
   }
   int* b = new int[bLength];
   for(int i = 0; i < bLength; i++) {
      int temp;
      std::cout << "Enter digit # " << i << " from left" << std::endl;
      std::cin >> temp;
      while(temp < 0 || temp > 9) {
         std::cout << "Digits must be positive integers between 0 and 9" << std::endl;
         std::cin >> temp;
      }
      b[i] = temp;
   }
   int resultSize;
   int* result = addIntArray(a, aLength, b, bLength, resultSize);
   for(int i = 0; i < aLength; i++) {
      std::cout << a[i];
   }
   std::cout << " + ";
   for(int i = 0; i < bLength; i++) {
      std::cout << b[i];
   }
   std::cout << " = ";
   for(int i = 0; i < resultSize; i++) {
     std::cout << result[i];
   }
   std::cout << std::endl;
   delete[] result;
   delete[] a;
   delete[] b;
   return 0;
}

int* addIntArray(int* a, int aLength, int* b, int bLength, int& resultSize) {
   resultSize = std::max(aLength, bLength);
   int* result = new int[resultSize];
   bool carry = false;
   for(int i = resultSize - 1, j = aLength - 1, k = bLength - 1; i >= 0; i--) {
      int sum = (a[j] + b[k]);
      if(carry == true)
         sum++;
      if(sum > 9) {
         carry = true;
      }
      else {
         carry = false;
      }
      result[i] = sum % 10;
      j--;
      k--;
   }
   if(carry == true) {
      int* newresult = new int [resultSize + 1];
      newresult[0] = 1;
      for(int i = 1; i <= resultSize; i++) {
         newresult[i] = result[i - 1];
      }
      resultSize++;
      delete[] result;
      result = newresult;
   }
   return result;
}
