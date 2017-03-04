package br.com.main;

import java.net.ServerSocket;

import br.com.servidor.ServidorWeb;

public class Principal {

	private static ServerSocket ss;

	public static void main(String[] args) throws Exception{
		ss = new ServerSocket(8080);
		System.out.println("Servidor Web Iniciado");
		while(true){
			System.out.println("Aguardando requisição...");
			new ServidorWeb(ss.accept()).start();
		}
	}

}
