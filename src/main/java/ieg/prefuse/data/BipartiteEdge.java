package ieg.prefuse.data;

import prefuse.data.Graph;
import prefuse.data.Table;
import prefuse.data.tuple.TableNode;

/**
 * Edge implementation for a bi-partite graph.
 * 
 * <p>
 * Following the <em>proxy tuple</em> pattern [Heer & Agrawala, 2006] it
 * provides an object oriented proxy for accessing an edge of the bipartite
 * graph.
 * 
 * @author Rind
 * 
 */
public class BipartiteEdge extends TableNode {

    // TODO handle bipartite edges that are also part of a graph (if needed?)

    /**
     * The backing bipartite graph.
     */
    protected BipartiteGraph bGraph;

    @Deprecated
    protected void init(Table table, BipartiteGraph bGraph, int row) {
        super.init(table, null, row);
        this.bGraph = bGraph;
    }

    protected void init(Table table, Graph graph, BipartiteGraph bGraph, int row) {
        super.init(table, graph, row);
        this.bGraph = bGraph;
    }

    public BipartiteGraph getBipartiteGraph() {
        return bGraph;
    }

}
