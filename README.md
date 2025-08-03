# Projeto Sudoku

Repositório com o desafio de criação de um jogo de Sudoku em Java, proposto no bootcamp da DIO.

## 💡 Descrição

Este projeto consiste na implementação de um jogo de Sudoku funcional via terminal. O jogador pode visualizar o tabuleiro, inserir e remover números, limpar o jogo e verificar se a solução está correta de acordo com as regras clássicas do Sudoku.

As funcionalidades incluem:

- Início de novo jogo com tabuleiro preenchido parcialmente.
- Inserção e remoção de números.
- Validação do status atual do tabuleiro.
- Impressão visual do jogo no terminal.
- Verificação de vitória com base nas regras do Sudoku.

## 📋 Funcionalidades

- [x] Gerar tabuleiro inicial com números fixos válidos
- [x] Inserir número em posição específica
- [x] Remover número inserido pelo jogador
- [x] Visualizar o estado atual do jogo
- [x] Verificar se o jogo foi resolvido corretamente
- [x] Limpar o tabuleiro (mantendo os fixos)
- [x] Finalizar o jogo
- [x] Menu interativo no terminal

## 🛠️ Tecnologias Utilizadas

- Java 21+
- IDE: IntelliJ IDEA / Eclipse
- Execução via terminal

## ▶️ Como executar

1. Clone o repositório:
```bash
git clone https://github.com/juliocsantos2504/SUDUKO.git

2. Acesse a pasta do projeto:
cd SUDUKO

3. Compile e execute a classe principal:
javac Main.java
java Main

🧠 Lógica de funcionamento
O tabuleiro é representado como uma matriz de objetos Celula, contendo:

Valor do número

Posição (linha, coluna)

Flag indicando se o valor é fixo (não editável)

O jogo inicial é gerado com uma configuração válida de Sudoku, onde apenas alguns números são "fixos", e o restante pode ser preenchido pelo jogador.
