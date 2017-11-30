/* This is a utility class, similar to Pair class in C++. This is not intended to be part of the system but a built in library.*/
public class Pair<L,R> {
    private L l;
    private R r;
    
    public  Pair(L l, R r){
        this.l = l;
        this.r = r;
    }
    public L getL(){ return l; }
    public R getR(){ return r; }
    public void setL(L l){ this.l = l; }
    public void setR(R r){ this.r = r; }
}