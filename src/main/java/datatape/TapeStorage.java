package datatape;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Igor on 18.07.2018.
 */
public class TapeStorage implements ITapeStorage, Comparable<TapeStorage> {
    private int size;
    private int curPosition;
    private ArrayList<Integer> data;

    public TapeStorage(int size) {
        this.size = size;
        data = new ArrayList<>(this.size);

        moveFirst();
    }

    /**
     * Init for buffer for test purpose
     *
     * @param buf
     */
    public TapeStorage(int[] buf) {
        size = buf.length;
        data = new ArrayList<>(size);
        for (int i = 0; i < buf.length; i++) {
            data.add(buf[i]);
        }

        moveFirst();
    }

    @Override
    public void sortData(boolean isReversedOrder) {
        if (isReversedOrder) {
            Collections.sort(data, Collections.reverseOrder());
        } else {
            Collections.sort(data);
        }
    }

    @Override
    public int compareTo(TapeStorage o) {
        return (getCurrentData() - o.getCurrentData());
    }

    @Override
    public boolean moveFirst() {
        curPosition = -1;
        return size > 0;
    }

    @Override
    public int getCurrentData() {
//        if(!isEnd()) {
        return data.get(curPosition);
//        } else {
//            return 0;
//        }
    }

    @Override
    public boolean moveNext() {
        int nextPos = curPosition + 1;
        if (nextPos <= size - 1) {
            curPosition = nextPos;
            return true;
        }
        return false;
    }

    @Override
    public boolean movePrev() {
        int prevPos = curPosition - 1;
        if(prevPos >= -1) {
            curPosition = prevPos;
            return true;
        }
        return false;
    }

    @Override
    public boolean isEnd() {
        return (size - 1 == curPosition);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean writeData(int dataValue) {
        if (data.size() == curPosition) {
            data.add(dataValue);
        } else {
            data.set(curPosition, dataValue);
        }
        return true;
    }

    @Override
    public void printData() {
        if (moveFirst()) {
            while (moveNext()) {
                System.out.printf("%d ", getCurrentData());
            }
        }
    }
}
