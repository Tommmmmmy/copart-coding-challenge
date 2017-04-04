package copart;
import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;

//code by Maoming Tang

public class image {
	public static void main(String[] args){
		//Open the image file
        File imageFile = new File("F:/ChromeDownloads/title_arkansas.png");
//        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        // File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
        // instance.setDatapath(tessDataFolder.getParent());

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }	
	}
}
