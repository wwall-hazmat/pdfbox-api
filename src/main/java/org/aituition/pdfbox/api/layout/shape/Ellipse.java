package org.aituition.pdfbox.api.layout.shape;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.aituition.pdfbox.api.layout.text.Position;

import java.io.IOException;

/**
 * Shapes an ellipse, or a circle if width==height.
 */
public class Ellipse extends RoundRect {

    /**
     * Default constructor.
     */
    public Ellipse() {
	super(0);
    }


    @Override
    protected void addRoundRect(PDPageContentStream contentStream,
            Position upperLeft, float width, float height, float cornerRadiusX,
            float cornerRadiusY) throws IOException {
        super.addRoundRect(contentStream, upperLeft, width, height, width/2f,
        	height/2);
    }
}
