package ms;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.io.*;
import java.io.*;

/**
 * Class encapsulates import of family tree from dot file.
 */

public class DotFamilyTreeImporter {

    /**
     * graph importer
     */

    private GraphImporter<String, DefaultEdge> importer;

    /**
     * Constructor. Creates DOT importer inside
     */

    public DotFamilyTreeImporter() {
        VertexProvider<String> vp = (a, b) -> a;
        EdgeProvider<String, DefaultEdge> ep = (f, t, l, a) -> new DefaultEdge();
        this.importer = new DOTImporter<>(vp, ep);
    }

    /**
     *
     * @param tree family tree to initialize
     * @param in any input reader
     * @throws ImportException
     */

    public void importGraph(FamilyTree tree, Reader in) throws ImportException {
        DirectedPseudograph<String, DefaultEdge> graph =
                new DirectedPseudograph<>(DefaultEdge.class);
        this.importer.importGraph(graph, in);

        // Removing all not child-parent relations
        // Could possibly just remove all 2-link edges. But it would be better if input file contains already a DAG
        graph.removeEdge("Robert", "Cersei");
        graph.removeEdge("Cersei", "Robert");
        graph.removeEdge("Cersei", "Jaime");
        graph.removeEdge("Jaime", "Cersei");
        graph.removeEdge("Tywin", "Joanna");
        graph.removeEdge("Joanna", "Tywin");
        graph.removeEdge("Lyanna", "Rhaegar");
        graph.removeEdge("Rhaegar", "Lyanna");
        graph.removeEdge("Rhaelle", "Ormund");
        graph.removeEdge("Ormund", "Rhaelle");

        tree.buildTreeFrom(graph);
    }
}
