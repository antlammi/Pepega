package logic;

public class Quadruplet<X, Y, B, D>{

    public X x;
    public Y y;
    public B dir;
    public D rotation;
    public Quadruplet(X x, Y y, B dir, D rotation){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.rotation = rotation;
    }
}