#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

//appends a character to the end of an array
void append(char array[], char character){
    int len = strlen(array);
    array[len] = character;
    array[len+1] = '\0'; //add the null char to signify it's end
}

char* reverse(char array[]){
    size_t l = strlen(array);
    char* reversed_array = (char*)malloc(((l + 1) * sizeof(char))); //allocate adequate space
    reversed_array[l] = '\0'; //add null char 
    for(int i = 0; i < l; i++){
        reversed_array[i] = array[l - 1 - i];
    }
    strcpy(array, reversed_array);//copy to the initial array we gave as arg in order to free the memory
    free(reversed_array);
    return array;
}

int binary_to_decimal(char* binary_number ){

    int decimal = 0;
    int power;
    int num;
    char digit;
    for (int i = 0; i < strlen(binary_number); i++){
        power = pow(2, strlen(binary_number) - i - 1); //begin from the MSB and go to LSB
        digit = binary_number[i];
        num = atoi(&digit);
        decimal += num * power;
    }
    
    return decimal;
}

char* decimal_to_binary(char* c, int decimal_number){

                  
    int div_val = decimal_number/2;
    int mod_val = decimal_number%2;
     
    while (decimal_number != 0){
        div_val = decimal_number/2;
        mod_val = decimal_number%2;
        append(c, mod_val + '0'); //add '0' to convert int into char
        decimal_number = decimal_number / 2;
        
    }
    
    return reverse(c);
}


int main(int argc, char **argv){

    int number = 12;
    printf("Choose an option: \n");
    printf("1. Convert Decimal to Binary \n");
    printf("2. Convert Binary to Decimal \n");
    char c = getchar();
    printf("You pressed the character: %c \n", c);

    switch (c)
    {
    case '1':
        printf("Give a decimal number as input ");
        int number;
        
        scanf("%d", &number);
        char* c = (char*)malloc(sizeof(number));
        char digit;
        strncpy(c, "88666782921", sizeof(int)); // null-terminated if the length of the C string in source is less than num.
        strncpy(c, "", sizeof(int));  
        printf("You gave %d as input...\n", number);
        printf("It is converted into: %s\n", decimal_to_binary(c, number));
        free(c);
        break;
    
    case '2':
        printf("Give a binary number as input: \n");
        char *binary_string;
        binary_string =  (char *) malloc(10);
        scanf("%s", binary_string);
        printf("You gave as input... %s \n", binary_string);
        printf("It is converted into: %d\n", binary_to_decimal(binary_string));
        free(binary_string);
        break;
    default:
        printf("Invalid Choice!");
    }


    return 0;
}