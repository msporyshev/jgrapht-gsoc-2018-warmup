package ms;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.io.*;
import java.io.*;

public class DotFamilyTreeImporter {
    GraphImporter<String, DefaultEdge> importer;

    public DotFamilyTreeImporter() {
        VertexProvider<String> vp = (a, b) -> a;
        EdgeProvider<String, DefaultEdge> ep = (f, t, l, a) -> new DefaultEdge();
        this.importer = new DOTImporter<String, DefaultEdge>(vp, ep);
    }

    public void importGraph(FamilyTree tree, Reader in) throws ImportException {
        DirectedPseudograph<String, DefaultEdge> graph =
                new DirectedPseudograph<String, DefaultEdge>(DefaultEdge.class);
        this.importer.importGraph(graph, in);

        graph.removeEdge("Robert", "Cersei");
        graph.removeEdge("Cersei", "Robert");
        graph.removeEdge("Cersei", "Jaime");
        graph.removeEdge("Jaime", "Cersei");
        graph.removeEdge("Tywin", "Joanna");
        graph.removeEdge("Joanna", "Tywin");
        graph.removeEdge("Lyanna", "Rhaegar");
        graph.removeEdge("Rhaegar", "Lyanna");

        tree.buildTreeFrom(graph);
    }
}
