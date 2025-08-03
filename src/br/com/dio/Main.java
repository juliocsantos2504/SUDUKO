package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.util.BoardTemplate;
import br.com.dio.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT =9;

    public Main() {
    }

    public static void main(String[] args){
        final var positions = Stream.of(args)
                .collect(toMap(
                    k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));
        var option = -1;
        while (true){
            System.out.println("Selecione uma das opcoes a seguir: ");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo numero");
            System.out.println("3 - Remover um numero");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - Limpar Jogo");
            System.out.println("7 - Finalizar Jogo");
            System.out.println("8 - Sair");

            option = scanner.nextInt();

            switch (option){
                case 1 -> starGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opcao invalida, selecione uma das opcoes do menu");


            }

        }
    }
    private static void starGame(Map<String, String> positions) {
        if (nonNull(board)) {
            System.out.println("O jogo ja foi iniciado");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();

        for (int i = 0; i < BOARD_LIMIT; i++) {
            List<Space> row = new ArrayList<>();  // Cria uma nova linha

            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);

                var currentSpace = new Space(expected, fixed);
                row.add(currentSpace);  // Adiciona o Space na linha atual
            }

            spaces.add(row);  // Adiciona a linha inteira na matriz
        }

        board = new Board(spaces);  // Cria o tabuleiro com a matriz pronta
        System.out.println("O jogo esta pronto para comecar");
    }


    private static void inputNumber () {
            if (isNull(board)) {
                System.out.println("O jogo ainda nao foi iniciado");
                return;
            }
            System.out.println("Informe a linha que o numero sera inserido");
            var col = runUntilGetValidNumber(0, 8);
            System.out.println("Informe a coluna que o numero sera inserido");
            var row = runUntilGetValidNumber(0, 8);
            System.out.printf("informe o numero que vai entrar na posicao[%s,%s]\n", col, row);
            var value = runUntilGetValidNumber(1,9);
            if (!board.changeValue(col,row,value)){
                System.out.printf("A posicao [%s,%s] tem um valor fixo\n", col,row);
            }

        }


    private static void removeNumber() {
        if (isNull(board)) {
            System.out.println("O jogo ainda nao foi iniciado");
            return;
        }
        System.out.println("Informe a linha que o numero sera inserido");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a coluna que o numero sera inserido");
        var row = runUntilGetValidNumber(0, 8);
        if (!board.clearValue(col,row)){
            System.out.printf("A posicao [%s,%s] tem um valor fixo\n", col,row);
        }
    }

    private static void showCurrentGame() {
        if (isNull(board)) {
            System.out.println("O jogo ainda nao foi iniciado");
            return;
        }

        // Preparar um array de 81 strings com os valores para preencher no template
        String[] values = new String[81];
        int index = 0;
        for (int row = 0; row < BOARD_LIMIT; row++) {
            for (int col = 0; col < BOARD_LIMIT; col++) {
                Integer val = board.getSpaces().get(row).get(col).getActual();
                values[index++] = (val == null) ? " " : val.toString();
            }
        }

        // Imprimir usando o template e o array de valores (varargs)
        System.out.printf(BoardTemplate.BOARD_TEMPLATE + "%n", (Object[]) values);
    }


    private static void showGameStatus() {
        if (isNull(board)) {
            System.out.println("O jogo ainda nao foi iniciado");
            return;
        }
        System.out.printf("O jogo atualmente se encontra no status %s\n", board.getStatus().getLabel());
        if(board.hasErrors()){
            System.out.println("O jogo contem erros");
        }else {
            System.out.println("O jogo nao contem erros");
        }
    }

    private static void clearGame() {
        if (isNull(board)) {
            System.out.println("O jogo ainda nao foi iniciado");
            return;
        }
        System.out.println("Tem certeza que deseja limpar seu jogo e perder todo seu processo ?");
        var confirm = scanner.next();
        while (!confirm.equalsIgnoreCase("SIM") && !confirm.equalsIgnoreCase("NAO")){
            System.out.println("informe 'sim' ou 'nao'");
            confirm= scanner.next();
        }
        if(confirm.equalsIgnoreCase("sim")){
            board.reset();
        }
    }

    private static void finishGame() {
        if (isNull(board)) {
            System.out.println("Nenhum espaco foi preenchido");
            return;
        }
        if (board.gameIsFinished()){
            System.out.println("Parabens voce concluiu o jogo");
            showCurrentGame();
            board = null;
        } else if (board.hasErrors()){
            System.out.println("Seu jogo contem erros, verifique seu board e ajuste-o");
        }else {
            System.out.println("Voce ainda precisa preencher algum espaco");
        }

    }

    private static int runUntilGetValidNumber(final int min, final int max){
        var current = scanner.nextInt();
        while (current < min || current > max ){
            System.out.printf("Informe um numero entre %s e %s\n", min, max);
            current = scanner.nextInt();
        }
        return current;

    }
}
