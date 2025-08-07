import org.junit.Test;

import java.util.*;

import static com.google.common.truth.Truth.*;

public class TestGraph {

    @Test
    public void testPath1() {
        Graph g = new Graph(2);
        assertWithMessage("Expected pathExists()==false between 2 vertices, no edges.").that(g.pathExists(0, 1)).isFalse();
        assertWithMessage("Expected pathExists()==false between 2 vertices, no edges.").that(g.pathExists(1, 0)).isFalse();

        List<Integer> path1 = g.path(0, 1);
        List<Integer> path2 = g.path(1, 0);

        assertWithMessage("Expected size 0 path between 2 vertices, no edges.").that(path1.isEmpty()).isTrue();
        assertWithMessage("Expected size 0 path between 2 vertices, no edges.").that(path2.isEmpty()).isTrue();
    }

    // DONE: add more tests!

    /**
     * 验证 order 是一个合法的拓扑序：包含 [0..n-1] 且对每条有向边 (u->v) 都满足 pos(u) < pos(v)
     */
    private void assertValidTopologicalOrder(Graph g, int n, List<Integer> order) {
        assertWithMessage("Topological order must contain exactly all vertices.")
                .that(new HashSet<>(order)).containsExactlyElementsIn(rangeSet(n));

        assertWithMessage("Topological order size must equal vertex count.")
                .that(order).hasSize(n);

        Map<Integer, Integer> pos = new HashMap<>();
        for (int i = 0; i < order.size(); i++) pos.put(order.get(i), i);

        for (int u = 0; u < n; u++) {
            for (int v : g.neighbors(u)) {
                assertWithMessage(String.format("Edge %d->%d must satisfy pos(u)<pos(v) in a topological order.", u, v))
                        .that(pos.get(u)).isLessThan(pos.get(v));
            }
        }
    }

    private Set<Integer> rangeSet(int n) {
        Set<Integer> s = new HashSet<>();
        for (int i = 0; i < n; i++) s.add(i);
        return s;
    }

    @Test
    public void testAddEdgeAndAdjacency() {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);

        assertThat(g.isAdjacent(0, 1)).isTrue();
        assertThat(g.isAdjacent(0, 2)).isTrue();
        assertThat(g.isAdjacent(1, 0)).isFalse();
        assertThat(g.isAdjacent(2, 0)).isFalse();

