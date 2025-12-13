package utils

import utils.Edge.EdgeType

class Graph<T> {
    val adjacencyMap = mutableMapOf<Vertex<T>, MutableList<Edge<T>>>()

    companion object {
        fun <T> fromDirectedGraph(map: Map<T, List<T>>): Graph<T> =
            Graph<T>().apply {
                map.forEach { (source, edges) ->
                    val sourceVertex = createVertex(source)
                    edges.forEach { destination ->
                        val destinationVertex = Vertex(destination)
                        addDirectedEdge(sourceVertex, destinationVertex)
                    }
                }
            }

        fun <T> fromMatrix(
            matrix: Matrix<T>,
            edgeType: EdgeType = EdgeType.DIRECTED,
            withCellConstraint: (cell: Matrix.Cell<T>) -> Boolean = { true },
            neighborsSelector: (current: Matrix.Cell<T>) -> List<Matrix.Cell<T>> = { current ->
                matrix.neighborsCells(current)
            },
            withNeighborsConstraint: (p1: Matrix.Cell<T>, p2: Matrix.Cell<T>) -> Boolean = { _, _ -> true },
        ) = Graph<Matrix.Cell<T>>().apply {
            matrix.rowsIndices.forEach { row ->
                matrix.columnsIndices.forEach { col ->
                    matrix.getCellOrNull(row, col)?.let { current ->
                        if (withCellConstraint(current)) {
                            val source = createVertex(current)
                            neighborsSelector(current)
                                .filter { withNeighborsConstraint(current, it) }
                                .forEach { add(edgeType, source, Vertex(it)) }
                        }
                    }
                }
            }
        }

    fun createVertex(data: T): Vertex<T> {
        val vertex = Vertex(data)
        adjacencyMap[vertex] = mutableListOf()
        return vertex
    }

    fun addDirectedEdge(
        source: Vertex<T>,
        destination: Vertex<T>,
        weight: Int? = null,
    ) {
        val edge = Edge(source, destination, weight)
        adjacencyMap.getOrPut(source) { mutableListOf() }.add(edge)
    }

    fun addUndirectedEdge(
        source: Vertex<T>,
        destination: Vertex<T>,
        weight: Int? = null,
    ) {
        addDirectedEdge(source, destination, weight)
        addDirectedEdge(destination, source, weight)
    }

    fun add(
        edgeType: EdgeType,
        source: Vertex<T>,
        destination: Vertex<T>,
        weight: Int? = null,
    ) {
        when (edgeType) {
            EdgeType.DIRECTED -> addDirectedEdge(source, destination, weight)
            EdgeType.UNDIRECTED -> addUndirectedEdge(source, destination, weight)
        }
    }

    fun edges(source: Vertex<T>): List<Edge<T>> = adjacencyMap[source] ?: emptyList()

    fun leafs(): List<Vertex<T>> = adjacencyMap.filter { it.value.isEmpty() }.keys.toList()

    fun weight(
        source: Vertex<T>,
        destination: Vertex<T>,
    ): Int? = edges(source).firstOrNull { it.destination == destination }?.weight

    fun countLeafs(): Int = adjacencyMap.count { it.value.isEmpty() }

    fun numberOfPaths(
        source: Vertex<T>,
        destination: Vertex<T>,
        counter: MutableMap<Vertex<T>, Long> = mutableMapOf(),
    ): Long {
        if (source == destination) {
            return 1L
        } else if (counter.containsKey(source)) {
            return counter.getValue(source)
        } else {
            val count =
                edges(source).sumOf { edge ->
                    numberOfPaths(edge.destination, destination, counter)
                }
            counter[source] = count
            return count
        }
    }

    override fun toString(): String =
        adjacencyMap.entries.joinToString("\n") { (vertex, edges) ->
            val edgesString =
                edges.joinToString { edge ->
                    "${edge.destination}${if (edge.weight != null) " (${edge.weight})" else ""}"
                }
            "$vertex -> [$edgesString]"
        }
}

data class Vertex<T>(
    val data: T,
) {
    override fun toString(): String = data.toString()
}

data class Edge<T>(
    val source: Vertex<T>,
    val destination: Vertex<T>,
    val weight: Int? = null,
) {
    enum class EdgeType {
        DIRECTED,
        UNDIRECTED,
    }

    override fun toString(): String = "$source -> $destination${if (weight != null) " [$weight]" else ""}"
}
