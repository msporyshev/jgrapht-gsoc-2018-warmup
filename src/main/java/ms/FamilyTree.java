package ms;

import com.sun.istack.internal.NotNull;
import org.jgrapht.alg.NaiveLcaFinder;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

import java.util.*;

/**
 * Class that represents a family tree.
 */

public class FamilyTree {

    /**
     * Graph for family tree. Shouldn't  have cycles
     */

    private DirectedPseudograph<String, DefaultEdge> graph;

    /**
     * LCA finder
     */

    private NaiveLcaFinder<String, DefaultEdge> lcaFinder;

    /**
     * Default constructor
     */

    public FamilyTree() {

    }


    /**
     * Initializes graph and lca finder
     * @param graph family tree graph.
     */

    public void buildTreeFrom(@NotNull DirectedPseudograph<String, DefaultEdge> graph) {
        this.graph = graph;
        this.lcaFinder = new NaiveLcaFinder<>(graph);
    }

    /**
     *
     * @param first name of first person
     * @param second name of second person
     * @return set of their LCAs
     */

    public Set<String> findLcas(@NotNull String first, @NotNull String second) {
        return Objects.requireNonNull(lcaFinder).findLcas(first, second);
    }

    /**
     * Getter for family graph
     * @return family tree graph
     */

    public DirectedPseudograph<String, DefaultEdge> getGraph() {
        return graph;
    }
}
