import java.io.*;
import java.net.*;
import java.util.Scanner;

public class WebClient_NG_16 {

    public Socket webClient(String serverAddress, int serverPort, String target) throws IOException {
        //estabelecer a conexão TCP com o servidor
        Socket socket = new Socket(serverAddress, serverPort);

        //preparar a request HTTP
        String request = "GET " + target + " HTTP/1.1\r\n" +
                "Host: " + serverAddress + "\r\n" +
                "Connection: close\r\n" + //fecho de comunicação após resposta
                "\r\n"; //linha em branco para indicar o fim do header

        //obter o fluxo de saída do socket para enviar os dados para o servidor
        OutputStream outputStream = socket.getOutputStream();
        //converter a String de request para bytes
        outputStream.write(request.getBytes());
        //garantir que os dados são todos enviados
        outputStream.flush();

        //obter o fluxo de entrada para receber os dados que o servidor enviou
        InputStream inputStream = socket.getInputStream();
        //criar um buffer para ler o fluxo de entrada
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        //variável para guardar cada linha da resposta
        String line;
        //objeto para concatenar todas as linhas
        StringBuilder response = new StringBuilder();

        //lê cada linha até o fluxo acabar
        while ((line = reader.readLine()) != null) {
            //adiciona ao StringBuilder cada linha, de modo a que cada uma fique numa linha diferente (\n)
            response.append(line).append("\n");
        }

        //dar display da resposta do servidor
        System.out.println("----------------- Resposta do Servidor ----------------");
        System.out.println(response.toString());

        //retornar a socket
        return socket;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (scanner) {
            System.out.println("Insira o endereço de servidor: ");
            String address = scanner.nextLine();
            System.out.println("Insira o número da porta: ");
            int port = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Insira a target: ");
            String target = scanner.nextLine();
            WebClient_NG_16 client = new WebClient_NG_16();
            Socket webclient = client.webClient(address, port, target);
            //tratamento de errros
        } catch (UnknownHostException e) {
            System.err.println("Erro: Host desconhecido - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro I/O - " + e.getMessage());
        }
    }
}