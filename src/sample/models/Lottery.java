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

    public void evaluateWinners() {
        ListIterator<LotteryTip> iterator = participants.listIterator();

        SortedSet<Integer> intersection = null;

        while (iterator.hasNext()) {
            LotteryTip currentTip = iterator.next();
            System.out.println(currentTip);
            intersection = new TreeSet<>(currentTip.getTipNumbers());
            intersection.retainAll(numbers);
            currentTip.setCorrectGuesses(intersection.size());

            if (intersection.size() >= 2) {
                winners.add(currentTip);
            }
        }

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

    public void generateNumbers(){
        Random rd = new Random();

        for( int i = 0; i < AMOUNT_OF_NUMBERS || numbers.size() < AMOUNT_OF_NUMBERS; i++){
            numbers.add( rd.nextInt(45) + 1);
        }
    }

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

    public void reset() {
        this.numbers.clear();
        participants.clear();
        winners.clear();
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