        List<Integer> n0 = g.neighbors(0);
        assertThat(n0).containsExactlyElementsIn(Arrays.asList(1, 2));
        assertThat(g.neighbors(1)).isEmpty();
        assertThat(g.neighbors(2)).isEmpty();
        assertThat(g.neighbors(3)).isEmpty();
    }

    @Test
    public void testAddUndirectedEdgeSymmetry() {
        Graph g = new Graph(3);
        g.addUndirectedEdge(1, 2);

        assertThat(g.isAdjacent(1, 2)).isTrue();
        assertThat(g.isAdjacent(2, 1)).isTrue();

        assertThat(g.neighbors(1)).containsExactly(2);
        assertThat(g.neighbors(2)).containsExactly(1);
    }

    @Test
    public void testAddEdgeReplaceDoesNotDuplicate() {
        Graph g = new Graph(3);
        g.addEdge(0, 1, 5);
        g.addEdge(0, 1, 7); // 替换同一条边

        // 不应出现重复邻居
        assertThat(g.neighbors(0)).containsExactly(1);
        // 入度应为 1
        assertThat(g.inDegree(1)).isEqualTo(1);
    }

    @Test
    public void testAddUndirectedEdgeReplaceDoesNotDuplicate() {
        Graph g = new Graph(3);
        g.addUndirectedEdge(0, 2, 2);
        g.addUndirectedEdge(0, 2, 9); // 替换

        assertThat(g.neighbors(0)).containsExactly(2);
        assertThat(g.neighbors(2)).containsExactly(0);

        // 两端入度各 +1（因为是两条反向边）
        assertThat(g.inDegree(0)).isEqualTo(1);
        assertThat(g.inDegree(2)).isEqualTo(1);
    }

    @Test
    public void testInDegreeCountsMultipleIncoming() {
        Graph g = new Graph(4);
        g.addEdge(0, 3);
        g.addEdge(1, 3);
        g.addEdge(2, 3);

        assertThat(g.inDegree(3)).isEqualTo(3);
        assertThat(g.inDegree(0)).isEqualTo(0);
        assertThat(g.inDegree(1)).isEqualTo(0);
        assertThat(g.inDegree(2)).isEqualTo(0);
    }

    // ===== DFS 语义（不依赖具体遍历顺序）=====

    @Test
    public void testDfsReachableSetOnlyNoDuplicates() {
        Graph g = new Graph(6);
        // 0 -> 1 -> 2 ; 3 -> 4 ; 5 isolated
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(3, 4);

        List<Integer> from0 = g.dfs(0);
        // 可达点：0,1,2
        assertThat(new HashSet<>(from0)).containsExactlyElementsIn(Arrays.asList(0, 1, 2));
        assertThat(from0).containsNoDuplicates();

        List<Integer> from3 = g.dfs(3);
        assertThat(new HashSet<>(from3)).containsExactlyElementsIn(Arrays.asList(3, 4));
        assertThat(from3).containsNoDuplicates();

        List<Integer> from5 = g.dfs(5);
        assertThat(from5).containsExactly(5); // 孤立点也应被遍历到自身
    }

    // ===== pathExists / path =====

    @Test
    public void testPathExistsTrivialSameVertex() {
        Graph g = new Graph(3);
        // 即使没有边，START == STOP 也应返回 true
        assertThat(g.pathExists(2, 2)).isTrue();

        List<Integer> p = g.path(2, 2);
        assertThat(p).containsExactly(2); // 规范要求为单元素列表
    }

    @Test
    public void testPathAndPathExistsSimpleChain() {
        Graph g = new Graph(4);
        // 0 -> 1 -> 2, 2 -> 3
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);

        assertThat(g.pathExists(0, 3)).isTrue();
        assertThat(g.pathExists(3, 0)).isFalse();

        List<Integer> p03 = g.path(0, 3);
        // 路径应从 start 到 stop；允许多种具体路径，这里链式唯一
        assertThat(p03.get(0)).isEqualTo(0);
        assertThat(p03.get(p03.size() - 1)).isEqualTo(3);
        // 所有相邻对都应是图中存在的边
        for (int i = 0; i + 1 < p03.size(); i++) {
            assertWithMessage("Adjacent pair in returned path must be an edge.")
                    .that(g.isAdjacent(p03.get(i), p03.get(i + 1))).isTrue();
        }
    }

    @Test
    public void testPathNoPathEmptyList() {
        Graph g = new Graph(3);
        g.addEdge(0, 1);
        // 无法从 1 到 0，也无法从 2 到 0
        assertThat(g.pathExists(1, 0)).isFalse();
        assertThat(g.path(1, 0)).isEmpty();

        assertThat(g.pathExists(2, 0)).isFalse();
        assertThat(g.path(2, 0)).isEmpty();
    }

    // ===== Topological Sort / iterator() =====

    @Test
    public void testTopologicalSortOnDAG() {
        int n = 5;
        Graph g = new Graph(n);
        // 与源码中 generateG2 同构的 DAG：
        // 0->1, 0->2, 0->4, 1->2, 2->3, 4->3
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 4);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(4, 3);

        List<Integer> topo = g.topologicalSort();
        assertValidTopologicalOrder(g, n, topo);
    }

    @Test
    public void testIteratorReturnsTopologicalOrder() {
        int n = 4;
        Graph g = new Graph(n);
        // 一个简单的 DAG：0->1, 0->2, 1->3, 2->3
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);

        // iterator() 规范：返回 topological sorted order 的迭代器
        List<Integer> iterOrder = new ArrayList<>();
        for (Integer integer : g)
            iterOrder.add(integer);

        assertValidTopologicalOrder(g, n, iterOrder);

        // 与 topologicalSort() 的结果都应是合法拓扑序（不必完全相同，但都应有效）
        List<Integer> topo = g.topologicalSort();
        assertValidTopologicalOrder(g, n, topo);
    }

    // ===== 邻接与入度在替换后的稳定性 =====

    @Test
    public void testReplaceStabilityWithMoreEdges() {
        Graph g = new Graph(5);
        // 初始边
        g.addEdge(0, 1, 3);
        g.addEdge(0, 2, 4);
        g.addEdge(1, 3, 5);
        g.addEdge(2, 3, 6);

        // 替换其中两条边（不应增加入度）
        g.addEdge(0, 1, 7);
        g.addEdge(2, 3, 8);

        assertThat(g.neighbors(0)).containsExactlyElementsIn(Arrays.asList(1, 2));
        assertThat(g.neighbors(1)).containsExactly(3);
        assertThat(g.neighbors(2)).containsExactly(3);

        assertThat(g.inDegree(1)).isEqualTo(1); // 0->1 仍然只有一条
        assertThat(g.inDegree(3)).isEqualTo(2); // 来自 1 和 2 的两条
    }

    @Test
    public void testAddUndirectedEdge() {
        Graph g = new Graph(2);
        g.addUndirectedEdge(0, 1);
        assertThat(g.isAdjacent(0, 1)).isTrue();
        assertThat(g.isAdjacent(1, 0)).isTrue();
    }

    @Test
    public void testNeighborsAndInDegree() {
        Graph g = new Graph(3);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        List<Integer> neighbors = g.neighbors(0);
        assertThat(neighbors).containsExactly(1, 2);
        assertThat(g.inDegree(2)).isEqualTo(2);
        assertThat(g.inDegree(0)).isEqualTo(0);
    }

    @Test
    public void testDFS() {
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        List<Integer> result = g.dfs(0);
        assertThat(result).contains(4);
        assertThat(result.get(0)).isEqualTo(0);  // 起点
    }

    @Test
    public void testPathExistsAndPath() {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        assertThat(g.pathExists(0, 3)).isTrue();
        assertThat(g.pathExists(3, 0)).isFalse();

        List<Integer> path = g.path(0, 3);
        assertThat(path.get(0)).isEqualTo(0);
        assertThat(path.get(path.size() - 1)).isEqualTo(3);
        assertThat(path).containsExactly(0, 1, 2, 3).inOrder();
    }

    @Test
    public void testTopologicalSort() {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(0, 2);
        g.addEdge(2, 3);
        List<Integer> result = g.topologicalSort();
        // Ensure a valid topological order
        assertThat(result.indexOf(0)).isLessThan(result.indexOf(1));
        assertThat(result.indexOf(1)).isLessThan(result.indexOf(2));
        assertThat(result.indexOf(2)).isLessThan(result.indexOf(3));
    }
}
