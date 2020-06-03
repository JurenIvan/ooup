package lab4.hr.fer.zemris.ooup.renderer;

import lab4.hr.fer.zemris.ooup.model.primitives.Point;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

public class SVGRendererImpl implements Renderer {
    private List lines = new ArrayList<>();
    private Path path;

    public SVGRendererImpl(Path path) {
        this.path = path;
        lines.add("<svg version=\"1.0\" xmlns=\"http://www.w3.org/2000/svg\"\n" +
                "    xmlns:xlink=\"http://www.w3.org/1999/xlink\" >");
    }

    public void close() throws IOException {
        lines.add("</svg>");
        Files.writeString(path, lines.stream().collect(Collectors.joining("\n")).toString());
    }

    @Override
    public void drawLine(Point s, Point e) {
        lines.add(String.format("    <line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:#0000FF;\"/>", s.getX(), s.getY(), e.getX(), e.getY()));
    }

    @Override
    public void fillPolygon(Point[] points) {
        lines.add(format("<polygon points=\"{0}\" style=\"stroke:#FF0000; fill:#0000FF; stroke-width: 1;\"/>", Arrays.stream(points).map(e -> e.getX() + "," + e.getY()).collect(Collectors.joining(" "))));
    }
}
