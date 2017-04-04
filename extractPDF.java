package copart;
import com.aspose.pdf.TextExtractionOptions;
import com.aspose.pdf.devices.TextDevice;
import com.aspose.words.Document;

public class extractPDF {
	public static void main(String[] args) throws Exception{
		// open document
		com.aspose.pdf.Document pdfDocument = new com.aspose.pdf.Document("E:/eclipse-jee-mars-1-win32-x86_64/eclipse/copart/src/copart/test.pdf");
		java.io.OutputStream text_stream = new java.io.FileOutputStream("E:/eclipse-jee-mars-1-win32-x86_64/eclipse/copart/src/copart/test.txt", false);
		// iterate through all the pages of PDF file
		for(com.aspose.pdf.Page page : (Iterable<com.aspose.pdf.Page>)pdfDocument.getPages())
		{
		    //create text device
		    TextDevice textDevice = new TextDevice();
		    //set text extraction options - set text extraction mode (Raw or Pure)
		    com.aspose.pdf.TextExtractionOptions textExtOptions = new
		com.aspose.pdf.TextExtractionOptions(com.aspose.pdf.TextExtractionOptions.TextFormattingMode.Raw);
		textDevice.setExtractionOptions(textExtOptions);
		    //get the text from pages of PDF and save it to OutputStream object
		    textDevice.process(page, text_stream);
		}
		//close stream object
		text_stream.close();
	}
}
