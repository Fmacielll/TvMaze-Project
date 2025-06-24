package Prova_2tri;

import java.io.*;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {
    private static final String ARQUIVO = "Usuarios.json";

    // Carrega todos os usuários do arquivo
    private static List<User> carregarTodosUsuarios() {
        try {
            File f = new File(ARQUIVO);
            if (!f.exists()) return new ArrayList<>();
            FileReader fr = new FileReader(f);
            Gson gson = new Gson();
            List<User> usuarios = gson.fromJson(fr, new TypeToken<List<User>>(){}.getType());
            fr.close();
            if (usuarios == null) return new ArrayList<>();
            return usuarios;
        } catch (Exception e) {
            System.out.println("Erro ao carregar todos os usuários: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Salva a lista de usuários no arquivo
    private static void salvarTodosUsuarios(List<User> usuarios) {
        try {
            FileWriter fw = new FileWriter(ARQUIVO);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(usuarios, fw);
            fw.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar todos os usuários: " + e.getMessage());
        }
    }

    // Salva ou atualiza um usuário
    public static void salvarUsuario(User user) {
        List<User> usuarios = carregarTodosUsuarios();
        // Remove o usuário antigo caso já exista
        usuarios.removeIf(u -> u.getNome().equals(user.getNome()));
        usuarios.add(user);
        salvarTodosUsuarios(usuarios);
    }

    // Carrega um usuário pelo nome
    public static User carregarUsuario(String nome) {
        List<User> usuarios = carregarTodosUsuarios();
        for (User u : usuarios) {
            if (u.getNome().equals(nome)) {
                return u;
            }
        }
        // Se não encontrar, retorna um novo usuário com esse nome
        return new User(nome);
    }
}