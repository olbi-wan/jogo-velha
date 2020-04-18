package br.com.itau.entrevista.jogovelha;

import static java.util.stream.IntStream.range;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.common.collect.ImmutableMap;

import lombok.val;

public class JogoVelha {

    private static final Scanner ENTRADA = new Scanner(System.in);

    // Simbolizam os jogadores e uma casa vazia.
    private enum Jogador { LIVRE, JOGADOR_1, JOGADOR_2 };

    /*
     * Simboliza as casas do jogo da velha.
     * 0 - simboliza uma casa vazia.
     * 
     * XXX troquei a implementacao. Faltou o jogador que efetuou a jogada no original.
     */
    private static final Map<Point, Jogador> CASAS = new HashMap<Point, Jogador>(ImmutableMap.<Point, Jogador>builder()
            .put(new Point(1, 1), Jogador.LIVRE) .put(new Point(1, 2), Jogador.LIVRE) .put(new Point(1, 3), Jogador.LIVRE)
            .put(new Point(2, 1), Jogador.LIVRE) .put(new Point(2, 2), Jogador.LIVRE) .put(new Point(2, 3), Jogador.LIVRE)
            .put(new Point(3, 1), Jogador.LIVRE) .put(new Point(3, 2), Jogador.LIVRE) .put(new Point(3, 3), Jogador.LIVRE)
    .build());

    public static void main(String args[]) {

        while(true) {

            jogar(Jogador.JOGADOR_1);
            if(verificarGanhador(Jogador.JOGADOR_1)) break;

            jogar(Jogador.JOGADOR_2);									
            if(verificarGanhador(Jogador.JOGADOR_2)) break;											

        }

    }

    private static void jogar(Jogador jogador) {

        val posicaoLivre = new Point(lerEntrada(jogador, "linha"), lerEntrada(jogador, "coluna"));

        if(CASAS.get(posicaoLivre) == null || CASAS.get(posicaoLivre) != Jogador.LIVRE) {

            System.out.println("Posição inválida, tente novamente!");

            jogar(jogador);

        } else {

            // Troca a casa vazia, por uma casa ocupada pelo {@code jogador}.
            CASAS.put(posicaoLivre, jogador);

        }

     }

    private static int lerEntrada(Jogador jogador, String localizacao) {

        System.out.printf("Jogador (%s) é sua vez, digite sua posição (%s): ", jogador, localizacao);

        return ENTRADA.nextInt();

    }

    private static boolean verificarGanhador(Jogador jogador) {

    	System.out.println(CASAS);
    	
        if(verificarGanhadorLinha(jogador) || verificarGanhadorColuna(jogador) || verificarGanhadorDiagonal(jogador)) {

            System.out.printf("(%s) campeão!!!", jogador);	

            return true;

        }

        return false;

    }

    // Varre a primeira linha e depois varre todas as colunas sucessivamente ate achar todas (allMatch) marcadas pelo mesmo jogador.
    private static boolean verificarGanhadorLinha(Jogador jogador) {
        return range(1, 4).boxed().filter(linha -> range(1, 4).allMatch(coluna -> CASAS.get(new Point(linha, coluna)).equals(jogador))).findFirst().isPresent();
    }

    // Varre a primeira coluna e depois varre todas as linhas sucessivamente ate achar todas (allMatch) marcadas pelo mesmo jogador.
    private static boolean verificarGanhadorColuna(Jogador jogador) {
        return range(1, 4).boxed().filter(coluna -> range(1, 4).allMatch(linha -> CASAS.get(new Point(linha, coluna)).equals(jogador))).findFirst().isPresent();
    }

    // Se o centro for do jogador entao verifica as casas dos cantos.
    private static boolean verificarGanhadorDiagonal(Jogador jogador) {
        return CASAS.get(new Point(2, 2)).equals(jogador) && (CASAS.get(new Point(1, 1)).equals(jogador) && CASAS.get(new Point(3, 3)).equals(jogador)) ||
                                                             (CASAS.get(new Point(1, 3)).equals(jogador) && CASAS.get(new Point(3, 1)).equals(jogador));
    }

}