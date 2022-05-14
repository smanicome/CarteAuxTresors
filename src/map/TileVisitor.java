package map;

public interface TileVisitor {
    void visit(Mountain mountain);
    void visit(Treasure treasure);
}
