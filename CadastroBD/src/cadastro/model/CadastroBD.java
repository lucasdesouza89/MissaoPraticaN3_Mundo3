/*
    Autor: Wallace Tavares
 */
package cadastro.model;
import java.util.Scanner;


public class CadastroBD {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

            boolean continuar = true;

            while (continuar) {
                InterfaceCadastro.Opcoes();

                int opcao = InterfaceCadastro.ler(scanner);

                switch (opcao) {
                    case 1 -> {
                        Incluir_Pessoa:
                        InterfaceCadastro.Incluir(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    }
                    case 2 -> {
                        Alterar_Pessoa:
                        InterfaceCadastro.Alterar(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    }
                    case 3 -> {
                        Excluir_Pessoa:
                        InterfaceCadastro.Excluir(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    }
                    case 4 -> {
                        Buscar_pelo_Id:
                        InterfaceCadastro.Buscar(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    }
                    case 5 -> {
                        Exibir_todos:
                        InterfaceCadastro.Listar(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    }
                    case 0 -> {
                        Finalizar_Programa:
                        continuar = false;
                    }

                    default ->
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }

    }

}
