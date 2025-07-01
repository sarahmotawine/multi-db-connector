package com.banco;

import java.util.Scanner;

import com.banco.create_tabela.CriarTabelasPostgres;
import com.banco.model.Pessoa;
import com.banco.repository.PessoaNeo4jGraph;
import com.banco.services.PessoaService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PessoaService service = new PessoaService();
        PessoaNeo4jGraph grafo = new PessoaNeo4jGraph();
        CriarTabelasPostgres create = new CriarTabelasPostgres();

        create.criarTabelaPessoa();

        System.out.println("O que deseja fazer?");
        System.out.println("1 - Cadastrar novas pessoas e registrar relacionamentos");
        System.out.println("2 - Apenas registrar relacionamentos");
        System.out.print("Escolha (1 ou 2): ");
        String escolha = scanner.nextLine();

        if (escolha.equals("1")) {
            System.out.println("\nDigite o número de pessoas que deseja adicionar:");
            int n = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < n; i++) {
                System.out.println("\nCadastro da pessoa " + (i + 1));

                System.out.print("Id (número): ");
                Long id = Long.parseLong(scanner.nextLine());

                System.out.print("Nome: ");
                String nome = scanner.nextLine();

                System.out.print("Email: ");
                String email = scanner.nextLine();

                System.out.print("CPF: ");
                String cpf = scanner.nextLine();

                System.out.print("Data nascimento (YYYY-MM-DD): ");
                String nascimento = scanner.nextLine();

                Pessoa p = new Pessoa(id, nome, email, cpf, nascimento);
                service.salvarPessoa(p);

                grafo.criarPessoaSeNaoExistir(id, nome);

                System.out.println("Pessoa adicionada com sucesso!");
            }
        }

        System.out.println("\nDeseja registrar algum relacionamento entre as pessoas? (s/n)");
        String resposta = scanner.nextLine();

        while (resposta.equalsIgnoreCase("s")) {
            System.out.println("Qual tipo de relacionamento deseja registrar?");
            System.out.println("1 - Amizade");
            System.out.println("2 - Parentesco");
            System.out.println("3 - Colega de faculdade");
            System.out.print("Escolha (1, 2 ou 3): ");
            String tipo = scanner.nextLine();

            System.out.print("ID da primeira pessoa: ");
            Long id1 = Long.parseLong(scanner.nextLine());

            System.out.print("Nome da primeira pessoa: ");
            String nome1 = scanner.nextLine();

            System.out.print("ID da segunda pessoa: ");
            Long id2 = Long.parseLong(scanner.nextLine());

            System.out.print("Nome da segunda pessoa: ");
            String nome2 = scanner.nextLine();

            if (tipo.equals("1")) {
                grafo.criarAmizade(id1, nome1, id2, nome2);
                System.out.println("Amizade registrada com sucesso!");
            } else if (tipo.equals("2")) {
                grafo.criarParentesco(id1, nome1, id2, nome2);
                System.out.println("Parentesco registrado com sucesso!");
            } else if (tipo.equals("3")) {
                grafo.criarColega(id1, nome1, id2, nome2);
                System.out.println("Colegas de faculdade registrados com sucesso!");
            } else {
                System.out.println("Tipo inválido, tente novamente.");
            }

            System.out.println("Deseja registrar outro relacionamento? (s/n)");
            resposta = scanner.nextLine();
        }

        scanner.close();
        System.out.println("\nPrograma finalizado.");
    }
}