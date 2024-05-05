package Solver;

import Utilities.*;
import Utilities.Dictionary;

import java.util.*;

abstract public class Algorithm {
    protected static List<String> expandedList = new ArrayList<>();

    protected PriorityQueue<Tuple> priorityQueue = new PriorityQueue<>(Comparator.comparing(Tuple::getEvalValue));

    // kata awal
    public String initialWord;
    // kata akhir
    public String targetWord;

    // fungsi g
    abstract public int evalFuncG(int prevCost);

    // fungsi h
    abstract public int evalFuncH(Tuple word);

    public Algorithm(String initialWord, String targetWord) {
        this.initialWord = initialWord;
        this.targetWord = targetWord;
    }

    public List<String> getOneDiffString(String word) {
        List<String> wordArray = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (word.charAt(i) == targetWord.charAt(i) || c == word.charAt(i)) {
                    continue;
                }
                String tempWord = new String(word.substring(0, i) + c + word.substring(i + 1));
                if (!expandedList.contains(tempWord) && Dictionary.isWordValid(tempWord)) {
                    wordArray.add(tempWord);
                }
            }
        }
        return wordArray;
    }

    public void printTuple() {
        for (Tuple t : priorityQueue) {
            System.out.println(t.getWord() + t.getEvalValue());
        }
    }

    // yang sama hurufnya jadi patokan
    public void solve() {
        while (true) { // dilakukan sampai dibreak ketika sama dengan kata yang diekspansi
            // kosong -> tidak bisa expand lagi -> tidak ketemu solusi
            if (priorityQueue.isEmpty() && !expandedList.isEmpty()) {
                System.out.println("Tidak ada solusi!");
                break;
            } else {
                if (priorityQueue.isEmpty()) // belum ada -> inisialisasi
                {
                    priorityQueue.add(new Tuple(initialWord, 0, new ArrayList<String>()));
                }
                // nilai terkecil sekaligus dihapus
                Tuple expandedTuple = priorityQueue.poll();
                // prosedur ekspansi
                // jika elemen terakhir pada path sudah sesuai -> ketemu
                if (expandedTuple.getWord().equals(targetWord)) {
                    System.out.println("Path:");
                    for (String words : expandedTuple.getPrev()) {
                        System.out.println(words);
                    }
                    System.out.println(expandedTuple.getWord());
                    System.out.println("Banyak node yang dikunjungi: " + expandedTuple.getPrev().size());
                    break;
                } else // ekspansi pertama sesuai priority queue
                {
                    // katanya belum diekspansi
                    if (!expandedList.contains(expandedTuple.getWord())) {
                        List<String> nextWordsList = getOneDiffString(expandedTuple.getWord());
                        List<String> prevList = expandedTuple.getPrev();

                        // masukan semua dalam list
                        for (String word : nextWordsList) {
                            // buat dengan nilai eval dan prev sebelumnya dahulu
                            List<String> newPrevList = new ArrayList<String>(prevList);
                            newPrevList.add(expandedTuple.getWord());
                            // nilai eval untuk inisialisasi tidak penting
                            Tuple newTuple = new Tuple(word, expandedTuple.getEvalValue(), newPrevList);
                            // kasusnya sudah dihandle setiap kelas algoritma
                            int evalValue = evalFuncG(expandedTuple.getEvalValue()) + evalFuncH(newTuple);
                            // ubah value
                            newTuple.setEvalValue(evalValue);
                            // tambah dalam queue
                            priorityQueue.add(newTuple);
                        }
                        // tambah sebagai kata yang sudah diekspansi
                        expandedList.add(expandedTuple.getWord());
                    }
                }
            }
        }
    }
}