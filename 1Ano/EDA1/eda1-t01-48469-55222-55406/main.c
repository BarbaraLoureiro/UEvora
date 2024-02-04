#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "fatal.h"

#define TABLE_SIZE 100003
#define N 4

typedef struct Entry {
    char *word;
    struct Entry *next;
} Entry;


typedef struct HashTable {
    Entry *table[TABLE_SIZE];
    int count;  
} HashTable;

unsigned int hash(char *word) {
    unsigned int hash = 0;
    while (*word) {
        hash = (hash << 5) + *word++;
    }
    return hash % TABLE_SIZE;
}

HashTable *createHashTable() {
    HashTable *ht = calloc(1, sizeof(HashTable));
    ht->count = 0;  
    return ht;
}

void insert(HashTable *ht, char *word) {
    unsigned int h = hash(word);
    Entry *new_entry = malloc(sizeof(Entry));
    new_entry->word = strdup(word);
    new_entry->next = ht->table[h];
    ht->table[h] = new_entry;
   if (strlen(word) > 2) {  
    ht->count++;
    }
}

int search(HashTable *ht, char *word) {
    unsigned int h = hash(word);
    Entry *entry = ht->table[h];
    while (entry != NULL) {
        if (strcmp(entry->word, word) == 0) {
            return 1;
        }
        entry = entry->next;
    }
    return 0;
}

void loadDictionary(HashTable *ht, char *filename) {
    char word[256];
    FILE *file = fopen(filename, "r");
    if (!file) {
        perror("Failed to open dictionary file");
        exit(1);
    }
    int count = 0;
    while (fscanf(file, "%s", word) != EOF ) {
        insert(ht, word);
        count++;
    }
    printf("lidas %d palavras do dicionário\n", count);
    int distinctPrefixes = calculateDistinctPrefixes(ht);
    printf("Encontrados %d prefixos distintos\n", distinctPrefixes);

    fclose(file);
}

void loadBoggle(char boggle[4][4], char *filename) {
    FILE *file = fopen(filename, "r");
    if (!file) {
        perror("Failed to open boggle file");
        exit(1);
    }
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            fscanf(file, " %c", &boggle[i][j]);
        }
    }
    fclose(file);
}

int isSafe(int x, int y, int visited[4][4]) {
    return (x >= 0 && x < 4 && y >= 0 && y < 4 && !visited[x][y]);
}

int calculateDistinctPrefixes(HashTable *ht) {
    int count = 0;
    for (int i = 0; i < TABLE_SIZE; i++) {
        Entry *entry = ht->table[i];
        while (entry) {
            if (strlen(entry->word) > 2) {
                count++;
            }
            entry = entry->next;
        }
    }
    return count;
}

void printBoggle(char boggle[4][4]) {
    printf("******************\n");
    printf("*   B O G G L E  *\n");
    printf("******************\n");
    printf("*     0  1  2  3 *\n");
    for (int i = 0; i < 4; i++) {
        printf("* %d   ", i);
        for (int j = 0; j < 4; j++) {
            printf("%c  ", boggle[i][j]);
        }
        printf("*\n");
    }
    printf("******************\n");
}

void findWordsUtil(HashTable *ht, char boggle[4][4], int i, int j, int visited[4][4], char *str, int index, char *path, int path_index, int *count,  char* letters, int *letters_index) {
    visited[i][j] = 1;
    str[index] = boggle[i][j];
    str[index + 1] = '\0';
    letters[index] = boggle[i][j];

    if (search(ht, str) && strlen(str) > 2) {
        printf("%8s ", str);
        for (int k = 0; k < path_index; k += 2) {
             printf("%c:(%d,%d)->", letters[k / 2], path[k], path[k + 1]);
        }
        printf("%c:(%d,%d)\n", letters[index], i, j);
        (*count)++;
    }

    int dx[] = { -1, -1, -1, 0, 0, 1, 1, 1 };
    int dy[] = { -1, 0, 1, -1, 1, -1, 0, 1 };

    for (int k = 0; k < 8; k++) {
        int ni = i + dx[k];
        int nj = j + dy[k];
        if (isSafe(ni, nj, visited)) {
            path[path_index] = i;
            path[path_index + 1] = j;
            letters[*letters_index] = boggle[i][j];
            (*letters_index)++;
            findWordsUtil(ht, boggle, ni, nj, visited, str, index + 1, path, path_index + 2, count, letters, letters_index);
            (*letters_index)--;
        }
    }

    visited[i][j] = 0;
}

int findWords(HashTable *ht, char boggle[4][4]) {
    int visited[4][4] = { {0} };
    char str[17];
    char path[32];
    char letters[17];
    int letters_index = 0;
    int count = 0;

    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            findWordsUtil(ht, boggle, i, j, visited, str, 0, path, 0, &count, letters, &letters_index);
        }
    }

    return count;
}

int main() {
    HashTable *ht = createHashTable();
    loadDictionary(ht, "corncob_caps_2023.txt");

    char boggle[4][4];
    loadBoggle(boggle, "boggle0.txt");

    printBoggle(boggle);

    int wordCount = findWords(ht, boggle);

    

    printf("Foram encontradas %d soluções\n", wordCount);
    

    return 0;
}
