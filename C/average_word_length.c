// Calculates average word length from user input.

#include <stdio.h>
#include <ctype.h>

int main(void) {
    /*
     input
     
     count characters
     count spaces
     word count = spaces + 1
     average = characters / words
     */
    int ch, chars = 0, spaces = 0, word_count = 0;
    float average = 0.0f;
    
    printf("Enter a sentence: ");
    while ((ch = getchar()) != '\n' && ch != EOF) {
        if (ch != ' ') {
            chars += 1;
        } else {
            spaces += 1;
        }
    }
    printf("chars: %d\n", chars);
    printf("spaces: %d\n", spaces);

    word_count = spaces + 1;
    printf("word count: %d\n", word_count);
    average = (float)chars / (float)word_count;
    
    printf("Average word count: %.1f\n", average);
    
    return 0;
}
