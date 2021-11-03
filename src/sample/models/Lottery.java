package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class Lottery {

    public final static int AMOUNT_OF_NUMBERS = 6;

    private SortedSet<Integer> numbers;
    private ObservableList<LotteryTip> participants;
    private ObservableList<LotteryTip> winners;

    private static volatile Lottery instance;

    private Lottery() {
        numbers = new TreeSet<>();
        participants = FXCollections.observableArrayList();
        winners = FXCollections.observableArrayList();
    }

    /**
     * Get the Lottery Singleton
     * @return instance
     */
    public static Lottery getInstance(){

        Lottery result = instance;
        if (result == null) {
            synchronized (Lottery.class) {
                result = instance;
                if (result == null) {
                    instance = new Lottery();
                }
            }
        }
        return instance;
    }

    /**
     * Evaluate the winners of the lottery
     */
    public void evaluateWinners() {
        ListIterator<LotteryTip> iterator = participants.listIterator();

        SortedSet<Integer> intersection = null;

        while (iterator.hasNext()) {
            LotteryTip currentTip = iterator.next();

            // determine correct guesses by intersecting two Sets
            intersection = new TreeSet<>(currentTip.getTipNumbers());
            intersection.retainAll(numbers);
            currentTip.setCorrectGuesses(intersection.size());

            // if a tip has more than one correct guess --> add to the winners list
            if (intersection.size() >= 2) {
                winners.add(currentTip);
            }
        }

        // sort winners by correct guesses descending
        winners.sort(new Comparator<LotteryTip>()
        {
            @Override
            public int compare(LotteryTip o1, LotteryTip o2)
            {
                if (o1.getCorrectGuesses() > o2.getCorrectGuesses()) {
                    return -1;
                } else if (o1.getCorrectGuesses() < o2.getCorrectGuesses()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    /**
     * Generate random numbers for the winner tip
     */
    public void generateNumbers(){
        Random rd = new Random();

        for( int i = 0; i < AMOUNT_OF_NUMBERS || numbers.size() < AMOUNT_OF_NUMBERS; i++){
            numbers.add( rd.nextInt(45) + 1);
        }
    }

    /**
     * Generate test numbers: 1, 2, 3, 4, 5, 6
     */
    public void generateTestNumbers() {
        for (int i = 0; i < AMOUNT_OF_NUMBERS; i++)
        {
            numbers.add((i+1));
        }
    }

    public void addParticipant(LotteryTip participant) {
        participants.add(participant);
    }

    public Set<Integer> getNumbers() {
        return numbers;
    }

    /**
     * Reset lottery
     */
    public void reset() {
        this.numbers.clear();
        participants.clear();
        winners.clear();
    }

    /**
     * Clear lotto numbers
     */
    public void clearNumbers() {
        this.numbers.clear();
    }

    public ObservableList<LotteryTip> getParticipants()
    {
        return participants;
    }

    public ObservableList<LotteryTip> getWinners()
    {
        return winners;
    }

    @Override
    public String toString()
    {
        return "Lottery{" +
                "numbers=" + numbers +
                ", participants=" + participants +
                ", winners=" + winners +
                '}';
    }
}
