package datatape;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Igor on 18.07.2018.
 */
public class TapeDataSorter {

    public TapeDataSorter() {
    }

    /**
     * Main algorithm
     * @param inStorage
     * @param outStorage
     * @param countTempStorages
     */
    public void sortData(ITapeStorage inStorage, ITapeStorage outStorage, int countTempStorages) {
        ArrayList<TapeStorage> tempList = getTempStorageList(inStorage, countTempStorages);// gets temp storage
        for (TapeStorage storage : tempList) { //sort each storage
            storage.sortData(false);
        }

        for (TapeStorage tempStorage : tempList) {
            tempStorage.moveFirst(); // move pointer in the beginning
        }

        if (outStorage.moveFirst()) { // finds min values from list temp storages and write it to output
            TapeStorage minTapeStorage = null;
            do {
                minTapeStorage = getMinCurrentValueFromTmpStorageList(tempList);
                if (outStorage.moveNext() && minTapeStorage != null) {
                    outStorage.writeData(minTapeStorage.getCurrentData());
                }
            } while (minTapeStorage != null);
        }
    }

    private TapeStorage getMinCurrentValueFromTmpStorageList(ArrayList<TapeStorage> tempList) {
        int minValue = Integer.MAX_VALUE;
        TapeStorage result = null;
        for (TapeStorage tmpStorage : tempList) {
            if (tmpStorage.moveNext()) {
                if (tmpStorage.getCurrentData() < minValue) {
                    minValue = tmpStorage.getCurrentData();
                    if (result != null) {
                        result.movePrev();
                    }
                    result = tmpStorage;
                } else {
                    tmpStorage.movePrev();
                }
            }
        }
        return result;
    }

    private ArrayList<TapeStorage> getTempStorageList(ITapeStorage inStorage, int countTempStorages) {
        ArrayList<TapeStorage> resultList = null;

        int tempStorageSize = inStorage.getSize() / countTempStorages;

        if (inStorage.moveFirst()) {
            resultList = new ArrayList<>(tempStorageSize);

            TapeStorage tempStorage = null;
            while (inStorage.moveNext()) {

                if (tempStorage == null || tempStorage.isEnd()) {
                    tempStorage = new TapeStorage(tempStorageSize);
                    resultList.add(tempStorage);
                }

                if (tempStorage.moveNext()) {
                    tempStorage.writeData(inStorage.getCurrentData());
                }
            }
        } else {
            resultList = new ArrayList<>();
        }

        return resultList;
    }

    private ITapeStorage readTestTapeStorageData(int inSize) {
        int[] inTestData = new int[inSize];
        Random random = new Random();
        for (int i = 0; i < inSize; i++) {
            inTestData[i] = random.nextInt(12);
        }

        return new TapeStorage(inTestData);
    }

    public static void main(String[] args) {
        int inStorageSize = 100; //input storage size e.g 1 000 000
        int countTempStorages = 10; // input temporary storages e.g. 100 000

        if (inStorageSize % countTempStorages != 0) {
            System.err.println("Wrong input params");
            System.exit(1);
        }

        TapeDataSorter sorter = new TapeDataSorter();
        ITapeStorage inData = sorter.readTestTapeStorageData(inStorageSize);
        System.out.println("input streamData: ");
        inData.printData();
        System.out.println();
        ITapeStorage outData = new TapeStorage(inData.getSize());
        sorter.sortData(inData, outData, countTempStorages);

        System.out.println("output streamData: ");
        outData.printData();
    }
}
