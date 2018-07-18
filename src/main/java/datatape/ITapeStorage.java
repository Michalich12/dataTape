package datatape;

/**
 * @author Igor on 18.07.2018.
 */
public interface ITapeStorage {

    boolean moveFirst();

    int getCurrentData();

    boolean moveNext();

    boolean movePrev();

    boolean isEnd();

    int getSize();

    boolean writeData(int dataValue);

    void sortData(boolean isReversedOrder);

    void printData();
}
