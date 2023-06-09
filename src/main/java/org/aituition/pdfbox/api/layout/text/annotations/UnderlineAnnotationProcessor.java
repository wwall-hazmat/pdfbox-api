package org.aituition.pdfbox.api.layout.text.annotations;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.aituition.pdfbox.api.layout.shape.Stroke;
import org.aituition.pdfbox.api.layout.text.DrawContext;
import org.aituition.pdfbox.api.layout.text.Position;
import org.aituition.pdfbox.api.layout.text.StyledText;
import org.aituition.pdfbox.api.layout.text.annotations.Annotations.UnderlineAnnotation;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This annotation processor handles the {@link UnderlineAnnotation}s, and adds
 * the needed hyperlink metadata to the PDF document.
 */
public class UnderlineAnnotationProcessor implements AnnotationProcessor {

    private List<Line> linesOnPage = new ArrayList<Line>();

    @Override
    public void annotatedObjectDrawn(Annotated drawnObject,
	    DrawContext drawContext, Position upperLeft, float width,
	    float height) throws IOException {

	if (!(drawnObject instanceof StyledText)) {
	    return;
	}

	StyledText drawnText = (StyledText) drawnObject;
	for (UnderlineAnnotation underlineAnnotation : drawnObject
		.getAnnotationsOfType(UnderlineAnnotation.class)) {
	    float fontSize = drawnText.getFontDescriptor().getSize();
	    float ascent = fontSize
		    * drawnText.getFontDescriptor().getFont()
			    .getFontDescriptor().getAscent() / 1000;

	    float baselineOffset = fontSize * underlineAnnotation.getBaselineOffsetScale();
	    float thickness = (0.01f + fontSize * 0.05f)
		    * underlineAnnotation.getLineWeight();
	    
	    Position start = new Position(upperLeft.getX(), upperLeft.getY()
		    - ascent + baselineOffset);
	    Position end = new Position(start.getX() + width, start.getY());
	    Stroke stroke = Stroke.builder().lineWidth(thickness).build();
	    Line line = new Line(start, end, stroke, drawnText.getColor());
	    linesOnPage.add(line);
	}
    }

    @Override
    public void beforePage(DrawContext drawContext) throws IOException {
	linesOnPage.clear();
    }

    @Override
    public void afterPage(DrawContext drawContext) throws IOException {
	for (Line line : linesOnPage) {
	    line.draw(drawContext.getCurrentPageContentStream());
	}
	linesOnPage.clear();
    }

    @Override
    public void afterRender(PDDocument document) throws IOException {
	linesOnPage.clear();
    }

    private static class Line {

	private Position start;
	private Position end;
	private Stroke stroke;
	private Color color;

	public Line(Position start, Position end, Stroke stroke, Color color) {
	    super();
	    this.start = start;
	    this.end = end;
	    this.stroke = stroke;
	    this.color = color;
	}

	public void draw(PDPageContentStream contentStream) throws IOException {
	    if (color != null) {
		contentStream.setStrokingColor(color);
	    }
	    if (stroke != null) {
		stroke.applyTo(contentStream);
	    }
	    contentStream.drawLine(start.getX(), start.getY(), end.getX(),
		    end.getY());
	}

    }
}
