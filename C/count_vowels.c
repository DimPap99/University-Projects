#include <stdio.h>
#include <string.h>
#include <stdlib.h>


struct Vowels_Found {
    int a;
    int e;
    int i;
    int o;
    int y;
    int u;
    int other;
}   vowels_found = {0, 0, 0, 0, 0, 0};

typedef struct Vowels_Found Vowels_Struct;


enum vowels {a='a', e = 'e', i = 'i', o = 'o', y = 'y', u = 'u'};

char* inputString(FILE* fp, size_t size){

    char* str;
    int ch;
    size_t len = 0;
    str = realloc(NULL, sizeof(char)* size); //get starting size
    if(!str) return str;
    while(EOF != (ch=fgetc(fp)) && ch != '\n'){
        str[len++] = ch;
        if (len == size){
            str = realloc(str, sizeof(char)*(size+=16));
            if (!str) return str;
        }
    }
    str[len++]='\0';
    return realloc(str, sizeof(char)*len);
}

Vowels_Struct check_for_vowels(char* word){
    Vowels_Struct struct_vowels = vowels_found;
    enum vowels letter;
    for(int index = 0; index< strlen(word); index++){
        letter = word[index];
        switch(letter){
            case a:
                struct_vowels.a += 1;
                break;
            case e:
                struct_vowels.e += 1;
                break;
            case i:
                struct_vowels.i += 1;
                break;
            case o:
                struct_vowels.o += 1;
                break;
            case y:
                struct_vowels.y += 1;
                break;
            case u:
                struct_vowels.u += 1;
                break;
            default:
                struct_vowels.other += 1;
        }
    }
    return struct_vowels;
}


void print_struct(Vowels_Struct vowels){
    printf("Letter a: %d\n", vowels.a);
    printf("Letter e: %d\n", vowels.e);
    printf("Letter i: %d\n", vowels.i);
    printf("Letter o: %d\n", vowels.o);
    printf("Letter u: %d\n", vowels.u);
    printf("Letter y: %d\n", vowels.y);
    printf("Other letters: %d\n", vowels.other);
    
}



int main(int arc, char **argv){
    
    char* word;   
    printf("Give a word as input\n");
    word = inputString(stdin, 10);
    printf("The word is: %s\n", word);
    print_struct(check_for_vowels(word));
    free(word);
    return 0;
}