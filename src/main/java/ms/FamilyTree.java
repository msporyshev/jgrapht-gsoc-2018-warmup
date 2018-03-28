package ms;

import com.sun.istack.internal.NotNull;
import org.jgrapht.alg.NaiveLcaFinder;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

import java.util.*;

public class FamilyTree {
    private DirectedPseudograph<String, DefaultEdge> graph;
    private NaiveLcaFinder<String, DefaultEdge> lcaFinder;

    public FamilyTree() {

    }

    public void buildTreeFrom(DirectedPseudograph<String, DefaultEdge> graph) {
        this.graph = graph;
        this.lcaFinder = new NaiveLcaFinder<>(graph);
    }

    public Set<String> findLcas(@NotNull String first, @NotNull String second) {
        return Objects.requireNonNull(lcaFinder).findLcas(first, second);
    }

    public DirectedPseudograph<String, DefaultEdge> getGraph() {
        return graph;
    }
}
