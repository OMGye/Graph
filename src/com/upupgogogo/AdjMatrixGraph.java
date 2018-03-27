package com.upupgogogo;

/**
 * Created by upupgogogo on 2018/3/26.上午11:45
 */
public class AdjMatrixGraph<T> {

    protected SeqList<T> vertexlist;    //顺序表储存图的定点集合
    protected int[][] adjmatrix;        //图的邻接矩阵
    private final int MAX_WEIGHT = 99999;  //最大权值（表示无穷大）

    public AdjMatrixGraph(int size){
        size = size < 10 ? 10 : size;
        this.vertexlist = new SeqList<>(size);  //构造容量为size的空顺序表，当前定点数为0
        this.adjmatrix = new int[size][size];   //初始化邻接矩阵
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                this.adjmatrix[i][j] = (i == j) ? 0 : MAX_WEIGHT;
    }

    public AdjMatrixGraph(T[] vertexlist, Edge[] edges){
        this(vertexlist.length);
        if (vertexlist == null)
            return;
        for (int i = 0; i < vertexlist.length; i++)
            insertVertex(vertexlist[i]);
        if (edges != null)
            for (int j = 0; j < edges.length; j++)
                insertEdge(edges[j]);
    }

    //返回顶点数
    public int vertexCount(){return this.vertexlist.length();}

    //返回顶点vi的数据元素
    public T get(int i){ return this.vertexlist.get(i);}

    //返回<vi,vj>边的权值
    public int getWeight(int i, int j){

        return this.adjmatrix[i][j];
    }

    public void checkIndex(int i, int j){
        if (i > this.vertexCount() || i < 0 || j > this.vertexCount() || j < 0)
            throw new IndexOutOfBoundsException("size ="+this.vertexCount());
    }

    //新增一个顶点
    public int insertVertex(T x){
        //顺序表自动扩容
        this.vertexlist.add(x);

        //二维数组判断是否扩容
        if (this.vertexCount() > this.adjmatrix.length){
            int[][] temp = this.adjmatrix;
            adjmatrix = new int[temp.length*2][temp.length*2];
            for (int i = 0; i < temp.length; i++){
                for (int j = 0; j < temp.length; j++)
                    this.adjmatrix[i][j] = temp[i][j];
                for (int j = temp.length; j < temp.length*2; j++)
                    this.adjmatrix[i][j] = MAX_WEIGHT;
            }
            for (int i = temp.length; i < temp.length*2; i++)
                for (int j = 0; j < temp.length*2; j++)
                    adjmatrix[i][j] = (i == j) ? 0 : MAX_WEIGHT;
        }
        return this.vertexlist.length() - 1;
    }

    //新增一条边
    public void insertEdge(int i, int j, int weight){
        int n = this.vertexCount();
        if (i >= 0 && i < n && j >= 0 && j < n && i != j )
            this.adjmatrix[i][j] = weight;
    }

    public void insertEdge(Edge edge){
        insertEdge(edge.getStart(),edge.getDest(),edge.getWeight());
    }

    //删除一条边
    public void removeEdge(int i, int j){
        int n = this.vertexCount();
        if (i >= 0 && i < n && j >= 0 && j < n && i != j )
            this.adjmatrix[i][j] = MAX_WEIGHT;
    }

    //删除一个顶点
    public void removeVertex(int i){
        int n = this.vertexCount();
        if (i < 0 || i >= n)
            return;
        this.vertexlist.remove(i);

        //删除二维数组该点的关系
        for (int j = i; j < n-1; j++){
            for (int k = 0; k < n; k++)
                this.adjmatrix[j][k] = adjmatrix[j+1][k];
        }
        for (int j = i; j < n-1; j++){
            for (int k = 0; k < n-1; k++)
                this.adjmatrix[k][j] = adjmatrix[k][j+1];
        }

    }


