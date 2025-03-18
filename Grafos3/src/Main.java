import java.io.*;
import java.util.ArrayList;

class BaldeDeTinta {
    private static final int[] dx = {-1, 1, 0, 0, -1, 1, -1, 1}; // Direções para 8 conexões
    private static final int[] dy = {0, 0, -1, 1, -1, -1, 1, 1};

    public static void baldeDeTinta(int[][] imagem, int pi, int pf, int novaCor) {
        int m = imagem.length;
        int n = imagem[0].length;
        int corInicial = imagem[pi][pf];


        // Cria o grafo
        Graph grafo = new Graph(m * n);

        // Criar as arestas
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int v = i * n + j;
                for (int k = 0; k < 8; k++) {
                    int novoX = i + dx[k];
                    int novoY = j + dy[k];

                    // Verifica se o vizinho está dentro dos limites da matriz
                    if (novoX >= 0 && novoX < m && novoY >= 0 && novoY < n) {
                        int w = novoX * n + novoY; // Representa o vizinho
                        grafo.addEdge(v, w);
                    }
                }
            }
        }

        // BFS para preencher a área conectada
        boolean[] visitado = new boolean[m * n];
        Queue<Integer> fila = new Queue<>();
        fila.enqueue(pi * n + pf); //ponto inicial
        visitado[pi * n + pf] = true;

        while (!fila.isEmpty()) {
            int v = fila.dequeue();
            int x = v / n; // coordenada x
            int y = v % n; // coordenada y

            // Se a cor não for a inicial, ignora
            if (imagem[x][y] != corInicial) continue;

            // Preenche a célula com a nova cor
            imagem[x][y] = novaCor;

            // Explora os vizinhos
            for (int w : grafo.adj(v)) {
                if (!visitado[w]) {
                    visitado[w] = true;
                    fila.enqueue(w);
                }
            }
        }
    }

    // Método para exibir a matriz
    public static void exibirImagem(int[][] imagem) {
        for (int i = 0; i < imagem.length; i++) {
            for (int j = 0; j < imagem[i].length; j++) {
                System.out.print(imagem[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        int[][] imagem = null;
        try {
            FileReader arq = new FileReader("C:\\Users\\marce\\IdeaProjects\\Grafos3\\src\\entrada.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            ArrayList<String> armLinhas = new ArrayList();
            while (linha != null) {
                armLinhas.add(linha);
                linha = lerArq.readLine(); // lê da segunda até a última linha
            }
            imagem = new int[armLinhas.size()][armLinhas.get(0).split(" ").length];
            for (int i = 0; i < imagem.length; i++) {
                for (int j = 0; j < imagem[0].length; j++) {
                    String[] aux = armLinhas.get(i).split(" ");
                    imagem[i][j] = Integer.parseInt(aux[j]);
                }
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }

        System.out.println();


        // Ponto inicial (0,0) e a nova cor (9)
        int pi = 0;
        int pf = 0;
        int novaCor = 9;

        System.out.println("Imagem antes do balde de tinta:");
        exibirImagem(imagem);

        // Aplicando o balde de tinta
        baldeDeTinta(imagem, pi, pf, novaCor);

        System.out.println("\nImagem após o balde de tinta:");
        exibirImagem(imagem);

        FileWriter saida = new FileWriter("C:\\Users\\marce\\IdeaProjects\\Grafos3\\src\\saida.txt");
        PrintWriter gravarSaida = new PrintWriter(saida);
        for (int i = 0; i < imagem.length ; i++) {
            for (int j = 0; j < imagem[0].length; j++) {
                if( j == imagem[0].length-1) {
                    gravarSaida.println(imagem[i][j]);
                } else {
                    gravarSaida.print(imagem[i][j] + " ");
                }
            }
        }
        saida.close();
    }
}