#include <stdio.h>
#include <stdbool.h>
#define QUEUE_SIZE 500 // tamanho maximo de filas
#define ROWS 3         // Número de processos
#define COLS 10        // Número de instantes por processo

typedef bool Boolean;
typedef struct so SO;
typedef struct queues Queue;
typedef struct program Program;

enum Queues
{
    ready,
    block
};          // enumeraçao de filas ready e blocks
enum States // enumeraçao de estados
{
    EXIT,
    READY,
    RUN,
    BLOCKED,
    NONCREATE,
    NEW,
    FINISH,
    READY_UNBLOCK
};

struct program
{
    int now;         // tempo atual de execução
    int state;       // estado do programa
    int start;       // tempo em que o programa começou a ser executado
    int cycle[COLS]; // numero de ciclos de cada execução
    int unblockId;   // ID do programa a ser desbloqueado
};

// Definição da estrutura Queue que representa uma fila
struct queues
{
    int readyPrograms[QUEUE_SIZE]; // Vetor que armazena os programas prontos a executar
    int readyFront;                // Índice do primeiro programa na fila de prontos
    int readyRear;                 // Índice do último programa na fila de prontos
    int blockPrograms[QUEUE_SIZE]; // Vetor que armazena os programas bloqueados
    int blockFront;                // Índice do primeiro programa na fila de bloqueados
    int blockRear;                 // Índice do último programa na fila de bloqueados
};

struct so
{
    int instante;
    int numOfPrograms;
    Program programs[ROWS]; // Vetor de programas
    Queue queues;
};

SO OS; // Variável global que representa o SO

// Esta função recebe como argumento uma queue e verifica se uma queue não contem programas
Boolean isEmpty(enum Queues Q)
{
    if (Q == ready)
        return OS.queues.readyFront == -1;
    else if (Q == block)
        return OS.queues.blockFront == -1;
    return false;
}

// Esta função recebe como argumento uma queue e devolve o primeiro programa da queue;
int peek(enum Queues Q)
{
    if (Q == ready)
    {
        if (!isEmpty(ready))
            return OS.queues.readyPrograms[OS.queues.readyFront]; // Se o índice do primeiro programa na fila de prontos for -1, a fila está vazia
    }
    else if (Q == block)
    {
        if (!isEmpty(block))
            return OS.queues.blockPrograms[OS.queues.blockFront]; // Se o índice do primeiro programa na fila de bloqueados for -1, a fila está vazia
    }
    return -1;
}

// esta função recebe como argumento uma queue e um programa e coloca esse programa na respetiva queue
void enqueue(int id, enum Queues Q)
{
    if (Q == ready)
    {
        if (OS.queues.readyRear == QUEUE_SIZE - 1)
        {
            printf("Ready queue is full. Cannot enqueue program %d.\n", id);
            return;
        }

        if (OS.queues.readyFront == -1)
            OS.queues.readyFront = 0;

        ++OS.queues.readyRear;
        OS.queues.readyPrograms[OS.queues.readyRear] = id;
    }
    else if (Q == block)
    {
        if (OS.queues.blockRear == QUEUE_SIZE - 1)
        {
            printf("Block queue is full. Cannot enqueue program %d.\n", id);
            return;
        }

        if (OS.queues.blockFront == -1)
            OS.queues.blockFront = 0;
        ++OS.queues.blockRear;
        OS.queues.blockPrograms[OS.queues.blockRear] = id;
    }
}

// Esta função recebe como argumento uma queue e remove o primeiro programa da queue;
int dequeue(enum Queues Q)
{
    int aux;
    if (Q == ready)
    {
        if (isEmpty(ready))
            return -1;

        // remove o primeiro programa da fila
        aux = OS.queues.readyPrograms[OS.queues.readyFront];
        ++OS.queues.readyFront;

        // Se a fila de ready agora estiver vazia, redefine os índices
        if (OS.queues.readyFront > OS.queues.readyRear)
        {
            OS.queues.readyFront = -1;
            OS.queues.readyRear = -1;
        }

        // retorna o ID do programa removido
        return aux;
    }
    else if (Q == block)
    {
        // se a queue esta vazia, retorna -1
        if (isEmpty(block))
            return -1;

        // remove o primeiro programa block da queue
        aux = OS.queues.blockPrograms[OS.queues.blockFront];
        ++OS.queues.blockFront;

        // se o block da queue esta vazio ficar vazio, redefine os indices
        if (OS.queues.blockFront > OS.queues.blockRear)
        {
            OS.queues.blockFront = -1;
            OS.queues.blockRear = -1;
        }

        // devolve o ID do programa removido
        return aux;
    }
    // se uma queue for invalida,retorna -1
    return -1;
}

