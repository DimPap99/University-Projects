#include <stdio.h>
#include <assert.h>

int main(int agc, char **arv){


    int* a;
    int b;
    b = 1999;

    a = &b;
    printf("%d\n", *a);


    char* p = "examp";
    printf("%c", *p);
    p++;
    printf("%c", *p);
    p++;
    printf("%c", *p);
    p++;
    printf("%c", *p);
    p++;
    printf("%c", *p);
    p++;  
    printf("%c", *p);
    assert(*p == 0);
    // int* pc, c;
    // c = 5;
    // pc = &c;
    // printf("%d", *pc);   // Output: 5

    return 0;
}