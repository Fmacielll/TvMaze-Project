package Prova_2tri;

import java.util.*;

public class SerieManager {
    private User user;

    public SerieManager(User user) {
        this.user = user;
    }

    public void menuBusca(Scanner sc) {
        System.out.print("Digite o nome da série para buscar: ");
        String termo = sc.nextLine();
        try {
            List<Serie> resultados = TvMazeApi.buscarSeries(termo);
            if (resultados.isEmpty()) {
                System.out.println("Nenhuma série encontrada.");
                return;
            }
            for (int i = 0; i < resultados.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, resultados.get(i).getNome());
            }
            System.out.print("Ver detalhes de qual série (0 para cancelar)? ");
            int idx = Integer.parseInt(sc.nextLine()) - 1;
            if (idx >= 0 && idx < resultados.size()) {
                Serie s = resultados.get(idx);
                System.out.println("\nDetalhes da série:");
                System.out.println(s);
                menuAdicionarSerie(sc, s);
            }
        } catch (Exception e) {
            System.out.println("Erro na busca: " + "Verifique sua conexao com a internet.");
        }
    }

    private void menuAdicionarSerie(Scanner sc, Serie s) {
        System.out.println("1. Adicionar aos favoritos");
        System.out.println("2. Adicionar a já assistidas");
        System.out.println("3. Adicionar a deseja assistir");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");
        int op = Integer.parseInt(sc.nextLine());
        switch (op) {
            case 1:
                if (!user.getFavoritos().contains(s)) user.getFavoritos().add(s);
                break;
            case 2:
                if (!user.getAssistidas().contains(s)) user.getAssistidas().add(s);
                break;
            case 3:
                if (!user.getDesejaAssistir().contains(s)) user.getDesejaAssistir().add(s);
                break;
            default:
                break;
        }
    }

    public void menuLista(Scanner sc, String tipo) {
        List<Serie> lista = obterLista(tipo);
        if (lista.isEmpty()) {
            System.out.println("Lista vazia.");
            return;
        }
        exibirLista(lista);
        System.out.println("1. Remover série");
        System.out.println("2. Ordenar lista");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");
        int op = Integer.parseInt(sc.nextLine());
        switch (op) {
            case 1:
                System.out.print("Índice da série para remover: ");
                int idx = Integer.parseInt(sc.nextLine()) - 1;
                if (idx >= 0 && idx < lista.size()) {
                    lista.remove(idx);
                    System.out.println("Removido.");
                }
                break;
            case 2:
                ordenarLista(sc, lista);
                break;
            default:
                break;
        }
    }

    private List<Serie> obterLista(String tipo) {
        switch (tipo) {
            case "favoritos": return user.getFavoritos();
            case "assistidas": return user.getAssistidas();
            case "deseja": return user.getDesejaAssistir();
            default: return new ArrayList<>();
        }
    }

    private void exibirLista(List<Serie> lista) {
        for (int i = 0; i < lista.size(); i++) {
            Serie s = lista.get(i);
            System.out.printf("%d. %s\n", i + 1, s.getNome());
        }
        System.out.print("Ver detalhes de qual série (0 para pular)? ");
        Scanner sc = new Scanner(System.in);
        int idx = Integer.parseInt(sc.nextLine()) - 1;
        if (idx >= 0 && idx < lista.size()) {
            System.out.println(lista.get(idx));
        }
    }

    private void ordenarLista(Scanner sc, List<Serie> lista) {
        System.out.println("Ordenar por:");
        System.out.println("1. Nome (A-Z)");
        System.out.println("2. Nota geral");
        System.out.println("3. Estado");
        System.out.println("4. Data de estreia");
        System.out.print("Escolha: ");
        int op = Integer.parseInt(sc.nextLine());
        switch (op) {
            case 1:
                lista.sort(Comparator.comparing(Serie::getNome));
                break;
            case 2:
                lista.sort(Comparator.comparing(Serie::getNota).reversed());
                break;
            case 3:
                lista.sort(Comparator.comparing(Serie::getEstado));
                break;
            case 4:
                lista.sort(Comparator.comparing(Serie::getDataEstreia));
                break;
            default:
                break;
        }
        System.out.println("Lista ordenada.");
    }
}