// Esta função recebe como argumento o id do programa e devolve o estado em que o programa se encontra;
enum States getState(int id)
{
    if (id >= 0 && id < OS.numOfPrograms)
        return OS.programs[id].state;

    // ID de programa inválido, retorna um estado padrão
    return NONCREATE; // Ou outro estado apropriado
}

// Esta função recebe como argumentos o id do programa e um estado e altera o estado do programa para o estado passado no argumento;
void setState(int id, enum States state)
{
    OS.programs[id].state = state;
}

// Esta função recebe como argumento o id do programa e coloca o programa no estado READY, enfileirando-o na fila de prontos
void unblockProcess(int id)
{
    if (getState(id) == BLOCKED)
    {
        setState(id, READY);
        enqueue(id, ready);
    }
}

// analisa o estado em que o programa se encontra e determina o novo estado do programa.
void changeProgram(int id)
{
    switch (getState(id))
    {
    case EXIT:
        // Se o programa já estiver no estado EXIT, defina seu estado para FINISH
        setState(id, FINISH);
        break;

    case READY:
        // Se o programa estiver no estado READY, remove o da fila de ready e define o seu estado como RUN.
        dequeue(ready);
        setState(id, RUN);
        ++OS.programs[id].now;

        break;

    case READY_UNBLOCK:
        // se o programa estiver no estado READY_UNBLOCK, define o seu estado como READY
        setState(id, READY);
        break;

    case RUN:
        // Se o programa estiver no estado RUN, verifica se ele completou seu ciclo. Em caso afirmativo, define o seu estado como EXIT.
        if (OS.programs[id].cycle[OS.programs[id].now] == 0)
            setState(id, EXIT);
        else
        {
            // Se o programa não completou seu ciclo, define o seu estado como BLOCKED.
            enqueue(id, block);
            setState(id, BLOCKED);
            ++OS.programs[id].now;
        }

        // Se houver algum programa na fila de Ready, remova o primeiro e defina o seu estado como RUN.
        if (!isEmpty(ready))
        {
            int aux = dequeue(block);
            setState(aux, READY);
            enqueue(aux, ready);
        }

        // Check for UNBLOCK instruction
        if (OS.programs[id].cycle[OS.programs[id].now] > 0 && OS.programs[id].cycle[OS.programs[id].now + 1] > 0)
        {
            int unblockId = OS.programs[id].cycle[OS.programs[id].now + 1];
            unblockProcess(unblockId);
        }
        break;

    case BLOCKED:
        if (!isEmpty(block))
        {
            int aux = dequeue(block);
            setState(aux, READY);
            enqueue(aux, ready);
        }
        break;

    case NONCREATE:
        setState(id, NEW);
        break;

    case NEW:
        if (getState(RUN) == FINISH)
        {
            setState(id, RUN);
            ++OS.programs[id].now;
        }
        else
        {
            setState(id, READY);
            enqueue(id, ready);
        }
        break;

    default:
        break;
    }
}

// Esta função recebe como argumento o id do programa e imprime o estado atual em que o programa se encontra.
void printProgramState(enum States state)
{
    switch (state)
    {
    case NEW:
        printf("  NEW  |");
        break;
    case READY:
        printf(" READY |");
        break;
    case RUN:
        printf("  RUN  |");
        break;
    case BLOCKED:
        printf(" BLOCK |");
        break;
    case READY_UNBLOCK:
        printf(" READY(UNB) |");
        break;
    case EXIT:
        printf("  EXIT |");
        break;
    case FINISH:
        printf(" ----- |");
        break;
    case NONCREATE:
        printf("       |");
        break;
    default:
        break;
    }
}