    public String toString() {
        String str = "顶点集合: "+ this.vertexlist.toString()+"\n邻接矩阵:  \n";
        int n = this.vertexCount();
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++)
                str+=this.adjmatrix[i][j] == MAX_WEIGHT ? " ∞" : " "+this.adjmatrix[i][j];
            str+= "\n";
        }
        return str;
    }

    public void shortestPath(int i){
        int n = this.vertexCount();
        int[] dist = new int[n];   //i出发，到达每个顶点的最短路径长度
        int[] path = new int[n];   //i出发，最短路径终点的前一个顶点
        int[] vset = new int[n];   //i出发，以求最短路径的定点集合
        vset[i] = 1;
        for (int j = 0; j < n; j++){
            dist[j] = this.getWeight(i,j);
            path[j] = (j != i && dist[j] != MAX_WEIGHT) ? i : -1;
        }
        for(int j = (i+1) % n; j != i; j = (j+1) % n){
            int mindist = MAX_WEIGHT;
            int u = 0;
            for(int k = 0; k < n; k++)
                //从i出发，寻找未确定最短路径
                if (vset[k] == 0 && dist[k] < mindist){
                //得到最短路径的点
                u = k;
                //将最短路径的值放在临时的mindist变量里面来判断是否有还有最短路径
                mindist = dist[k];
                }
            if (mindist == MAX_WEIGHT)
                break;
            //将确定好的最短路径放入数组
            vset[u] = 1;

            //根据最短路径的点来进行调整其他的路径
            for (int k = 0; k < n; k++)
                //这里进行调整有三个判断要求
                //1.调整未确定的
                //2.该点到达其他点不能没有权值
                //3.该点到达其他点必须小于从i出发到达改点
                if (vset[k] == 0 && getWeight(u,k) < MAX_WEIGHT && dist[u]+getWeight(u,k)
                        <dist[k])
                {
                    dist[k] = dist[u] + this.getWeight(u,k);
                    path[k] = u;
                }
        }

        System.out.println("\n从顶点"+this.get(i)+"到其他顶点的最短距离如下: ");
        for (int j = 0; j < n; j++)
            if(j != i){
            String pathstr= "";
            for (int k = path[j]; k != i && k != j && k != -1; k = path[k])
                pathstr = ","+this.get(k)+pathstr;
            pathstr = "("+get(i)+pathstr+","+get(j)+")长度为"+(dist[j] == MAX_WEIGHT ? "∞" : dist[j]);
                System.out.println(pathstr);
            }

    }


    public static void main(String[] args) {
        String[] vertices = {"A","B","C","D","E"};
        Edge[] edges = {new Edge(0,1,5),new Edge(0,3,2),
                        new Edge(1,0,5),new Edge(1,3,6),
                        new Edge(1,2,7),new Edge(2,1,7),
                new Edge(2,3,8),new Edge(2,4,3),
                new Edge(3,0,2),new Edge(3,1,6),
                new Edge(3,2,8),new Edge(3,4,9),
                new Edge(4,2,3),new Edge(4,3,9),
                };
        AdjMatrixGraph<String> graph = new AdjMatrixGraph<String>(vertices,edges);
        System.out.println(graph);
        graph.insertVertex("F");
        System.out.println(graph);
        graph.removeVertex(1);
        System.out.println(graph);
        graph.insertEdge(new Edge(4,0,4));
        System.out.println(graph);
        graph.shortestPath(4);
    }

    public void changeShort(int i){
        int n = this.vertexCount();
        int dist[] = new int[n];
        int path[] = new int[n];
        int vset[] = new int[n];

        vset[i] = 1;
        for (int j = 0; j < n; j++){
            dist[j] = this.getWeight(i,j);
            path[j] = (j != i && dist[j] <MAX_WEIGHT) ? i : -1;
        }
        for (int j = 0; j < n-1; j++){
            int tempDist = MAX_WEIGHT;
            int u = 0;
            for (int k = 0; k < n; k++)
                if (vset[k] == 0 && dist[k] < tempDist){
                u = k;
                tempDist = dist[k];
                }
            if (tempDist == MAX_WEIGHT)
                break;

            for (int k = 0; k < n; k++){
                if (vset[k] == 0 && this.getWeight(u,k) < MAX_WEIGHT && dist[u]+this.getWeight(u,k) < dist[k])
                {
                    dist[k] = dist[u]+this.getWeight(u,k);
                    path[k] = u;
                }
            }
        }
    }
}
