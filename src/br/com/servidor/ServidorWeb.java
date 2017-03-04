package br.com.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServidorWeb extends Thread{

	private Socket sc;
	
	public ServidorWeb(Socket sc) {
		this.sc = sc;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			String req = reader.readLine();
			String arquivo = req.split(" ")[1];
			// INICIO PERFUMARIA
			String ipCliente = sc.getInetAddress().toString();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dataAtual = sdf.format(new Date());
			System.out.println("["+dataAtual+"] "+req + " IP: "+ipCliente);
			//FIM PERFUMARIA
			gerarResposta(arquivo,sc.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private void gerarResposta(String arquivo, OutputStream outputStream) throws IOException, URISyntaxException{
		PrintWriter outStream = new PrintWriter(new OutputStreamWriter(outputStream));
		if (ArquivoHelper.existeArquivo(arquivo)) {
			outStream.println("HTTP/1.1 200 OK");
			String contentType = ArquivoHelper.contentArquivo(arquivo);
			outStream.println("Content-type: "+contentType);
			String contentLength = ArquivoHelper.contentLength(arquivo);
			outStream.println("Content-length: "+contentLength);
			outStream.println("");
			outStream.flush();
			ArquivoHelper.enviaArquivo(arquivo, outputStream);
		} else {
			outStream.println("HTTP/1.1 404 NotFound");
			outStream.println("Content-type: text/html");
			outStream.println("");
			outStream.println("UNIRN Web Server - Página Não Encontrada");
			outStream.flush();
			
		}
		outStream.close();
	}
	
}