void run()
{
    for (int i = 0; i < OS.numOfPrograms; ++i)
        if (OS.programs[i].start == 0)
            setState(i, NONCREATE);

    OS.instante = 1;
    int unblockId = 0; // Variável para armazenar o ID do programa a ser desbloqueado

    while (OS.instante != 0)
    {
        Boolean isProgramRunning = false;                      // Variável que indica se algum programa está em execução
        int numOfExecutingPrograms = isProgramRunning ? 1 : 0; // Número de programas que estão em execução
        int runningProgram;                                    // Variável que armazena o id do programa em execução

        printf("|    %2d |", OS.instante);

        for (int i = 0; i < OS.numOfPrograms; ++i)
        {
            OS.programs[i].state = NONCREATE;
            OS.programs[i].unblockId = 0;
            if ((getState(i) == RUN) || (getState(i) == BLOCKED))
                OS.programs[i].cycle[OS.programs[i].now]--;

            if (getState(i) == RUN)
            {
                isProgramRunning = true;
                runningProgram = i;
                numOfExecutingPrograms++;
            }
            changeProgram(i);

            printProgramState(getState(i)); // Imprime o estado atual do programa
        }
        printf("\n");

        for (int i = 0; i < OS.numOfPrograms; ++i)
        {
            if (getState(i) == NONCREATE) // Coloca programas não criados na fila de criação
                changeProgram(i);

            if (getState(i) == RUN || getState(i) == BLOCKED)
                changeProgram(i);

            if (OS.programs[i].cycle[OS.programs[i].now] == 0 && getState(i) == RUN)
            {
                changeProgram(i);
                if (OS.programs[i].cycle[OS.programs[i].now] > 0 && OS.programs[i].cycle[OS.programs[i].now + 1] > 0)
                {
                    unblockId = OS.programs[i].cycle[OS.programs[i].now + 1];
                    unblockProcess(unblockId);
                }
            }
        }

        if (!isProgramRunning && !isEmpty(ready))
            changeProgram(peek(ready));

        if (getState(runningProgram) == RUN && OS.programs[runningProgram].cycle[OS.programs[runningProgram].now] == 0 && OS.programs[runningProgram].unblockId == 0)
        {
            OS.programs[runningProgram].unblockId = OS.programs[runningProgram].cycle[OS.programs[runningProgram].now + 1];
        }

        if (unblockId != 0)
        {
            unblockProcess(unblockId);
            unblockId = 0;
        }

        ++OS.instante;

        if (numOfExecutingPrograms == 0)
        {
            printf("|    %2d |", OS.instante);
            for (int i = 0; i < OS.numOfPrograms; ++i)
                printf(" ----- |");
            OS.instante = 0;
        }
    }

    printf("\n");
}

// Lê o ficheiro programas.txt e insere cada valor na matriz programas
void readFile(const char *filename, int programas[ROWS][COLS])
{
    FILE *f = fopen(filename, "r");
    if (f == NULL)
    {
        printf("Failed to open file!");
        return;
    }

    for (int i = 0; i < ROWS; i++)
    {
        for (int j = 0; j < COLS; j++)
        {
            fscanf(f, "%d", &programas[i][j]);
        }
    }
    fclose(f);
}

int main()
{
    int programas[ROWS][COLS];

    readFile("input.txt", programas);

    int rows = sizeof(programas) / sizeof(programas[0]);
    int cols = sizeof(programas[0]) / sizeof(programas[0][0]);

    if (rows != ROWS)
    {
        printf("Número incorreto de programas no arquivo de entrada.\n");
        printf("Esperado: %d, Encontrado: %d\n", ROWS, rows);
        return 1;
    }

    OS.numOfPrograms = rows;
    OS.queues.blockFront = -1;
    OS.queues.blockRear = -1;
    OS.queues.readyFront = -1;
    OS.queues.readyRear = -1;
    printf("|Instate|");
    for (int i = 0; i < rows; ++i)
    {
        OS.programs[i].now = 0;
        setState(i, NONCREATE);
        int j = 0;
        while (j < cols && programas[i][j] != -1)
        {
            OS.programs[i].cycle[j] = programas[i][j];
            ++j;
        }
        for (int j = 0; j < cols; ++j)
            OS.programs[i].cycle[j] = programas[i][j];

        OS.programs[i].start = OS.programs[i].cycle[0];
    }

    for (int i = 0; i < rows; ++i)
    {
        if (i < 9)
            printf(" -P0%d- |", i + 1);
        else
            printf(" -P%d- |", i + 1);
    }
    printf("\n|:-----:|");
    for (int i = 0; i < rows; ++i)
    {
        printf(":-----:|");
    }

    OS.instante = 0;

    printf("\n");

    run();
}
