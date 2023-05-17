package org.aituition;


import org.aituition.pdfbox.api.layout.elements.Document;
import org.aituition.pdfbox.api.layout.elements.Frame;
import org.aituition.pdfbox.api.layout.elements.ImageElement;
import org.aituition.pdfbox.api.layout.elements.Paragraph;
import org.aituition.pdfbox.api.layout.elements.render.VerticalLayoutHint;
import org.aituition.pdfbox.api.layout.shape.Rect;
import org.aituition.pdfbox.api.layout.shape.Stroke;
import org.aituition.pdfbox.api.layout.text.Alignment;
import org.aituition.pdfbox.api.layout.text.BaseFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    private final Document document = new Document(40, 60, 40, 60);
    private ImageElement logo;

    @BeforeEach
    public void testSetup() throws IOException {
        //Add company logo
        logo = new ImageElement(getClass().getClassLoader().getResourceAsStream("aituition.logo.png"));
        logo.setWidth(logo.getWidth()/10);
        logo.setHeight(logo.getHeight()/10);
        document.add(logo, new VerticalLayoutHint(Alignment.Right, 0, 0,
                0, 0, true));
    }

    @Test
    public void testMultiplePagesPdfDocument() throws IOException {
        //Add main heading element
        Paragraph paragraph = new Paragraph();
        paragraph.addMarkup("*MULTIPLICATION*\n__*Multiplication Terms*__",28, BaseFont.Courier);
        document.add(paragraph,new VerticalLayoutHint(
                Alignment.Left,0,0,logo.getHeight()/5,0));

        paragraph = new Paragraph();

        //GPT Response, spread into multiple pages
        String textThree = "Here is a worksheet for year 3 students to practice multiplication by 1 using multiplication terms and avoiding repetition:\n" +
                "\n" +
                "1. Fill in the blank:\n" +
                "\n" +
                "a) 5 x __ = 5\n" +
                "b) 3 x __ = 3\n" +
                "c) 9 x __ = 9\n" +
                "d) 7 x __ = 7\n" +
                "e) 2 x __ = 2\n" +
                "\n" +
                "2. Solve the following multiplication problems:\n" +
                "\n" +
                "a) 1 x 4 =\n" +
                "b) 1 x 8 =\n" +
                "c) 1 x 2 =\n" +
                "d) 1 x 9 =\n" +
                "e) 1 x 6 =\n" +
                "\n" +
                "3. Write the multiplication fact for the following picture:\n" +
                "\n" +
                "[Picture of one group of three apples]\n" +
                "\n" +
                "4. Circle the correct answer:\n" +
                "\n" +
                "a) What is 1 x 10?\n" +
                "i) 11\n" +
                "ii) 10\n" +
                "iii) 1\n" +
                "\n" +
                "b) What is 1 x 5?\n" +
                "i) 10\n" +
                "ii) 5\n" +
                "iii) 1\n" +
                "\n" +
                "c) What is 1 x 0?\n" +
                "i) 1\n" +
                "ii) 0\n" +
                "iii) 10\n" +
                "\n" +
                "d) What is 1 x 2?\n" +
                "i) 3\n" +
                "ii) 2\n" +
                "iii) 1\n" +
                "\n" +
                "e) What is 1 x 7?\n" +
                "i) 6\n" +
                "ii) 7\n" +
                "iii) 1\n" +
                "\n" +
                "5. Complete the following sentences with the correct multiplication fact:\n" +
                "\n" +
                "a) If you have 1 group of 4, you have ___.\n" +
                "b) If you have 1 group of 7, you have ___.\n" +
                "c) If you have 1 group of 2, you have ___.\n" +
                "d) If you have 1 group of 5, you have ___.\n" +
                "e) If you have 1 group of 9, you have ___.\n" +
                "\n" +
                "Answers:\n" +
                "\n" +
                "1. a) 1, b) 1, c) 1, d) 1, e) 1\n" +
                "2. a) 4, b) 8, c) 2, d) 9, e) 6\n" +
                "3. 1 x 3 = 3\n" +
                "4. a) ii) 10, b) ii) 5, c) ii) 0, d) ii) 2, e) ii) 7\n" +
                "5. a) 4, b) 7, c) 2, d) 5, e) 9\"\n";

        paragraph.addText(textThree, 20, PDType1Font.COURIER);

        Frame frame = new Frame(paragraph);
        frame.setShape(new Rect());
        frame.setBorder(Color.black, new Stroke());
        frame.setPadding(10, 0, 5, 5);
        frame.setMargin(0, 0, 5, 10);
        document.add(frame, VerticalLayoutHint.CENTER);

        final OutputStream outputStream = new FileOutputStream("sample.pdf");
        document.save(outputStream);

        Path path = Paths.get("sample.pdf");
        assertTrue(Files.exists(path),"Failed to generate a multiple pages pdf document");
    }
}
