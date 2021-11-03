package sample.models;

import java.util.Set;
import java.util.SortedSet;

public class LotteryTip
{
    private static int idCounter = 0;

    private Integer id;
    private SortedSet<Integer> tipNumbers;
    private String name;
    private Integer correctGuesses = 0;

    public LotteryTip(SortedSet<Integer> tipNumbers, String name) {
        this.tipNumbers = tipNumbers;
        this.name = name;
        this.id = LotteryTip.idCounter;
        LotteryTip.idCounter++;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public SortedSet<Integer> getTipNumbers()
    {
        return tipNumbers;
    }

    public void setTipNumbers(SortedSet<Integer> tipNumbers)
    {
        this.tipNumbers = tipNumbers;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getCorrectGuesses()
    {
        return correctGuesses;
    }

    public void setCorrectGuesses(Integer correctGuesses)
    {
        this.correctGuesses = correctGuesses;
    }

    @Override
    public String toString()
    {
        return "LotteryTip{" +
                "id=" + id +
                ", tipNumbers=" + tipNumbers +
                ", name='" + name + '\'' +
                '}';
    }
}
