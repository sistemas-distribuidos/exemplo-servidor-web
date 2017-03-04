package br.com.servidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;

public class ArquivoHelper {

	private static final String DOCUMENT_ROOT = "/Users/romulofc/Sites/base-admin-3/theme";
	private static final String INDEX_FILE = "/index.html";

	public static boolean existeArquivo(String arquivo) {
		if ("/".equals(arquivo)) {
			return new File(DOCUMENT_ROOT + INDEX_FILE).exists();
		} else {
			return new File(DOCUMENT_ROOT + arquivo).exists();
		}
	}

	public static void enviaArquivo(String arquivo, OutputStream outputStream) {
		File arquivoLocal = null;
		if ("/".equals(arquivo)) {
			arquivoLocal = new File(DOCUMENT_ROOT + INDEX_FILE);
		} else {
			arquivoLocal = new File(DOCUMENT_ROOT + arquivo);
		}
		FileInputStream fin;
		try {
			fin = new FileInputStream(arquivoLocal);
			int bt = 0;
			byte[] buffer = new byte[1024];
			while ((bt = fin.read(buffer)) != -1) {
				outputStream.write(buffer,0,bt);
			}
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String contentArquivo(String arquivo) throws IOException, URISyntaxException {
		if("/".equals(arquivo)){
			return "text/html";
		}
		String[] imageExtensionJPG = {".jpg",".jpeg"};
		String[] imageExtensionPNG = {".png"};
		String[] imageExtensionHTML = {".htm",".html",".xhtml"};
		String[] imageExtensionCSS = {".css"};
		String[] imageExtensionJS = {".js"};
		HashMap<String, String[]> mapaTipos = new HashMap<String, String[]>();
		mapaTipos.put("text/x-javascript",imageExtensionJS);
		mapaTipos.put("text/css",imageExtensionCSS);
		mapaTipos.put("text/html",imageExtensionHTML);
		mapaTipos.put("image/png",imageExtensionPNG);
		mapaTipos.put("image/jpeg",imageExtensionJPG);
		
		for (String tipos : mapaTipos.keySet()){
			for(String ext: mapaTipos.get(tipos)){
				if(arquivo.endsWith(ext)){
					return tipos;
				}
			}
		}
		
		return "text/plain";
		
	}

	public static String contentLength(String arquivo) {
		if ("/".equals(arquivo)) {
			return String.valueOf(new File(DOCUMENT_ROOT + INDEX_FILE).length());
		} else {
			return String.valueOf(new File(DOCUMENT_ROOT + arquivo).length());
		}
	}

}
